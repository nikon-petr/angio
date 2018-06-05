// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Vuetify from 'vuetify'
import App from './App'
import store from './store'
import router from './router'
import axios from 'axios'
import VueAxios from 'vue-axios'
import 'vuetify/dist/vuetify.min.css'
import VueImg from 'v-img'

Vue.router = router

Vue.use(VueAxios, axios)
Vue.axios.defaults.baseURL = 'http://localhost/api'

Vue.axios.interceptors.request.use((config) => {
  return config
}, (error) => {
  if (error.status === 401) {
    Vue.auth.logout({
      redirect: {path: '/user/sign-in'}
    })
  }
  return Promise.reject(error)
})

Vue.use(Vuetify)

Vue.use(VueImg)

Vue.use(require('@websanova/vue-auth'), {
  auth: require('@websanova/vue-auth/drivers/auth/bearer.js'),
  http: require('@websanova/vue-auth/drivers/http/axios.1.x.js'),
  router: require('@websanova/vue-auth/drivers/router/vue-router.2.x.js'),
  rolesVar: 'authorities',
  authRedirect: {path: '/user/sign-in'},
  notFoundRedirect: {path: '/not-found'},
  registerData: {url: 'v1/user', method: 'POST', redirect: false},
  loginData: {url: 'v1/auth/token', method: 'POST', redirect: false, fetchUser: true},
  logoutData: {url: 'v1/auth/logout', method: 'POST', redirect: '/', makeRequest: true},
  fetchData: {url: 'v1/user/self', method: 'GET', enabled: true},
  refreshData: {url: 'v1/auth/token/refresh', method: 'POST', enabled: true, interval: 30}
})

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
