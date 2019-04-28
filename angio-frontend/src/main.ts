import Vue from 'vue';
import 'reflect-metadata';

// plugins importing here
import 'animate.css';
import '@/plugins/logger';
import '@/plugins/bus';
import '@/plugins/axios';
import '@/plugins/moment';
import '@/plugins/vuetify';
import '@/plugins/notification';
import '@/plugins/lightbox';
import '@/plugins/filepond';
import '@/plugins/filters';
import '@/plugins/axios';
import i18n from '@/plugins/i18n';

// always in the end of imports
import App from '@/App.vue';
import router from '@/router';
import store from '@/store';

Vue.config.productionTip = false;

new Vue({
    router,
    store,
    i18n,
    render: (h) => h(App),
}).$mount('#app');
