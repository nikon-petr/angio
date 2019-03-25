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

// always in the end of imports
import App from "./App.vue";
import router from "./router";
import store from "./store/root";

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
