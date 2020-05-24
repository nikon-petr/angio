import {Response} from '@/modules/common/models/response';
import {DashboardModel} from '@/modules/dashboard/models/dashboard';
import axios, {AxiosPromise} from 'axios';
import root from 'loglevel';

const log = root.getLogger('api/dashboard');

export class DashboardApiService {
    public static getDashboard(): AxiosPromise<Response<DashboardModel>> {
        log.debug(`create getDashboard request`);
        return axios.get<Response<DashboardModel>>('/dashboard');
    }
}
