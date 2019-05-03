import {AxiosResponse} from 'axios';
import ls from 'local-storage';
import {ActionContext} from 'vuex';
import {getStoreAccessors} from 'vuex-typescript';
import root from 'loglevel';
import store, {RootState} from '@/store';
import {UserApiService} from '@/modules/user/services/userApiService';
import JwtUtils from '@/utils/jwtUtils';
import {UserLocalStorageService} from '@/modules/user/services/userLocalStorageService';
import {UserAuthModel} from '@/modules/user/models/user';
import {Locale, UserAuth, UserInfo, UserPermission, UserSettings, UserState} from '@/modules/user/store/userState';
import {namespace} from 'vuex-class';
import {downNotificationPolling, initiateNotificationPolling} from '@/modules/notification/store/notificationStore';
import {cancelAllRequests, deleteAxiosAccessToken, setAxiosAccessToken} from '@/plugins/axios';
import '@/modules/user/interceptors/refreshAccessTokenInterceptor';

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
        async authUser(ctx: UserContext, credentials: { username: string; password: string }) {
            startFetching(ctx);
            return UserApiService.getToken(credentials)
                .then(async (userAuthResponse: AxiosResponse<UserAuthModel>) => {

                    setAxiosAccessToken(userAuthResponse.data.access_token);

                    await UserApiService.getMe().then((meResponse) => {

                        clearUser(ctx);
                        const userAuth: UserAuth = {
                            accessToken: userAuthResponse.data.access_token,
                            refreshToken: userAuthResponse.data.refresh_token,
                        };
                        setAuth(ctx, userAuth);

                        setUser(ctx, meResponse.data.data);
                        setSettings(ctx, meResponse.data.data.settings);
                    });

                    initiateNotificationPolling(store);
                })
                .catch((error) => {
                    log.error(error);
                    clearUser(ctx);
                    deleteAxiosAccessToken();
                    throw error;
                })
                .finally(() => endFetching(ctx));
        },
        async refreshAuth(ctx: UserContext) {
            if (isAuthenticated(ctx)) {
                initiateNotificationPolling(store);
            }
        },
        async refreshAccessToken(ctx: UserContext) {
            startFetching(ctx);
            await UserApiService
                .refreshToken(ctx.state.auth.refreshToken as string)
                .then((response) => {
                    setAccessToken(ctx, response.data.access_token);
                    setAxiosAccessToken(response.data.access_token);
                    JwtUtils.decodeJwtToken(response.data.access_token)
                        .then((jwtClaims) => {
                            setPermissions(ctx, jwtClaims.authorities as UserPermission[]);
                        })
                        .catch(() => log.error('jwt decryption failed'));
                })
                .catch((error) => {
                    clearUser(ctx);
                    deleteAxiosAccessToken();
                    log.error(error);
                })
                .then(() => endFetching(ctx));
        },
        async logout(ctx: UserContext) {
            startFetching(ctx);
            // TODO: api call
            downNotificationPolling(store);
            clearUser(ctx);
            cancelAllRequests();
            endFetching(ctx);
        },
        async fetchUser(ctx: UserContext) {
            startFetching(ctx);
            await UserApiService
                .getMe()
                .then((response) => setUser(ctx, response.data.data))
                .catch((error) => log.error(error));
            await UserApiService
                .getSettings()
                .then((response) => setSettings(ctx, response.data.data))
                .catch((error) => log.error(error));
            endFetching(ctx);
        },
    },
};

const {commit, read, dispatch} = getStoreAccessors<UserState, RootState>('user');

// getters
export const isAuthenticated = read(user.getters.isAuthenticated);
export const isAnonymous = read(user.getters.isAnonymous);
export const hasPermissions = read(user.getters.hasPermissions);
export const hasAnyOfGivenPermissions = read(user.getters.hasAnyOfGivenPermissions);
export const hasAnyPermission = read(user.getters.hasAnyPermission);

// mutations
export const startFetching = commit(user.mutations.startFetching);
export const endFetching = commit(user.mutations.endFetching);
export const clearUser = commit(user.mutations.clearUser);
export const setAuth = commit(user.mutations.setAuth);
export const setAccessToken = commit(user.mutations.setAccessToken);
export const setUser = commit(user.mutations.setUser);
export const setPermissions = commit(user.mutations.setPermissions);
export const setSettings = commit(user.mutations.setSettings);

// actions
export const authUser = dispatch(user.actions.authUser);
export const refreshAuth = dispatch(user.actions.refreshAuth);
export const refreshAccessToken = dispatch(user.actions.refreshAccessToken);
export const logout = dispatch(user.actions.logout);
export const fetchUser = dispatch(user.actions.fetchUser);

// module
export const userModule = namespace('user');
