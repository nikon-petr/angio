import Vue from 'vue';
import Router, {RouteConfig} from 'vue-router';
import {AuthPredicate} from '@/modules/common/helpers/authPredicate';
import {userRouterConfig} from '@/modules/user/userRouter';

Vue.use(Router);

let rootRouterConfig: RouteConfig[] = [
    {path: '*', redirect: '/notfound', meta: {auth: AuthPredicate.permitAll()}},
];

rootRouterConfig = rootRouterConfig.concat(userRouterConfig);

const rootRouter: Router = new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: rootRouterConfig,
});

rootRouter.beforeEach((to, from, next) => {
    for (const record of to.matched) {
        if (!!record.meta.auth) {
            record.meta.auth(to, from, next);
        }
    }
    next();
});

export default rootRouter;
