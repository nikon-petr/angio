import store from '@/store';
import {
    hasAnyOfGivenPermissions,
    hasAnyPermission,
    hasPermissions,
    isAnonymous,
    isAuthenticated,
} from '@/modules/user/store/userStore';
import root, {Logger} from 'loglevel';
import {Route} from 'vue-router';
import {UserPermission} from '@/modules/user/store/userState';

const log: Logger = root.getLogger('router');

export class AuthPredicate {

    public static permitAll() {
        return (to: Route, from: Route, next: any): any => next();
    }

    public static isAnonymous() {
        return (to: Route, from: Route, next: any): any => {
            if (!isAnonymous(store)) {
                log.debug(`path ${to.path} only for anonymous users. redirect to default authenticated user page`);
                next({path: '/home'});
            }
        };
    }

    public static isAuthenticated() {
        return (to: Route, from: Route, next: any): any => {
            if (!isAuthenticated(store)) {
                log.debug(`path ${to.path} only for authenticated users. redirect to login page`);
                next({path: '/login'});
            }
        };
    }

    public static hasAnyPermission() {
        return (to: Route, from: Route, next: any): any => {
            if (!hasAnyPermission(store)) {
                log.debug(`path ${to.path} only for users with any permissions. ` +
                    'redirect to forbidden page');
                next({path: '/forbidden'});
            }
        };
    }

    public static hasAnyOfGivenPermissions(permissions: UserPermission[]) {
        return (to: Route, from: Route, next: any): any => {
            if (!hasAnyOfGivenPermissions(store)(permissions)) {
                log.debug(`path ${to.path} only for users with any of permissions: ${permissions}. ` +
                    'redirect to forbidden page');
                next({path: '/forbidden'});
            }
        };
    }

    public static hasPermissions(permissions: UserPermission[]) {
        return (to: Route, from: Route, next: any): any => {
            if (!hasPermissions(store)(permissions)) {
                log.debug(`path ${to.path} only for users with permissions: ${permissions.toString()}. ` +
                    'redirect to forbidden page');
                next({path: '/forbidden'});
            }
        };
    }
}
