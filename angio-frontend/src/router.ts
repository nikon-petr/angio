import Vue from 'vue';
import root, {Logger} from 'loglevel';
import Router, {Route, RouteConfig} from 'vue-router';
import {AuthPredicate} from '@/modules/common/helpers/authPredicate';
import {userRouterConfig} from '@/modules/user/userRouter';
import NotFound from '@/modules/common/views/404.vue';
import store from '@/store';
import ServerError from '@/modules/common/views/500.vue';
import i18n from '@/plugins/i18n';
import About from '@/modules/common/views/About.vue';
import {analyseRouterConfig} from '@/modules/analyse/analyseRouter';
import {hasAnyOfGivenPermissions, hasPermissions} from '@/modules/user/store/userStore';
import {UserPermission} from '@/modules/user/store/userState';

const log: Logger = root.getLogger('router');

Vue.use(Router);

let rootRouterConfig: RouteConfig[] = [
    {path: '/', alias: '/home', redirect: homeRedirect},
    {path: '/about', component: About, meta: {title: 'common.view.about.title', auth: AuthPredicate.permitAll()}},
    {path: '/500', component: ServerError, meta: {title: 'common.view.500.title', auth: AuthPredicate.permitAll()}},
    {path: '/404', component: NotFound, meta: {title: 'common.view.404.title', auth: AuthPredicate.permitAll()}},
    {path: '*', redirect: '/404', meta: {auth: AuthPredicate.permitAll()}},
];

rootRouterConfig = rootRouterConfig.concat(userRouterConfig, analyseRouterConfig);

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
    if (to.meta.title) {
        document.title = `${i18n.t(to.meta.title).toString()} | AngioVision`;
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

// home redirect
function homeRedirect (to: Route) {
    if(hasPermissions(store)([UserPermission.ANALYSE_VIEW])) {
        log.debug('redirect to /analyse');
        return {path: '/analyse', replace: true};
    } else if (hasAnyOfGivenPermissions(store)([
        UserPermission.USER_VIEW,
        UserPermission.ORGANIZATION_VIEW,
        UserPermission.TOKEN_VIEW,
        UserPermission.ANALYSE_PURGE_DELETED,
        UserPermission.IMAGE_UPLOAD_PURGE_UNUSED
    ])) {
        log.debug('redirect to /dashboard');
        return {path: '/dashboard', replace: true};
    } else {
        log.debug('redirect to /landing');
        return {path: '/landing', replace: true};
    }
}

export default rootRouter;
