export interface Response<D> {
    date: Date;
    status: ResponseStatus;
    data: D;
}

export enum ResponseStatus {
    SUCCESS = 'SUCCESS',
    ERROR = 'ERROR',
}
