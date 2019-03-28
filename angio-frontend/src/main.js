import Vue from "vue";

// plugins importing here
import "./plugins/event-bus";
import "./plugins/axios";
import "./plugins/logger";
import "./plugins/moment";
import "./plugins/vuetify";
import "./plugins/notification";
import "./plugins/lightbox";
import "./plugins/filepond";
import i18n from "./plugins/i18n";

// always in the end of imports
import App from "./App.vue";
import router from "./router";
import store from "./store/index";

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  i18n,
  render: h => h(App)
}).$mount("#app");
