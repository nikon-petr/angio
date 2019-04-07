import root from 'loglevel';
import {ActionContext} from 'vuex';
import {RootState} from '@/store';
import {NotificationState, Notification} from '@/modules/notification/store/notificationState';
import {namespace} from 'vuex-class';
import {getStoreAccessors} from 'vuex-typescript';
import {NotificationApiService} from '@/modules/notification/services/notificationApiService';

const log = root.getLogger('store/modules/notification');

type NotificationContext = ActionContext<NotificationState, RootState>;

export const notification = {
    namespaced: true,

    state: () => ({
        fetching: false,
        list: []
    }),

    mutations: {
        startFetching(state: NotificationState) {
            state.fetching = true;
        },
        endFetching(state: NotificationState) {
            state.fetching = false;
        },
        clearNotifications(state: NotificationState) {
            state.list = [];
        },
        setNotifications(state: NotificationState, notifications: Notification[]) {
            state.list = notifications;
        },
        addNotification(state: NotificationState, notification: Notification) {
            state.list.unshift(notification);
        }
    },

    getters: {
        hasUnreadNotifications(state: NotificationState): boolean {
            return state.list.some((notification) => !notification.read);
        },
    },

    actions: {
        async fetchNotifications(ctx: NotificationContext) {
            startFetching(ctx);
            NotificationApiService.getNotifications()
                .then((notificationResponse) => {
                    setNotifications(ctx, notificationResponse.data.data);
                })
                .catch((error) => log.error(error))
                .then(() => endFetching(ctx));
        }
    }
};

const {commit, read, dispatch} = getStoreAccessors<NotificationState, RootState>('notification');

// getters
export const hasUnreadNotifications = read(notification.getters.hasUnreadNotifications);

// mutations
export const startFetching = commit(notification.mutations.startFetching);
export const endFetching = commit(notification.mutations.endFetching);
export const clearNotifications = commit(notification.mutations.clearNotifications);
export const setNotifications = commit(notification.mutations.setNotifications);
export const addNotification = commit(notification.mutations.addNotification);

// actions
export const fetchNotifications = dispatch(notification.actions.fetchNotifications);

// module
export const notificationModule = namespace('notification');
