import Vue from 'vue';
import qs from 'qs';
import ls from 'local-storage';
import axios, {AxiosRequestConfig, AxiosStatic, CancelTokenStatic} from 'axios';
import root from 'loglevel';

const log = root.getLogger('AxiosConfigurator');

export class AxiosConfigurator {

    private static initiated: boolean = false;

    private static CancelToken: CancelTokenStatic = axios.CancelToken;

    private static cancelSourceContainer = {
        cancelSource: AxiosConfigurator.CancelToken.source(),
    };

    public static readonly OAUTH_CONFIG: AxiosRequestConfig = {
        baseURL: process.env.VUE_APP_OAUTH_BASE_URL as string,
        auth: {
            username: process.env.VUE_APP_CLIENT_ID as string,
            password: process.env.VUE_APP_CLIENT_SECRET as string,
        },
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
    };

    public static setAxiosAccessToken(accessToken: string) {
        log.debug('set access token [SECRET]');
        axios.defaults.headers.common.Authorization = `Bearer ${accessToken}`;
    }

    public static deleteAxiosAccessToken() {
        log.debug('delete access token');
        delete axios.defaults.headers.common.Authorization;
    };

    public static setAxiosLocale(locale: string) {
        log.debug(`set axios Accept-Language header to ${locale}-${locale.toUpperCase()}`);
        axios.defaults.headers.common['Accept-Language'] = `${locale}-${locale.toUpperCase()}`;
    };

    public static cancelAllRequests() {
        log.debug('cancel all requests');
        AxiosConfigurator.cancelSourceContainer.cancelSource.cancel();
        AxiosConfigurator.cancelSourceContainer.cancelSource = AxiosConfigurator.CancelToken.source();
    };

    public static init() {
        log.debug('init');

        if (AxiosConfigurator.initiated) {
            log.debug('axios already initiated');
            return;
        }

        log.debug(`set base url: ${process.env.VUE_APP_API_BASE_URL}`);
        axios.defaults.baseURL = process.env.VUE_APP_API_BASE_URL;

        if (!!ls.get('accessToken')) {
            log.debug('accessToken found in local storage...');
            AxiosConfigurator.setAxiosAccessToken(ls.get('accessToken'));
        }

        if (!!ls.get('locale')) {
            log.debug('locale found in local storage...');
            AxiosConfigurator.setAxiosLocale(ls.get('locale'));
        }

        const dateFormat = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}Z$/;

        log.trace(`setup response transformer for date format ${dateFormat}`);
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

        log.trace('setup query params serializer');
        // @ts-ignore
        axios.defaults.paramsSerializer = (params) => {
            const query = {...params};
            Object.keys(query).forEach(key => query[key] === undefined && delete query[key]);
            return qs.stringify(query, {arrayFormat: 'repeat'});
        };

        log.trace('setup cancelToken injector');
        axios.interceptors.request.use(function (config) {
            config.cancelToken = AxiosConfigurator.cancelSourceContainer.cancelSource.token;
            return config;
        }, function (error) {
            return Promise.reject(error);
        });

        Vue.prototype.$axios = axios;

        AxiosConfigurator.initiated = true;
        log.debug('initiated');
    }
}

AxiosConfigurator.init();

declare module 'vue/types/vue' {
    export interface Vue {
        $axios: AxiosStatic;
    }
}
