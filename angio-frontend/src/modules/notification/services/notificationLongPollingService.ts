import Vue from 'vue';
import root from 'loglevel';
import store from '@/store';
import {NotificationApiService} from '@/modules/notification/services/notificationApiService';
import NotificationSoundService from '@/modules/notification/services/notificationSoundService';
import {NotificationMutation} from '@/modules/notification/store/notificationStore';

const log = root.getLogger('NotificationLongPollingService');

export default class NotificationLongPollingService {

    private _pollingEnabled: boolean = false;
    private _watching: boolean = false;

    private static _instance: NotificationLongPollingService;

    private constructor() {
    }

    public static getInstance() {
        return this._instance || (this._instance = new this());
    }

    public async startPolling() {

        this._pollingEnabled = true;

        if (this._watching) {
            throw new Error('watching push notifications already enabled');
        } else {
            this._watching = true;
        }

        NotificationApiService.watchNotification()
            .then((watchResponse) => {

                log.debug(`get new notification: ${JSON.stringify(watchResponse.data.data)}`);
                NotificationApiService.readNotification([watchResponse.data.data.id]);
                watchResponse.data.data.read = true;
                store.commit(NotificationMutation.ADD_NOTIFICATION, watchResponse.data.data);
                Vue.notify({data: watchResponse.data.data});
                NotificationSoundService.getInstance().playNewNotificationSound();
                Vue.bus.emit(watchResponse.data.data.subject.name);

                this._watching = false;
                if (this._pollingEnabled) {
                    this.startPolling();
                }
            })
            .catch((error) => {
                    log.error(error);
                    this._watching = false;
                    if (this._pollingEnabled) {
                        setTimeout(() => {
                            this.startPolling();
                        }, 30000);
                    }
                }
            );
    }

    public stopPolling() {
        this._pollingEnabled = false;
    }
}
