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

    public static FORBIDDEN_PATH = '/403';
    public static HOME_PATH = '/';
    public static LOGIN_PATH = '/login';

    public static permitAll() {
        return (to: Route, from: Route, next: any): any => next();
    }

    public static isAnonymous() {
        return (to: Route, from: Route, next: any): any => {
            if (!isAnonymous(store)) {
                log.debug(`path ${to.path} only for anonymous users. redirect to default authenticated user page`);
                next({path: AuthPredicate.HOME_PATH});
            }
        };
    }

    public static isAuthenticated() {
        return (to: Route, from: Route, next: any): any => {
            if (!isAuthenticated(store)) {
                AuthPredicate.redirectToLogin(to, from, next);
            }
        };
    }

    public static hasAnyPermission() {
        return (to: Route, from: Route, next: any): any => {
            if (!isAuthenticated(store)) {
                AuthPredicate.redirectToLogin(to, from, next);
            } else if (!hasAnyPermission(store)) {
                log.debug(`path ${to.path} only for users with any permissions. ` +
                    'redirect to forbidden page');
                next({path: AuthPredicate.FORBIDDEN_PATH});
            }
        };
    }

    public static hasAnyOfGivenPermissions(permissions: UserPermission[]) {
        return (to: Route, from: Route, next: any): any => {
            if (!isAuthenticated(store)) {
                AuthPredicate.redirectToLogin(to, from, next);
            } else if (!hasAnyOfGivenPermissions(store)(permissions)) {
                log.debug(`path ${to.path} only for users with any of permissions: ${permissions}. ` +
                    'redirect to forbidden page');
                next({path: AuthPredicate.FORBIDDEN_PATH});
            }
        };
    }

    public static hasPermissions(permissions: UserPermission[]) {
        return (to: Route, from: Route, next: any): any => {
            if (!isAuthenticated(store)) {
                AuthPredicate.redirectToLogin(to, from, next);
            } else if (!hasPermissions(store)(permissions)) {
                log.debug(`path ${to.path} only for users with permissions: ${permissions.toString()}. ` +
                    'redirect to forbidden page');
                next({path: AuthPredicate.FORBIDDEN_PATH});
            }
        };
    }

    private static redirectToLogin(to: Route, from: Route, next: any) {
        log.debug(`path ${to.path} only for authenticated users. redirect to login page`);
        next({path: AuthPredicate.LOGIN_PATH, query: {next: to.fullPath}});
    }
}
