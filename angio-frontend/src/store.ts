import Vue from 'vue';
import Vuex, {Store, StoreOptions} from 'vuex';
import {UserState} from '@/modules/user/store/userState';
import createLogger from 'vuex/dist/logger';
import {user} from '@/modules/user/store/userStore';
import {NotificationState} from '@/modules/notification/store/notificationState';
import {notification} from '@/modules/notification/store/notificationStore';

Vue.use(Vuex);

export interface RootState {
    user: UserState;
    notification: NotificationState;
}

export const storeOptions: StoreOptions<RootState> = {
    state: {
        user: user.state(),
        notification: notification.state(),
    },
    modules: {
        user,
        notification,
    },
    strict: process.env.NODE_ENV !== 'production',
    plugins: process.env.NODE_ENV !== 'production' ? [createLogger()] : [],
};

export default new Vuex.Store<RootState>(storeOptions);
