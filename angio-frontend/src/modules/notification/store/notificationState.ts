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

export interface Subject {
    name: SubjectName
}

export enum NotificationType {
    SUCCESS = 'SUCCESS',
    INFO = 'INFO',
    WARNING = 'WARNING',
    ERROR = 'ERROR'
}

export enum SubjectName {
    COMMON = 'COMMON',
    USER = 'USER',
    ANALYSE = 'ANALYSE'
}