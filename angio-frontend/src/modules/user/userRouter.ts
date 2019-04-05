import {RouteConfig} from 'vue-router';
import Login from '@/modules/user/views/Login.vue';
import Forbidden from '@/modules/user/views/403.vue';
import {AuthPredicate} from '@/modules/common/helpers/authPredicate';

export const userRouterConfig: RouteConfig[] = [
    {path: '/login', component: Login, meta: {auth: AuthPredicate.isAnonymous()}},
    {path: '/403', component: Forbidden, meta: {auth: AuthPredicate.permitAll()}},
];
