import {Response} from '@/modules/common/models/response';
import {DashboardModel} from '@/modules/dashboard/models/dashboard';
import axios, {AxiosPromise} from 'axios';
import root from 'loglevel';

export class DashboardApiService {
    private static log = root.getLogger('DashboardApiService');

    public static getDashboard(): AxiosPromise<Response<DashboardModel>> {
        DashboardApiService.log.debug(`create getDashboard request`);
        return axios.get<Response<DashboardModel>>('/dashboard');
    }
}
