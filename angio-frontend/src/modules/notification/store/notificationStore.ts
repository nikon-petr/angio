import Vue from 'vue';
import root from 'loglevel';
import {ActionContext} from 'vuex';
import {RootState} from '@/store';
import {NotificationState, Notification} from '@/modules/notification/store/notificationState';
import {NotificationApiService} from '@/modules/notification/services/notificationApiService';
import NotificationLongPollingService from "@/modules/notification/services/notificationLongPollingService";

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
        },
        setReadNotifications(state: NotificationState, ids: string[]) {
            state.list
                .filter((notification) => ids.includes(notification.id))
                .forEach((notification) => notification.read = true);
        }
    },

    getters: {
        hasUnreadNotifications(state: NotificationState): boolean {
            return state.list.some((notification) => !notification.read);
        },
    },

    actions: {
        async fetchNotifications(ctx: NotificationContext) {
            ctx.commit(NotificationMutation.START_FETCHING, undefined, {root: true});
            NotificationApiService.getNotifications()
                .then((notificationResponse) => {
                    ctx.commit(NotificationMutation.SET_NOTIFICATIONS, notificationResponse.data.data, {root: true});
                })
                .catch((error) => log.error(error))
                .then(() => ctx.commit(NotificationMutation.END_FETCHING, undefined, {root: true}));
        },
        async readNotifications(ctx: NotificationContext, ids: string[]) {
            NotificationApiService.readNotification(ids)
                .then((notificationReadResponse) => {
                    ctx.commit(NotificationMutation.SET_READ_NOTIFICATIONS, ids, {root: true});
                })
                .catch((error) => log.error(error));
        },
        async initiateNotificationPolling(ctx: NotificationContext) {
            ctx.dispatch(NotificationAction.FETCH_NOTIFICATIONS, undefined, {root: true});
            NotificationLongPollingService.getInstance().startPolling();
        },
        async downNotificationPolling(ctx: NotificationContext) {
            ctx.commit(NotificationMutation.CLEAR_NOTIFICATIONS, undefined, {root: true});
            NotificationLongPollingService.getInstance().stopPolling();
            Vue.notify({clean: true});
        },

    }
};

export enum NotificationMutation {
    START_FETCHING = 'notification/startFetching',
    END_FETCHING = 'notification/endFetching',
    CLEAR_NOTIFICATIONS = 'notification/clearNotifications',
    SET_NOTIFICATIONS = 'notification/setNotifications',
    ADD_NOTIFICATION = 'notification/addNotification',
    SET_READ_NOTIFICATIONS = 'notification/setReadNotifications'
}

export enum NotificationGetter {
    HAS_UNREAD_NOTIFICATIONS = 'notification/hasUnreadNotifications'
}

export enum NotificationAction {
    FETCH_NOTIFICATIONS = 'notification/fetchNotifications',
    READ_NOTIFICATIONS = 'notification/readNotifications',
    INITIATE_NOTIFICATION_POLLING = 'notification/initiateNotificationPolling',
    DOWN_NOTIFICATION_POLLING = 'notification/downNotificationPolling'
}
