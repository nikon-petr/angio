import axios, {AxiosPromise} from 'axios';
import qs from 'querystring';
import root from 'loglevel';
import {
    RefreshTokenModel,
    UserAuthModel,
    UserCredentialsModel,
    UserInfoModel,
    UserSettingsModel,
} from '@/modules/user/models/user';
import {Response} from '@/modules/common/models/response';
import {OAUTH_CONFIG} from '@/plugins/axios';

const log = root.getLogger('api/user');

export class UserApiService {
    public static getToken(credentials: UserCredentialsModel): AxiosPromise<UserAuthModel> {
        const data = {
            grant_type: 'password',
            username: credentials.username,
            password: credentials.password,
        };
        log.debug(`create getToken request with data: ${JSON.stringify(data)}`);
        return axios.post<UserAuthModel>('/oauth/token', qs.stringify(data), OAUTH_CONFIG);
    }

    public static refreshToken(refreshToken: string): AxiosPromise<RefreshTokenModel> {
        const data = {
            grant_type: 'refresh_token',
            refresh_token: refreshToken,
        };
        log.debug(`create refreshToken request with data: ${JSON.stringify(data)}`);
        return axios.post('/oauth/token', qs.stringify(data), OAUTH_CONFIG);
    }

    public static getMe(): AxiosPromise<Response<UserInfoModel>> {
        log.debug('create getMe request');
        return axios.get<Response<UserInfoModel>>('/user/me');
    }

    public static getSettings(): AxiosPromise<Response<UserSettingsModel>> {
        log.debug('create getSettings request');
        return axios.get<Response<UserSettingsModel>>('/user/me/settings');
    }
}
