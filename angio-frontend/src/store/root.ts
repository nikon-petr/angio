import Vue from 'vue';
import Vuex, {Store, StoreOptions} from 'vuex';
import {UserState} from '@/store/modules/user';
import createLogger from 'vuex/dist/logger';
import {user} from '@/store/modules/user';

Vue.use(Vuex);

export interface RootState {
    user: UserState;
}

export const storeOptions: StoreOptions<RootState> = {
    state: {
        user: user.state(),
    },
    modules: {
        user,
    },
    strict: process.env.NODE_ENV !== 'production',
    plugins: process.env.NODE_ENV !== 'production' ? [createLogger()] : [],
};

export default new Vuex.Store<RootState>(storeOptions);
