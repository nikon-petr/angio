import axios, {AxiosPromise} from 'axios';
import root from 'loglevel';
import {Response} from '@/modules/common/models/response';
import AnalyseItem, {Analyse, AnalyseFilterModel, AnalyseStarred} from '@/modules/analyse/models/analyse';
import Page from '@/modules/common/models/page';
import printJS from 'print-js';

const log = root.getLogger('api/analyse');

export class AnalyseApiService {
    public static getAnalyseFilter(filter: AnalyseFilterModel, size?: number, page?: number, sort?: string): AxiosPromise<Response<Page<AnalyseItem>>> {
        log.debug(`create getAnalyseFilter request with data ${JSON.stringify(filter)}`);
        return axios.get<Response<Page<AnalyseItem>>>('/analyse', {params: {...filter, size, page, sort}});
    }

    public static setAnalyseStarred(id: number, starred: boolean): AxiosPromise<Response<AnalyseStarred>> {
        log.debug(`create setAnalyseStarred request starred: ${starred} for analyse id=${id}`);
        return axios.post<Response<AnalyseStarred>>(`/analyse/${id}/starred`, {starred})
    }

    public static deleteAnalyse(id: number): AxiosPromise<Response<any>> {
        log.debug(`create deleteAnalyse request for analyse id=${id}`);
        return axios.delete(`/analyse/${id}`);
    }

    public static printAnalyseReport(id: number): Promise<void> {
        return new Promise<void>((resolve, reject) => {
            log.debug(`print analyse #${id}`);
            axios.get(`/analyse/${id}/report`, {responseType: 'arraybuffer'})
                .then((response) => {
                    // @ts-ignore
                    printJS({
                        printable: Buffer.from(response.data, 'binary').toString('base64'),
                        type: 'pdf',
                        base64: true,
                        onPrintDialogClose: () => resolve()
                    });
                })
                .catch((error) => {
                    log.error(error);
                    reject();
                });
        });
    }

    public static downloadArchive(id: number): Promise<void> {
        return new Promise<void>(((resolve, reject) => {
            log.debug(`create downloadArchive request for analyse id=${id}`);
            axios.get(`/analyse/${id}/archive`, {responseType: 'arraybuffer'})
                .then((response) => {
                    const url = window.URL.createObjectURL(new Blob([response.data]));
                    const link = document.createElement('a');
                    link.href = url;
                    link.setAttribute('download', `analyse_${id}.zip`);
                    document.body.appendChild(link);
                    link.click();
                    resolve();
                })
                .catch((error) => {
                    log.error(error);
                    reject();
                })
        }))
    }

    public static getAnalyseById(id: number): AxiosPromise<Response<Analyse>> {
        log.debug(`create getAnalyseById request with id ${id}`);
        return axios.get(`/analyse/${id}`);
    }
}