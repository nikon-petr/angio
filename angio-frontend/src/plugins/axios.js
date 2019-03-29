import Vue from "vue";
import axios from "axios";
import ls from "local-storage";
import { types as userTypes } from "../store/modules/user";

axios.defaults.baseURL = process.env.VUE_APP_API_BASE_URL;
axios.defaults.headers.common["Authorization"] =
  ls.get("accessToken") || undefined;

Vue.prototype.$axios = axios;

let isRefreshing = false;
let failedQueue = [];

const processQueue = (error, token = null) => {
  failedQueue.forEach(prom => {
    if (error) {
      prom.reject(error);
    } else {
      prom.resolve(token);
    }
  });

  failedQueue = [];
};

axios.interceptors.response.use(
  response => {
    return response;
  },

  error => {
    const originalRequest = error.config;

    if (error.response.status === 401 && !originalRequest._retry) {
      if (isRefreshing) {
        return new Promise((resolve, reject) => {
          failedQueue.push({ resolve, reject });
        })
          .then(token => {
            originalRequest.headers["Authorization"] = "Bearer " + token;
            return axios(originalRequest);
          })
          .catch(err => {
            return err;
          });
      }

      originalRequest._retry = true;
      isRefreshing = true;

      const refreshToken = window.localStorage.getItem("refreshToken");
      return new Promise(async (resolve, reject) => {
        await Vue.store.dispatch(userTypes.REFRESH_ACCESS_TOKEN);

        if (Vue.store.getters[userTypes.IS_AUTHENTIFICATED]) {
          processQueue(null, data.token);
          isRefreshing = false;
          originalRequest.headers["Authorization"] =
            "Bearer " + ls.get("accessToken");
          resolve(axios(originalRequest));
        } else {
          processQueue(err, null);
          isRefreshing = false;
          reject(err);
        }
      });
    }

    return Promise.reject(error);
  }
);
