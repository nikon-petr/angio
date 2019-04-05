import Vue from 'vue';
import Router, {RouteConfig} from 'vue-router';
import {AuthPredicate} from '@/modules/common/helpers/authPredicate';
import {userRouterConfig} from '@/modules/user/userRouter';
import NotFound from '@/modules/common/views/404.vue';
import store from '@/store';
import ServerError from '@/modules/common/views/500.vue';

Vue.use(Router);

let rootRouterConfig: RouteConfig[] = [
    {path: '/500', component: ServerError, meta: {auth: AuthPredicate.permitAll()}},
    {path: '/404', component: NotFound, meta: {auth: AuthPredicate.permitAll()}},
    {path: '*', redirect: '/404', meta: {auth: AuthPredicate.permitAll()}},
];

rootRouterConfig = rootRouterConfig.concat(userRouterConfig);

const rootRouter: Router = new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: rootRouterConfig,
});

// check AuthPredicate
rootRouter.beforeEach((to, from, next) => {
    for (const record of to.matched) {
        if (!!record.meta.auth) {
            record.meta.auth(to, from, next);
        }
    }
    next();
});

// redirect when user login or logout
store.watch(((state, getters) => getters['user/isAuthenticated']), (newValue, oldValue) => {
    if (newValue) {
        rootRouter.push({path: '/'});
    } else {
        rootRouter.push({path: '/login'});
    }
});

export default rootRouter;
