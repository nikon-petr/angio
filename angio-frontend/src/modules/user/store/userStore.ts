import {AxiosResponse} from 'axios';
import ls from 'local-storage';
import {ActionContext} from 'vuex';
import root from 'loglevel';
import {RootState} from '@/store';
import JwtUtils from '@/utils/jwtUtils';
import {UserApiService} from '@/modules/user/services/userApiService';
import {UserLocalStorageService} from '@/modules/user/services/userLocalStorageService';
import {UserAuthModel} from '@/modules/user/models/user';
import {UserAuth, UserInfo, UserPermission, UserSettings, UserState} from '@/modules/user/store/userState';
import {
    cancelAllRequests,
    deleteAxiosAccessToken,
    setAxiosAccessToken,
    setAxiosLocale
} from '@/plugins/axios';
import '@/modules/user/interceptors/refreshAccessTokenInterceptor';
import {NotificationAction} from '@/modules/notification/store/notificationStore';

const log = root.getLogger('store/modules/user');

type UserContext = ActionContext<UserState, RootState>;

export const user = {
    namespaced: true,

    state: () => ({
        fetching: false,
        info: {
            id: ls.get('id') || undefined,
            email: ls.get('email') || undefined,
            fullName: {
                firstname: ls.get('firstname') || undefined,
                lastname: ls.get('lastname') || undefined,
                patronymic: ls.get('patronymic') || undefined,
            },
            organizationName: ls.get('organizationName') || undefined,
            permissions: ls.get('permissions') || [],
        },
        auth: {
            accessToken: ls.get('accessToken') || undefined,
            refreshToken: ls.get('refreshToken') || undefined,
        },
        settings: {
            darkThemeEnabled: ls.get('darkThemeEnabled') || false,
            locale: ls.get('locale') || process.env.VUE_APP_I18N_LOCALE,
        },
    }),

    mutations: {
        startFetching(state: UserState) {
            state.fetching = true;
        },
        endFetching(state: UserState) {
            state.fetching = false;
        },
        clearUser(state: UserState) {
            state.info.email = undefined;
            state.info.id = undefined;
            state.info.fullName.firstname = undefined;
            state.info.fullName.lastname = undefined;
            state.info.fullName.patronymic = undefined;
            state.info.permissions = [];
            state.auth.accessToken = undefined;
            state.auth.refreshToken = undefined;
            UserLocalStorageService.purgeUser();
        },
        setAuth(state: UserState, userAuth: UserAuth) {
            state.auth.accessToken = userAuth.accessToken;
            state.auth.refreshToken = userAuth.refreshToken;
            UserLocalStorageService.persistUserAuth(userAuth);
        },
        setAccessToken(state: UserState, accessToken: string) {
            state.auth.accessToken = accessToken;
            UserLocalStorageService.persistAccessToken(accessToken);
        },
        setUser(state: UserState, userInfo: UserInfo) {
            state.info.id = userInfo.id;
            state.info.email = userInfo.email;
            state.info.fullName.firstname = userInfo.fullName.firstname;
            state.info.fullName.lastname = userInfo.fullName.lastname;
            state.info.fullName.patronymic = userInfo.fullName.patronymic;
            state.info.organizationName = userInfo.organizationName;
            state.info.permissions = userInfo.permissions;
            UserLocalStorageService.persistUserInfo(userInfo);
        },
        setPermissions(state: UserState, userPermissions: UserPermission[]) {
            state.info.permissions = userPermissions;
            UserLocalStorageService.persistUserPermissions(userPermissions);
        },
        setSettings(state: UserState, userSettings: UserSettings) {
            state.settings.darkThemeEnabled = userSettings.darkThemeEnabled;
            state.settings.locale = userSettings.locale;
            UserLocalStorageService.persistUserSetting(userSettings);
            setAxiosLocale(userSettings.locale);
        },
    },

    getters: {
        isAuthenticated(state: UserState): boolean {
            return !!state.auth.accessToken && !!state.auth.refreshToken;
        },
        isAnonymous(state: UserState): boolean {
            return !(!!state.auth.accessToken || !!state.auth.refreshToken);
        },
        hasPermissions(state: UserState) {
            return (permissions: UserPermission[]): boolean => (
                permissions.every((permission) => state.info.permissions.includes(permission as never)) &&
                permissions.length > 0
            );
        },
        hasAnyOfGivenPermissions(state: UserState) {
            return (permissions: UserPermission[]): boolean => (
                permissions.some((permission) => state.info.permissions.includes(permission as never)) &&
                permissions.length > 0
            );
        },
        hasAnyPermission(state: UserState) {
            return state.info.permissions.length > 0;
        },
    },

    actions: {
        async authUser(ctx: UserContext, credentials: { username: string; password: string }): Promise<void> {
            return new Promise((resolve, reject) => {
                ctx.commit(UserMutation.START_FETCHING, undefined, {root: true});
                UserApiService
                    .getToken(credentials)
                    .then(async (userAuthResponse: AxiosResponse<UserAuthModel>) => {

                        setAxiosAccessToken(userAuthResponse.data.access_token);

                        await UserApiService.getMe().then((meResponse) => {

                            ctx.commit(UserMutation.CLEAR_USER, undefined, {root: true});
                            const userAuth: UserAuth = {
                                accessToken: userAuthResponse.data.access_token,
                                refreshToken: userAuthResponse.data.refresh_token,
                            };
                            ctx.commit(UserMutation.SET_AUTH, userAuth, {root: true});

                            ctx.commit(UserMutation.SET_USER, meResponse.data.data, {root: true});
                            ctx.commit(UserMutation.SET_SETTINGS, meResponse.data.data.settings, {root: true});
                        });

                        ctx.dispatch(NotificationAction.INITIATE_NOTIFICATION_POLLING, undefined, {root: true});
                        resolve();
                    })
                    .catch((error) => {
                        log.error(error);
                        ctx.commit(UserMutation.CLEAR_USER, undefined, {root: true});
                        deleteAxiosAccessToken();
                        reject(error);
                    })
                    .finally(() => ctx.commit(UserMutation.END_FETCHING, undefined, {root: true}));
            });
        },
        async refreshAuth(ctx: UserContext) {
            if (ctx.rootGetters[UserGetter.IS_AUTHENTICATED]) {
                ctx.dispatch(NotificationAction.INITIATE_NOTIFICATION_POLLING, undefined, {root: true});
            }
        },
        async refreshAccessToken(ctx: UserContext) {
            ctx.commit(UserMutation.START_FETCHING, undefined, {root: true});
            await UserApiService
                .refreshToken(ctx.state.auth.refreshToken as string)
                .then((response) => {
                    ctx.commit(UserMutation.SET_ACCESS_TOKEN, response.data.access_token, {root: true});
                    setAxiosAccessToken(response.data.access_token);
                    JwtUtils.decodeJwtToken(response.data.access_token)
                        .then((jwtClaims) => {
                            ctx.commit(UserMutation.SET_PERMISSIONS, jwtClaims.authorities as UserPermission[], {root: true});
                        })
                        .catch(() => log.error('jwt decryption failed'));
                })
                .catch((error) => {
                    ctx.commit(UserMutation.CLEAR_USER, undefined, {root: true});
                    deleteAxiosAccessToken();
                    log.error(error);
                })
                .then(() => ctx.commit(UserMutation.END_FETCHING, undefined, {root: true}));
        },
        async logout(ctx: UserContext) {
            ctx.commit(UserMutation.START_FETCHING, undefined, {root: true});
            // TODO: api call
            ctx.dispatch(NotificationAction.DOWN_NOTIFICATION_POLLING, undefined, {root: true});
            ctx.commit(UserMutation.CLEAR_USER, undefined, {root: true});
            cancelAllRequests();
            ctx.commit(UserMutation.END_FETCHING, undefined, {root: true});
        },
        async fetchUser(ctx: UserContext) {
            ctx.commit(UserMutation.START_FETCHING, undefined, {root: true});
            await UserApiService
                .getMe()
                .then((response) => ctx.commit(UserMutation.SET_USER, response.data.data, {root: true}))
                .catch((error) => log.error(error));
            await UserApiService
                .getSettings()
                .then((response) => ctx.commit(UserMutation.SET_SETTINGS, response.data.data, {root: true}))
                .catch((error) => log.error(error));
            ctx.commit(UserMutation.END_FETCHING, undefined, {root: true});
        },
    },
};

export enum UserMutation {
    START_FETCHING = 'user/startFetching',
    END_FETCHING = 'user/endFetching',
    CLEAR_USER = 'user/clearUser',
    SET_AUTH = 'user/setAuth',
    SET_ACCESS_TOKEN = 'user/setAccessToken',
    SET_USER = 'user/setUser',
    SET_PERMISSIONS = 'user/setPermissions',
    SET_SETTINGS = 'user/setSettings'
}

export enum UserGetter {
    IS_AUTHENTICATED = 'user/isAuthenticated',
    IS_ANONYMOUS = 'user/isAnonymous',
    HAS_PERMISSIONS = 'user/hasPermissions',
    HAS_ANY_OF_GIVEN_PERMISSIONS = 'user/hasAnyOfGivenPermissions',
    HAS_ANY_PERMISSION = 'user/hasAnyPermission'
}

export enum UserAction {
    AUTH_USER = 'user/authUser',
    REFRESH_AUTH = 'user/refreshAuth',
    REFRESH_ACCESS_TOKEN = 'user/refreshAccessToken',
    LOGOUT = 'user/logout',
    FETCH_USER = 'user/fetchUser'
}
