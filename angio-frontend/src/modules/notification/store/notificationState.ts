export interface NotificationState {
    fetching: boolean;
    list: Notification[];
}

export interface Notification {
    id: string;
    date: Date;
    type: NotificationType;
    body: string;
    subject: Subject;
    read: boolean;
}

export enum NotificationType {
    SUCCESS = 'SUCCESS',
    INFO = 'INFO',
    WARNING = 'WARNING',
    ERROR = 'ERROR'
}

export enum Subject {
    COMMON = 'COMMON',
    USER = 'USER',
    ANALYSE = 'ANALYSE'
}