import Vue from "vue";
import VueI18n from "vue-i18n";
import ls from "local-storage";
import store from "../store/index";

Vue.use(VueI18n);

function loadLocaleMessages() {
  const locales = require.context(
    "../locales",
    true,
    /[A-Za-z0-9-_,\s]+\.json$/i
  );
  const messages = {};
  locales.keys().forEach(key => {
    const matched = key.match(/([A-Za-z0-9-_]+)\./i);
    if (matched && matched.length > 1) {
      const locale = matched[1];
      messages[locale] = locales(key);
    }
  });
  return messages;
}

// Hot updates
if (module.hot) {
  module.hot.accept(["../locales/en", "../locales/ru"], function() {
    Vue.$i18n.setLocaleMessage("en", require("../locales/en").default);
    Vue.$i18n.setLocaleMessage("ru", require("../locales/ru").default);
  });
}

const i18n = new VueI18n({
  locale: ls.get("settings.locale") || process.env.VUE_APP_I18N_LOCALE,
  fallbackLocale: process.env.VUE_APP_I18N_FALLBACK_LOCALE || "en",
  messages: loadLocaleMessages()
});

store.watch(
  state => state.user.settings.locale,
  (newValue, oldValue) => {
    i18n.locale = newValue;
  }
);

export default i18n;
