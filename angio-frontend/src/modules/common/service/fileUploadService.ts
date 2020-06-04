import axios, {AxiosPromise, AxiosRequestConfig} from 'axios';
import {Response} from '@/modules/common/models/response';
import StaticFile from '@/modules/common/models/staticFile';
import root from 'loglevel';

export class FileUploadService {
    private static log = root.getLogger(FileUploadService.name);

    public static uploadImage(
        formData: FormData,
        process: (length: number, loaded: number, total: number) => void): AxiosPromise<Response<StaticFile>> {
        FileUploadService.log.debug(`create uploadImage request with data`);
        let config: AxiosRequestConfig = {
            headers: {
                'Content-Type': 'multipart/form-data'
            },
            onUploadProgress: progressEvent => {
                process(progressEvent.lengthComputable, progressEvent.loaded, progressEvent.total);
            }
        };
        return axios.post<Response<StaticFile>>('/upload/image', formData, config);
    }

    public static uploadVideo(
        formData: FormData,
        process: (length: number, loaded: number, total: number) => void): AxiosPromise<Response<StaticFile>> {
        FileUploadService.log.debug(`create uploadVideo request with data`);
        let config: AxiosRequestConfig = {
            headers: {
                'Content-Type': 'multipart/form-data'
            },
            onUploadProgress: progressEvent => {
                process(progressEvent.lengthComputable, progressEvent.loaded, progressEvent.total);
            }
        };
        return axios.post<Response<StaticFile>>('/upload/video', formData, config);
    }

    public static purgeUploadedImages(): AxiosPromise<Response<number>> {
        FileUploadService.log.debug('create purgeUploadedImages request');
        return axios.delete('/upload/image');
    }
}
