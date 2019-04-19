import Vue from 'vue';
import {NotificationApiService} from '@/modules/notification/services/notificationApiService';
import {NotificationModel} from '@/modules/notification/models/notification';
import root from 'loglevel';
import NotificationSoundService from '@/modules/notification/services/notificationSoundService';

const log = root.getLogger('NotificationLongPollingService');

export default class NotificationLongPollingService {

    private _notificationCallBack?: (notification: NotificationModel) => any;
    private _pollingEnabled: boolean = false;
    private _watching: boolean = false;

    private static _instance: NotificationLongPollingService;

    private constructor() {
    }

    public static getInstance() {
        return this._instance || (this._instance = new this());
    }

    public set notificationCallBack(notificationCallBack: (notification: NotificationModel) => any) {
        this._notificationCallBack = notificationCallBack;
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

                if (this._notificationCallBack) {
                    log.debug(`get new notification: ${JSON.stringify(watchResponse.data.data)}`);
                    this._notificationCallBack(watchResponse.data.data);
                    Vue.notify({
                        data: watchResponse.data.data
                    });
                    NotificationSoundService.getInstance().playNewNotificationSound();
                } else {
                    throw new Error('notification callback is not set');
                }

                this._watching = false;
                if (this._pollingEnabled) {
                    this.startPolling();
                }
            })
            .catch((error) => {
                    log.error(error);
                    this._watching = false;
                    setTimeout(() => {
                        if (this._pollingEnabled) {
                            this.startPolling();
                        }
                    }, 30000);
                }
            );
    }

    public stopPolling() {
        this._pollingEnabled = false;
    }
}
