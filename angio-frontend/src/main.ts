import Vue from 'vue';

// plugins importing here
import '@/plugins/bus';
import '@/plugins/axios';
import '@/plugins/logger';
import '@/plugins/moment';
import '@/plugins/check-view';
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
