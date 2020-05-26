import axios, {AxiosPromise, AxiosRequestConfig} from 'axios';
import {Response} from '@/modules/common/models/response';
import StaticFile from '@/modules/common/models/staticFile';
import root from 'loglevel';

const log = root.getLogger('api/upload');

export class FileUploadService {
    public static uploadImage(
        formData: FormData,
        process: (length: number, loaded: number, total: number) => void): AxiosPromise<Response<StaticFile>> {
        log.debug(`create getAnalyseFilter request with data ${JSON.stringify(formData)}`);
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

    public static purgeUploadedImages(): AxiosPromise<Response<number>> {
        log.debug('create purgeUploadedImages request');
        return axios.delete('/upload/image');
    }
}
