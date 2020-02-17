import Vue from 'vue';
import {truncate, placeholder} from '@/filters/text-filters';

Vue.filter('truncate', truncate);
Vue.filter('placeholder', placeholder);
