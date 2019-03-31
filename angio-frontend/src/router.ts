import Vue from 'vue';
import Router, {RouteConfig} from 'vue-router';
import {AuthPredicate} from '@/modules/common/helpers/authPredicate';
import {userRouterConfig} from '@/modules/user/userRouter';
import NotFound from "@/modules/common/views/NotFound.vue";
import store from "@/store";

Vue.use(Router);

let rootRouterConfig: RouteConfig[] = [
    {path: '/notfound', component: NotFound},
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

// redirect when user login or logout
store.watch(((state, getters) => getters['user/isAuthenticated']), (newValue, oldValue) => {
    if (newValue) {
        rootRouter.push({path: '/'})
    } else {
        rootRouter.push({path: '/login'});
    }
});

export default rootRouter;
