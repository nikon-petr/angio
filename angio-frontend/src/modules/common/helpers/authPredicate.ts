import store from '@/store';
import {Route} from 'vue-router';
import root, {Logger} from 'loglevel';
import {UserGetter} from '@/modules/user/store/userStore';
import {UserPermission} from '@/modules/user/store/userState';

export class AuthPredicate {

    private static log: Logger = root.getLogger(AuthPredicate.name);

    public static FORBIDDEN_PATH = '/403';
    public static HOME_PATH = '/';
    public static LOGIN_PATH = '/login';

    public static permitAll() {
        return (to: Route, from: Route, next: any): any => next();
    }

    public static isAnonymous() {
        return (to: Route, from: Route, next: any): any => {
            if (!store.getters[UserGetter.IS_ANONYMOUS]) {
                AuthPredicate.log.debug(`path ${to.path} only for anonymous users. redirect to default authenticated user page`);
                next({path: AuthPredicate.HOME_PATH});
            }
        };
    }

    public static isAuthenticated() {
        return (to: Route, from: Route, next: any): any => {
            if (!store.getters[UserGetter.IS_AUTHENTICATED]) {
                AuthPredicate.log.debug(`path ${to.path} only for authenticated users. redirect to default anonymous home page`);
                AuthPredicate.redirectToLogin(to, from, next);
            }
        };
    }

    public static hasAnyPermission() {
        return (to: Route, from: Route, next: any): any => {
            if (!store.getters[UserGetter.IS_AUTHENTICATED]) {
                AuthPredicate.redirectToLogin(to, from, next);
            } else if (!store.getters[UserGetter.HAS_ANY_PERMISSION]) {
                AuthPredicate.log.debug(`path ${to.path} only for users with any permissions. ` +
                    'redirect to forbidden page');
                next({path: AuthPredicate.FORBIDDEN_PATH});
            }
        };
    }

    public static hasAnyOfGivenPermissions(permissions: UserPermission[]) {
        return (to: Route, from: Route, next: any): any => {
            if (!store.getters[UserGetter.IS_AUTHENTICATED]) {
                AuthPredicate.redirectToLogin(to, from, next);
            } else if (!store.getters[UserGetter.HAS_ANY_OF_GIVEN_PERMISSIONS](permissions)) {
                AuthPredicate.log.debug(`path ${to.path} only for users with any of permissions: ${permissions}. ` +
                    'redirect to forbidden page');
                next({path: AuthPredicate.FORBIDDEN_PATH});
            }
        };
    }

    public static hasPermissions(permissions: UserPermission[]) {
        return (to: Route, from: Route, next: any): any => {
            if (!store.getters[UserGetter.IS_AUTHENTICATED]) {
                AuthPredicate.redirectToLogin(to, from, next);
            } else if (!store.getters[UserGetter.HAS_PERMISSIONS](permissions)) {
                AuthPredicate.log.debug(`path ${to.path} only for users with permissions: ${permissions.toString()}. ` +
                    'redirect to forbidden page');
                next({path: AuthPredicate.FORBIDDEN_PATH});
            }
        };
    }

    private static redirectToLogin(to: Route, from: Route, next: any) {
        AuthPredicate.log.debug(`path ${to.path} only for authenticated users. redirect to login page`);
        next({path: AuthPredicate.LOGIN_PATH, query: {next: to.fullPath}});
    }
}
