import Vue from 'vue';
import axios from 'axios';
import ls from 'local-storage';

axios.defaults.baseURL = process.env.VUE_APP_API_BASE_URL;

if(!!ls.get('accessToken')) {
    axios.defaults.headers.common.Authorization = ls.get('accessToken');
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

Vue.prototype.$axios = axios;
