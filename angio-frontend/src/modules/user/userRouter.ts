import {RouteConfig} from 'vue-router';
import Login from '@/modules/user/views/Login.vue';
import Forbidden from '@/modules/user/views/403.vue';
import {AuthPredicate} from '@/modules/common/helpers/authPredicate';

export const userRouterConfig: RouteConfig[] = [
    {
        path: '/login',
        component: Login,
        meta: {title: 'user.view.login.title', auth: AuthPredicate.isAnonymous()},
        props: (route) => ({ next: route.query.next })
    },
    {path: '/403', component: Forbidden, meta: {title: 'user.view.403.title', auth: AuthPredicate.permitAll()}},
];
