import axios, {AxiosResponse} from 'axios';
import ls from 'local-storage';
import {ActionContext} from 'vuex';
import {getStoreAccessors} from 'vuex-typescript';
import root from 'loglevel';
import {RootState} from '@/store/root';
import UserApi from '@/api/user';
import JwtUtils from '@/utils/jwtUtils';
import userStoreHelper from '@/store/helpers/user';
import {UserAuthModel, UserPermission} from '@/models/user';

const log = root.getLogger('store/modules/user');

export interface UserState {
    fetching: boolean;
    info: UserInfo;
    auth: UserAuth;
    settings: UserSettings;
}

export interface UserInfo {
    id: string | undefined;
    email: string | undefined;
    firstname: string | undefined;
    lastname: string | undefined;
    patronymic: string | undefined;
    permissions: UserPermission[] | [];
}

export interface UserAuth {
    accessToken: string | undefined;
    refreshToken: string | undefined;
}

export interface UserSettings {
    darkThemeEnabled: boolean;
    locale: string;
}

type UserContext = ActionContext<UserState, RootState>;

export const user = {
    namespaced: true,

    state: () => ({
        fetching: false,
        info: {
            id: ls.get('id') || undefined,
            email: ls.get('email') || undefined,
            firstname: ls.get('firstname') || undefined,
            lastname: ls.get('lastname') || undefined,
            patronymic: ls.get('patronymic') || undefined,
            permissions: ls.get('permissions') || [],
        } as UserInfo,
        auth: {
            accessToken: ls.get('accessToken') || undefined,
            refreshToken: ls.get('refreshToken') || undefined,
        } as UserAuth,
        settings: {
            darkThemeEnabled: ls.get('darkThemeEnabled') || false,
            locale: ls.get('locale') || process.env.VUE_APP_I18N_LOCALE,
        } as UserSettings,
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
            state.info.firstname = undefined;
            state.info.lastname = undefined;
            state.info.patronymic = undefined;
            state.info.permissions = [];
            state.auth.accessToken = undefined;
            state.auth.refreshToken = undefined;
            userStoreHelper.purgeUser();
        },
        setAuth(state: UserState, userAuth: UserAuth) {
            state.auth.accessToken = userAuth.accessToken;
            state.auth.refreshToken = userAuth.refreshToken;
            userStoreHelper.persistUserAuth(userAuth);
        },
        setAccessToken(state: UserState, accessToken: string) {
            state.auth.accessToken = accessToken;
            userStoreHelper.persistAccessToken(accessToken);
        },
        setUser(state: UserState, userInfo: UserInfo) {
            state.info.id = userInfo.id;
            state.info.email = userInfo.email;
            state.info.firstname = userInfo.firstname;
            state.info.lastname = userInfo.lastname;
            state.info.patronymic = userInfo.patronymic;
            state.info.permissions = userInfo.permissions;
            userStoreHelper.persistUserInfo(userInfo);
        },
        setPermissions(state: UserState, userPermissions: UserPermission[]) {
            state.info.permissions = userPermissions;
            userStoreHelper.persistUserPermissions(userPermissions);
        },
        setSettings(state: UserState, userSettings: UserSettings) {
            state.settings.darkThemeEnabled = userSettings.darkThemeEnabled;
            state.settings.locale = userSettings.locale;
            userStoreHelper.persistUserSetting(userSettings);
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
            UserApi.getToken(credentials)
                .then(async (userAuthResponse: AxiosResponse<UserAuthModel>) => {
                    clearUser(ctx);
                    const userAuth: UserAuth = {
                        accessToken: userAuthResponse.data.access_token,
                        refreshToken: userAuthResponse.data.refresh_token,
                    };
                    setAuth(ctx, userAuth);

                    axios.defaults.headers.common.Authorization = `Bearer ${userAuthResponse.data.access_token}`;

                    await UserApi.getMe().then((meResponse) => {
                        setUser(ctx, meResponse.data.data);
                    });
                    await UserApi.getSettings().then((settingsResponse) => {
                        setSettings(ctx, settingsResponse.data.data);
                    });
                })
                .catch((error) => {
                    clearUser(ctx);
                    delete axios.defaults.headers.common.Authorization;
                    log.error(error);
                })
                .then(() => endFetching(ctx));
        },
        async refreshAccessToken(ctx: UserContext) {
            startFetching(ctx);
            UserApi
                .refreshToken(ctx.state.auth.refreshToken as string)
                .then((response) => {
                    setAccessToken(ctx, response.data.access_token);
                    JwtUtils.decodeJwtToken(response.data.access_token)
                        .then((jwtClaims) => {
                            setPermissions(ctx, jwtClaims.authorities as UserPermission[]);
                        })
                        .catch(() => log.error('jwt decryption failed'));
                })
                .catch((error) => {
                    clearUser(ctx);
                    delete axios.defaults.headers.common.Authorization;
                    log.error(error);
                })
                .then(() => endFetching(ctx));
        },
        async fetchUser(ctx: UserContext) {
            startFetching(ctx);
            await UserApi
                .getMe()
                .then((response) => setUser(ctx, response.data.data))
                .catch((error) => log.error(error));
            await UserApi
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
export const refreshAccessToken = dispatch(user.actions.refreshAccessToken);
export const fetchUser = dispatch(user.actions.fetchUser);
