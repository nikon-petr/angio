import Page from '@/modules/common/models/page';
import {Response} from '@/modules/common/models/response';
import {
    ChangePasswordModel,
    EmailModel,
    NewUserModel,
    RefreshTokenModel,
    UserActivationModel,
    UserAuthModel,
    UserCredentialsModel,
    UserDetailsModel,
    UserFilterModel,
    UserInfoModel,
    UserLockedModel,
    UserResetAccountModel,
    UserSettingsModel,
} from '@/modules/user/models/user';
import {OAUTH_CONFIG} from '@/plugins/axios';
import axios, {AxiosPromise} from 'axios';
import root from 'loglevel';
import qs from 'querystring';

export class UserApiService {
    private static log = root.getLogger('UserApiService');

    public static getToken(credentials: UserCredentialsModel): AxiosPromise<UserAuthModel> {
        const data = {
            grant_type: 'password',
            username: credentials.username,
            password: credentials.password,
        };
        UserApiService.log.debug(`create getToken request with data: ${JSON.stringify(data)}`);
        return axios.post<UserAuthModel>('/oauth/token', qs.stringify(data), OAUTH_CONFIG);
    }

    public static refreshToken(refreshToken: string): AxiosPromise<RefreshTokenModel> {
        const data = {
            grant_type: 'refresh_token',
            refresh_token: refreshToken,
        };
        UserApiService.log.debug(`create refreshToken request with data: [SECRET]`);
        return axios.post('/oauth/token', qs.stringify(data), OAUTH_CONFIG);
    }

    public static getMe(): AxiosPromise<Response<UserInfoModel>> {
        UserApiService.log.debug('create getMe request');
        return axios.get<Response<UserInfoModel>>('/user/me');
    }

    public static getUserById(userId: string): AxiosPromise<Response<UserDetailsModel>> {
        UserApiService.log.debug(`create getUserById request with id: ${userId}`);
        return axios.get<Response<UserDetailsModel>>(`/user/${userId}`);
    }

    public static getSettings(): AxiosPromise<Response<UserSettingsModel>> {
        UserApiService.log.debug('create getSettings request');
        return axios.get<Response<UserSettingsModel>>('/user/me/settings');
    }

    public static patchSettings(userSettings: UserSettingsModel): AxiosPromise<Response<UserSettingsModel>> {
        UserApiService.log.debug('create patchSettings request');
        return axios.patch<Response<UserSettingsModel>>('/user/me/settings', userSettings);
    }

    public static activate(userId: string, model: UserActivationModel): AxiosPromise<UserDetailsModel> {
        UserApiService.log.debug(`create activate request with data: ${JSON.stringify(model)}`);
        return axios.post<UserDetailsModel>(`/user/${userId}/enable`, model);
    }

    public static changePassword(model: ChangePasswordModel): AxiosPromise<void> {
        UserApiService.log.debug(`change current user password`);
        return axios.post(`/user/me/password`, model);
    }

    public static resetPassword(model: EmailModel): AxiosPromise<void> {
        UserApiService.log.debug(`reset current user password request with email: ${model.email}`);
        return axios.post(`/user/${model.email}/password/reset`);
    }

    public static resetAccount(userId: string, model: UserResetAccountModel): AxiosPromise<void> {
        UserApiService.log.debug(`reset current user account request with data: ${JSON.stringify(model)}`);
        return axios.post(`/user/${userId}/reset`, model);
    }

    public static register(model: EmailModel): AxiosPromise<void> {
        UserApiService.log.debug(`register new user with email: ${JSON.stringify(model)}`);
        return axios.post(`/user/register`, model);
    }

    public static getUserFilter(filter: UserFilterModel, size?: number, page?: number, sort?: string): AxiosPromise<Response<Page<UserDetailsModel>>> {
        UserApiService.log.debug(`create getUserFilter request with data ${JSON.stringify(filter)}`);
        return axios.get<Response<Page<UserDetailsModel>>>('/user', {params: {...filter, size, page, sort}});
    }

    public static postLocked(userId: string, locked: boolean): AxiosPromise<Response<UserLockedModel>> {
        UserApiService.log.debug(`change user ${userId} locked to ${locked}`);
        return axios.post<Response<UserLockedModel>>(`/user/${userId}/locked`, {locked});
    }

    public static changeRoles(userId: string, roleIds: number[]): AxiosPromise<Response<UserDetailsModel>> {
        UserApiService.log.debug(`create changeRoles request with data ${userId}`);
        return axios.post<Response<UserDetailsModel>>(`/user/${userId}/role`, roleIds);
    }

    public static changeOwnedRoles(userId: string, roleIds: number[]): AxiosPromise<Response<UserDetailsModel>> {
        UserApiService.log.debug(`create changeOwnedRoles request with data ${userId}`);
        return axios.post<Response<UserDetailsModel>>(`/user/${userId}/owned-role`, roleIds);
    }

    public static createUsers(users: NewUserModel[]): AxiosPromise<Response<UserDetailsModel[]>> {
        UserApiService.log.debug(`create createUsers request with data ${JSON.stringify(users)}`);
        return axios.post<Response<UserDetailsModel[]>>(`/user`, users);
    }
}
