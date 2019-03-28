import Vue from "vue";
import Vuex from "vuex";
import createLogger from "vuex/dist/logger";

// vuex modules imports here
import { default as user, userMutationInterceptor } from "./modules/user";

Vue.use(Vuex);
Vue.config.devtools = process.env.NODE_ENV === "development";

export const storeConfig = {
  modules: {
    user
  },
  strict: process.env.NODE_ENV !== "production",
  plugins: process.env.NODE_ENV !== "production" ? [createLogger()] : []
};

const store = new Vuex.Store(storeConfig);

store.subscribe((mutation, state) => {
  userMutationInterceptor(mutation, state);
});

export default store;
