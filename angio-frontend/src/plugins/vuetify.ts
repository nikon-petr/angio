import Vue from 'vue';
// @ts-ignore
import Vuetify from 'vuetify/lib';
import 'vuetify/src/stylus/app.styl';
import i18n from '@/plugins/i18n';
import {VuetifyLanguage} from 'vuetify/types/lang';

Vue.use(Vuetify, {
    lang: {
        t: (key, ...params) => i18n.t(key, params),
    } as VuetifyLanguage,
    iconfont: 'md',
    options: {
        customProperties: true
    }
});

declare module 'vue/types/vue' {
    export interface Vue {
        $vuetify: Vuetify;
    }
}
