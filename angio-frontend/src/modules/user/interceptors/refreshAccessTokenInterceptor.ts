import axios from 'axios';
import {refreshAccessToken} from '@/modules/user/store/userStore';
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

axios.interceptors.response.use((response) => {
    return response
}, (error) => {
    const {config, response: {status}} = error;
    const originalRequest = config;

    if (status == 401) {
        if (!isAlreadyFetchingAccessToken) {
            log.debug('access token expired. start refreshing');
            isAlreadyFetchingAccessToken = true;
            refreshAccessToken(store)
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

    return Promise.reject(error);
});