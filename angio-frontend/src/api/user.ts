import axios, {AxiosPromise} from 'axios';
import root from 'loglevel';
import {RefreshTokenModel, UserAuthModel, UserCredentialsModel, UserInfoModel, UserSettingsModel} from '@/models/user';
import {Response} from '@/models/response';

const log = root.getLogger('api/user');

export default {
    getToken(credentials: UserCredentialsModel): AxiosPromise<UserAuthModel> {
        const data = {
            client_id: process.env.VUE_APP_CLIENT_ID,
            client_secret: process.env.VUE_APP_CLIENT_SECRET,
            grant_type: 'password',
            username: credentials.username,
            password: credentials.password,
        };
        log.debug(`create getToken request with data: ${data}`);
        return axios.post<UserAuthModel>('/oauth/token', data);
    },

    refreshToken(refreshToken: string): AxiosPromise<RefreshTokenModel> {
        const data = {
            client_id: process.env.VUE_APP_CLIENT_ID,
            client_secret: process.env.VUE_APP_CLIENT_SECRET,
            grant_type: 'refresh_token',
            refresh_token: refreshToken,
        };
        log.debug(`create refreshToken request with data: ${data}`);
        return axios.post('/oauth/token', data);
    },

    getMe(): AxiosPromise<Response<UserInfoModel>> {
        log.debug('create getMe request');
        return axios.get<Response<UserInfoModel>>('/user/me');
    },

    getSettings(): AxiosPromise<Response<UserSettingsModel>> {
        log.debug('create getSettings request');
        return axios.get<Response<UserSettingsModel>>('/user/me/settings');
    },
};
