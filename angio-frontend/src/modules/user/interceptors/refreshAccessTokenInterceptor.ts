import axios from 'axios';
import {refreshAccessToken} from '@/modules/user/store/userStore';
import store from '@/store';

let isAlreadyFetchingAccessToken: Boolean = false;
let subscribers: (() => any)[] = [];

function onAccessTokenFetched() {
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