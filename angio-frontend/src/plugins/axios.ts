import Vue from 'vue';
import ls from 'local-storage';
import axios from 'axios';

axios.defaults.baseURL = process.env.VUE_APP_API_BASE_URL;

if (!!ls.get('accessToken')) {
    axios.defaults.headers.common.Authorization = `Bearer ${ls.get('accessToken')}`;
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

// cancel token
const CancelToken = axios.CancelToken;

const cancelSourceConteiner = {
    cancelSource: CancelToken.source(),
};

const refreshCancelToken = () => {
    cancelSourceConteiner.cancelSource = CancelToken.source();
};

export const cancelAllRequests = () => {
    cancelSourceConteiner.cancelSource.cancel('cancel all requests');
    refreshCancelToken();
};

axios.interceptors.request.use(function (config) {
    config.cancelToken = cancelSourceConteiner.cancelSource.token;
    return config;
}, function (error) {
    return Promise.reject(error);
});

// refresh access token interceptor

Vue.prototype.$axios = axios;
