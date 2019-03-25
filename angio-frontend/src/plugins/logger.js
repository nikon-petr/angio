import Vue from "vue";
import VueLogger from "vuejs-logger";

const options = {
  isEnabled: true,
  logLevel: process.env.VUE_APP_LOGGING_LEVEL,
  stringifyArguments: false,
  showLogLevel: true,
  showMethodName: true,
  separator: "|",
  showConsoleColors: true
};

Vue.use(VueLogger, options);
