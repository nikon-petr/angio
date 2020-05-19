import axios from 'axios';
import {UserAction, UserGetter} from '@/modules/user/store/userStore';
import store from '@/store';
import root from 'loglevel';

const log = root.getLogger('user/interceptors/refreshAccessTokenInterceptor');

let isAlreadyFetchingAccessToken: Boolean = false;
let subscribers: (() => any)[] = [];

function onAccessTokenFetched() {
    log.debug('resume all requests waiting for new access token');
    subscribers = subscribers.filter(callback => callback())
}

function addSubscriber(callback: () => any) {
    subscribers.push(callback)
}

axios.interceptors.response.use(
    (response) => response,
    (error) => {
    if (store.getters[UserGetter.IS_AUTHENTICATED] && error.response && error.response.status) {
        const {config, response: {status}} = error;
        const originalRequest = config;

        if (status == 401 && !config.url.includes('/oauth/token')) {
            if (!isAlreadyFetchingAccessToken) {
                log.debug('access token expired. start refreshing');
                isAlreadyFetchingAccessToken = true;
                store.dispatch(UserAction.REFRESH_ACCESS_TOKEN)
                    .then(() => {
                        isAlreadyFetchingAccessToken = false;
                        onAccessTokenFetched();
                    });
            }

            return new Promise((resolve) => {
                addSubscriber(() => {
                    originalRequest.headers.Authorization = axios.defaults.headers.common.Authorization;
                    resolve(axios(originalRequest));
                })
            });
        }
    }

    return Promise.reject(error);
});
