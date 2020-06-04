import {RouteConfig} from 'vue-router';
import Login from '@/modules/user/views/Login.vue';
import Forbidden from '@/modules/user/views/403.vue';
import {AuthPredicate} from '@/modules/common/helpers/authPredicate';
import AccountActivation from '@/modules/user/views/AccountActivation.vue';
import ResetPassword from '@/modules/user/views/ResetPassword.vue';
import ResetAccount from '@/modules/user/views/ResetAccount.vue';
import Settings from '@/modules/user/views/Settings.vue';
import Registration from '@/modules/user/views/Registration.vue';
import ChangePassword from "@/modules/user/views/ChangePassword.vue";
import UserList from '@/modules/user/views/UserList.vue';
import {UserPermission} from '@/modules/user/store/userState';

export const userRouterConfig: RouteConfig[] = [
    {
        path: '/login',
        component: Login,
        meta: {title: 'user.view.login.title', auth: AuthPredicate.isAnonymous()},
        props: (route) => ({ next: route.query.next })
    },
    {
        path: '/register',
        component: Registration,
        meta: {title:'user.view.registration.title', auth: AuthPredicate.isAnonymous()}
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
    {
        path: '/user/settings',
        component: Settings,
        meta: {title: 'user.view.settings.title', auth: AuthPredicate.isAuthenticated()}
    },
    {
        path: '/user/password',
        component: ChangePassword,
        meta: {title: 'user.view.changePassword.title', auth: AuthPredicate.isAuthenticated()}
    },
    {
        path: '/user',
        component: UserList,
        meta: {
            title: 'user.view.userManager.title',
            auth: AuthPredicate.hasPermissions([
                UserPermission.USER_CREATE,
                UserPermission.USER_VIEW,
                UserPermission.USER_EDIT
            ])}
    },
    {path: '/403', component: Forbidden, meta: {title: 'user.view.403.title', auth: AuthPredicate.permitAll()}},
];
