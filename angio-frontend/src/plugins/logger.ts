import Vue from 'vue';
import log, {LogLevelDesc, Logger} from 'loglevel';
import * as prefixer from 'loglevel-plugin-prefix';

log.setLevel(process.env.VUE_APP_LOGGING_LEVEL as LogLevelDesc, true);

prefixer.reg(log);
prefixer.apply(log, {
    template: '%t [%l] %n - ',
    levelFormatter(level) {
        return level.toUpperCase();
    },
    nameFormatter(name) {
        return name || 'store.ts';
    },
    timestampFormatter(date) {
        return date.toLocaleTimeString();
    },
});

const logger = log.getLogger('vue');
Vue.prototype.$logger = logger;
Vue.logger = logger;

declare module 'vue/types/vue' {
    export interface Vue {
        $logger: Logger;
    }
    export interface VueConstructor {
        logger: Logger
    }
}
