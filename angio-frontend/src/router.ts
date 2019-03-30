import Vue from 'vue';
import Router from 'vue-router';
import Home from '@/views/Home.vue';
import NotFound from '@/components/NotFound.vue';
import root from 'loglevel';
import {Logger} from 'loglevel';
import {
    hasAnyOfGivenPermissions,
    hasAnyPermission,
    hasPermissions,
    isAnonymous,
    isAuthenticated,
} from '@/store/modules/user';
import store from '@/store/root';
import VuetifyHelloWorld from '@/components/VuetifyHelloWorld.vue';

Vue.use(Router);

const log: Logger = root.getLogger('router');

enum ConditionType {
    IS_ANONYMOUS = 'IS_ANONYMOUS',
    PREMIT_ALL = 'PREMIT_ALL',
    IS_AUTHENTIFICATED = 'IS_AUTHENTIFICATED',
    HAS_PERMISSIONS = 'HAS_PERMISSION',
    HAS_ANY_OF_PERMISSIONS = 'HAS_ANY_OF_PERMISSIONS',
    HAS_ANY_PERMISSION = 'HAS_ANY_PERMISSION',
}

export const router: Router = new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/home',
            name: 'home',
            component: Home,
        },
        {
            path: '/login',
            name: 'about',
        },
        {
            path: '/vuetify',
            component: VuetifyHelloWorld,
        },
        {
            path: '/403',
            name: 'about',
            meta: {
                auth: ConditionType.IS_AUTHENTIFICATED,
            },
        },
        {
            path: '/404',
            name: '404',
            component: NotFound,
        }, {
            path: '*',
            redirect: '/404'
        }
    ],
});

router.beforeEach((to, from, next) => {
    let result = 1;

    for (const record of to.matched) {
        if (
            record.meta.auth === ConditionType.IS_ANONYMOUS &&
            isAuthenticated(store)
        ) {
            result = 2;
        }

        if (
            record.meta.auth === ConditionType.IS_AUTHENTIFICATED &&
            isAnonymous(store)
        ) {
            result = 0;
        }

        if (
            record.meta.auth === ConditionType.HAS_PERMISSIONS &&
            record.meta.permissions !== undefined &&
            record.meta.permissions.length > 0
        ) {
            if (!hasPermissions(store)(record.meta.permissions)) {
                result = result > 0 ? -1 : result;
            }
        }

        if (
            record.meta.auth === ConditionType.HAS_ANY_OF_PERMISSIONS &&
            record.meta.permissions !== undefined &&
            record.meta.permissions.length > 0
        ) {
            if (
                !hasAnyOfGivenPermissions(store)(
                    record.meta.permissions,
                )
            ) {
                result = result > 0 ? -1 : result;
            }
        }

        if (
            record.meta.auth === ConditionType.HAS_ANY_PERMISSION &&
            !hasAnyPermission(store)
        ) {
            result = result > 0 ? -1 : result;
        }
    }

    switch (result) {
        case 2:
            log.debug(`route ${to.path} not permited. allowed only for guest users`);
            next({
                path: '/home',
            });
            break;
        case 1:
            log.debug(`route ${to.path} permited`);
            next();
            break;
        case 0:
            log.debug(`route ${to.path} not permited. need authentification`);
            next({
                path: '/login',
                params: {nextUrl: to.fullPath},
            });
            break;
        case -1:
            log.debug(`route ${to.path} not permited. permissions are missing`);
            next({
                path: '/403',
            });
            break;
    }
});

export default router;
