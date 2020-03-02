import Vue from 'vue';
import {truncate, placeholder, round} from '@/filters/text-filters';

Vue.filter('truncate', truncate);
Vue.filter('placeholder', placeholder);
Vue.filter('round', round);
