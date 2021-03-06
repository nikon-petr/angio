import Vue from 'vue';
import moment from 'moment';
import 'moment/locale/ru';
// @ts-ignore
import VueMoment from 'vue-moment';

Vue.use(VueMoment, {moment});

declare module 'vue/types/vue' {
    export interface Vue {
        $moment: typeof moment
    }
    export interface VueConstructor {
        moment: typeof moment
    }
}
