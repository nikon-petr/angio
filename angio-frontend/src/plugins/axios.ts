import Vue from 'vue';
import axios from 'axios';
import ls from 'local-storage';

axios.defaults.baseURL = process.env.VUE_APP_API_BASE_URL;
axios.defaults.headers.common.Authorization =
    ls.get('accessToken') || undefined;

Vue.prototype.$axios = axios;
