import axios, {AxiosPromise} from 'axios';
import root from 'loglevel';
import {Response} from '@/modules/common/models/response';
import {NewNotificationModel, NotificationModel} from '@/modules/notification/models/notification';

export class NotificationApiService {
    private static log = root.getLogger(NotificationApiService.name);

    public static getNotifications(): AxiosPromise<Response<Array<NotificationModel>>> {
        NotificationApiService.log.debug(`create getNotifications request`);
        return axios.get<Response<Array<NotificationModel>>>('/notification/push');
    }

    public static watchNotification(): AxiosPromise<Response<NotificationModel>> {
        NotificationApiService.log.debug('create watchNotification request');
        return axios.get<Response<NotificationModel>>('/notification/push/watch');
    }

    public static readNotification(notificationIds: string[]): AxiosPromise<Response<any>> {
        NotificationApiService.log.debug(`create readNotification request id=${JSON.stringify(notificationIds)}`);
        return axios.post<Response<any>>('/notification/push/read', notificationIds);
    }

    public static sendNotification<T>(newNotification: NewNotificationModel<T>, userIds?: string[]): AxiosPromise<Response<void>> {
        NotificationApiService.log.debug(`create sendNotification with data${JSON.stringify(newNotification)} for users ${userIds}`);
        return axios.post<Response<any>>('/notification/push', newNotification, {params: {userIds}});
    }
}
