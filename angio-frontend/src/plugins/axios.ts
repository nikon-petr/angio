import Vue from 'vue';
import ls from 'local-storage';
import axios from 'axios';
import root from 'loglevel';

const log = root.getLogger('plugins/axios');

axios.defaults.baseURL = process.env.VUE_APP_API_BASE_URL;

export const setAxiosAccessToken = (accessToken: string) => {
    log.debug('set access token [SECRET]');
    axios.defaults.headers.common.Authorization = `Bearer ${accessToken}`;
};

export const deleteAxiosAccessToken = () => {
    log.debug('delete access token');
    delete axios.defaults.headers.common.Authorization;
};

if (!!ls.get('accessToken')) {
    setAxiosAccessToken(ls.get('accessToken'));
}

export const OAUTH_CONFIG = {
    baseURL: process.env.VUE_APP_OAUTH_BASE_URL as string,
    auth: {
        username: process.env.VUE_APP_CLIENT_ID as string,
        password: process.env.VUE_APP_CLIENT_SECRET as string,
    },
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
    },
};

// setup cancel token
const CancelToken = axios.CancelToken;

const cancelSourceContainer = {
    cancelSource: CancelToken.source(),
};

export const cancelAllRequests = () => {
    log.debug('cancel all requests');
    cancelSourceContainer.cancelSource.cancel();
    cancelSourceContainer.cancelSource = CancelToken.source();
};

axios.interceptors.request.use(function (config) {
    config.cancelToken = cancelSourceContainer.cancelSource.token;
    return config;
}, function (error) {
    return Promise.reject(error);
});

Vue.prototype.$axios = axios;
