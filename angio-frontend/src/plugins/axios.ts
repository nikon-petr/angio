import Vue from 'vue';
import qs from 'qs';
import ls from 'local-storage';
import axios, {AxiosStatic} from 'axios';
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

export const setAxiosLocale = (locale: string) => {
    log.debug(`set axios locale to ${locale}`);
    axios.defaults.headers.common['Accept-Language'] = `${locale}-${locale.toUpperCase()}`;
};

if (!!ls.get('accessToken')) {
    setAxiosAccessToken(ls.get('accessToken'));
}

if (!!ls.get('locale')) {
    setAxiosLocale(ls.get('locale'));
}

const dateFormat = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}Z$/;

axios.defaults.transformResponse = [(data: string) => {
    let resp;

    try {
        resp = JSON.parse(data, (key, value) => {
            if (typeof value === 'string' && dateFormat.test(value)) {
                return new Date(value);
            }

            return value;
        });
    } catch (error) {
        return data;
    }

    return resp;
}];

// @ts-ignore
axios.defaults.paramsSerializer = (params) => {
    const query = {...params};
    Object.keys(query).forEach(key => query[key] === undefined && delete query[key]);
    return qs.stringify(query, {arrayFormat: 'repeat'});
};

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

declare module 'vue/types/vue' {
    export interface Vue {
        $axios: AxiosStatic;
    }
}
