import {Notification, NotificationType, Subject} from '@/modules/notification/store/notificationState';

export interface NotificationModel extends Notification {
    id: string;
    date: Date;
    type: NotificationType;
    body: string;
    subject: Subject;
    read: boolean;
}

export interface NewNotificationModel<T> {
    date?: Date;
    type: NotificationType;
    templateName: Templates;
    subject: Subject;
    dataModel: T;
}

export interface PlainTextDataModel {
    text: string;
}

export enum Templates {
    PLAIN_TEXT = 'plain-text.ftl'
}
