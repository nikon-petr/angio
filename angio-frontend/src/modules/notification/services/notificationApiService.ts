import axios, {AxiosPromise} from 'axios';
import root from 'loglevel';
import {Response} from '@/modules/common/models/response';
import {NotificationModel} from '@/modules/notification/models/notification';

const log = root.getLogger('api/user');

export class NotificationApiService {
    public static getNotifications(): AxiosPromise<Response<Array<NotificationModel>>> {
        log.debug(`create getNotifications request`);
        return axios.get<Response<Array<NotificationModel>>>('/notification/push');
    }

    public static watchNotification(): AxiosPromise<Response<NotificationModel>> {
        log.debug('create watchNotification request');
        return axios.get<Response<NotificationModel>>('/notification/push/watch');
    }
}
