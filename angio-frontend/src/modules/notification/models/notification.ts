import {NotificationType, Subject} from '@/modules/notification/store/notificationState';

export interface NotificationModel {
    id: string;
    date: Date;
    type: NotificationType;
    body: string;
    subject: Subject;
    read: boolean;
}