import Vue from 'vue';
import {
    truncate,
    placeholder,
    round,
    fullName,
    compactFullName,
    formatBoolean
} from '@/filters/text-filters';

Vue.filter('truncate', truncate);
Vue.filter('placeholder', placeholder);
Vue.filter('round', round);
Vue.filter('fullName', fullName);
Vue.filter('compactFullName', compactFullName);
Vue.filter('formatBoolean', formatBoolean);
