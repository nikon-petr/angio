import Vue from 'vue';
import VueI18n, {LocaleMessages} from 'vue-i18n';
import ls from 'local-storage';
import store from '@/store/root';

Vue.use(VueI18n);

function loadLocaleMessages(): LocaleMessages {
    const locales = require.context(
        '../locales',
        true,
        /[A-Za-z0-9-_,\s]+\.json$/i,
    );
    const messages: LocaleMessages = {};
    locales.keys().forEach((key) => {
        const matched = key.match(/([A-Za-z0-9-_]+)\./i);
        if (matched && matched.length > 1) {
            const locale = matched[1];
            messages[locale] = locales(key);
        }
    });
    return messages;
}

const i18n = new VueI18n({
    locale: ls.get('locale') || process.env.VUE_APP_I18N_LOCALE,
    fallbackLocale: process.env.VUE_APP_I18N_FALLBACK_LOCALE || 'en',
    messages: loadLocaleMessages(),
});

// Hot updates
if (module.hot) {
    module.hot.accept(['../locales/en', '../locales/ru'], () => {
        i18n.setLocaleMessage('en', require('../locales/en').default);
        i18n.setLocaleMessage('ru', require('../locales/ru').default);
    });
}

store.watch(
    (state) => state.user.settings.locale,
    (newValue, oldValue) => {
        i18n.locale = newValue;
    },
);


export default i18n;
