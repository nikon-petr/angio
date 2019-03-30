import root, {Logger} from 'loglevel';
import ls from 'local-storage';
import {UserAuth, UserInfo, UserSettings} from '@/store/modules/user';
import {UserPermission} from '@/models/user';

const log: Logger = root.getLogger('store/helpers/user');

export function purgeUser() {
    log.debug('clear local storage');
    ls.remove('accessToken');
    ls.remove('refreshToken');
    ls.remove('id');
    ls.remove('email');
    ls.remove('firstname');
    ls.remove('lastname');
    ls.remove('patronymic');
    ls.remove('permissions');
}

export function persistAccessToken(accessToken: string) {
    log.debug('save access token to local storage');
    ls.set('accessToken', accessToken);
}

export function persistUserAuth(userAuth: UserAuth) {
    log.debug('save access and refresh token to local storage');
    ls.set('accessToken', userAuth.accessToken);
    ls.set('refreshToken', userAuth.refreshToken);
}

export function persistUserInfo(userInfo: UserInfo) {
    log.debug('save user data to local storage');
    ls.set('id', userInfo.id);
    ls.set('email', userInfo.email);
    ls.set('firstname', userInfo.firstname);
    ls.set('lastname', userInfo.lastname);
    ls.set('patronymic', userInfo.patronymic);
    ls.set('permissions', userInfo.permissions);
    ls.set('firstname', userInfo.firstname);
}

export function persistUserPermissions(permissions: UserPermission[]) {
    ls.set('permissions', permissions);
}

export function persistUserSetting(settings: UserSettings) {
    log.debug('save user settings to local storage');
    ls.set('settings.darkThemeEnabled', settings.darkThemeEnabled);
    ls.set('settings.locale', settings.locale);
}

export default {
    purgeUser,
    persistAccessToken,
    persistUserAuth,
    persistUserInfo,
    persistUserPermissions,
    persistUserSetting,
};
