import {RouteConfig} from 'vue-router';
import Login from '@/modules/user/views/Login.vue';
import Forbidden from '@/modules/user/views/403.vue';
import {AuthPredicate} from '@/modules/common/helpers/authPredicate';
import AccountActivation from '@/modules/user/views/AccountActivation.vue';
import ResetPassword from '@/modules/user/views/ResetPassword.vue';
import ResetAccount from '@/modules/user/views/ResetAccount.vue';

export const userRouterConfig: RouteConfig[] = [
    {
        path: '/login',
        component: Login,
        meta: {title: 'user.view.login.title', auth: AuthPredicate.isAnonymous()},
        props: (route) => ({ next: route.query.next })
    },
    {
        path: '/activation',
        component: AccountActivation,
        meta: {title: 'user.view.activation.title', auth: AuthPredicate.isAnonymous()},
        props: (route) => ({ userId: route.query.userId })
    },
    {
        path: '/user/reset',
        component: ResetPassword,
        meta: {title: 'user.view.resetPassword.title', auth: AuthPredicate.isAnonymous()}
    },
    {
        path: '/reset',
        component: ResetAccount,
        meta: {title: 'user.view.resetAccount.title', auth: AuthPredicate.isAnonymous()},
        props: (route) => ({ userId: route.query.userId })
    },
    {path: '/403', component: Forbidden, meta: {title: 'user.view.403.title', auth: AuthPredicate.permitAll()}},
];
