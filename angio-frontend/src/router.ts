import Vue from 'vue';
import Router from 'vue-router';
import Home from '@/views/Home.vue';
import NotFound from '@/modules/common/components/NotFound.vue';
import VuetifyHelloWorld from '@/modules/common/components/VuetifyHelloWorld.vue';
import {AuthPredicate} from '@/modules/common/helpers/authPredicate';
import {UserPermission} from '@/modules/user/store/userState';

Vue.use(Router);

export const router: Router = new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/home',
            name: 'home',
            component: Home,
            meta: {
                auth: AuthPredicate.hasPermissions([UserPermission.TOKEN_REVOKE]),
            },
        },
        {
            path: '/login',
            name: 'about',
            meta: {
                auth: AuthPredicate.permitAll(),
            },
        },
        {
            path: '/vuetify',
            component: VuetifyHelloWorld,
            meta: {
                auth: AuthPredicate.isAuthenticated(),
            },
        },
        {
            path: '/forbidden',
            name: 'about',
            meta: {
                auth: AuthPredicate.permitAll(),
            },
        },
        {
            path: '/notfound',
            name: '404',
            component: NotFound,
            meta: {
                auth: AuthPredicate.permitAll(),
            },
        }, {
            path: '*',
            redirect: '/notfound',
            meta: {
                auth: AuthPredicate.permitAll(),
            },
        },
    ],
});

router.beforeEach((to, from, next) => {
    for (const record of to.matched) {
        if (!!record.meta.auth) {
            record.meta.auth(to, from, next);
        }
    }
    next();
});

export default router;
