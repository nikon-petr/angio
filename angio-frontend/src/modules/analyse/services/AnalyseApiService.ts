import axios, {AxiosPromise} from 'axios';
import root from 'loglevel';
import {Response} from '@/modules/common/models/response';
import AnalyseItem, {AnalyseFilterModel} from '@/modules/analyse/models/analyse';
import Page from '@/modules/common/models/page';

const log = root.getLogger('api/analyse');

export class AnalyseApiService {
    public static getAnalyseFilter(filter: AnalyseFilterModel, size?: number, page?: number, sort?: string): AxiosPromise<Response<Page<AnalyseItem>>> {
        log.debug(`create getAnalyseFilter request with data ${JSON.stringify(filter)}`);
        return axios.get<Response<Page<AnalyseItem>>>('/analyse', {params: {...filter, size, page, sort}});
    }
}