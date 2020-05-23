import axios, {AxiosPromise} from 'axios';
import root from 'loglevel';
import {Response} from '@/modules/common/models/response';
import Page from '@/modules/common/models/page';
import {Organization} from '@/modules/organization/models/organization';

const log = root.getLogger('api/organization');

export class OrganizationApiService {
    public static getOrganizationsFilter(size?: number, page?: number, sort?: string, search?: string): AxiosPromise<Response<Page<Organization>>> {
        log.debug(`create getOrganizationsFilter request`);
        return axios.get<Response<Page<Organization>>>('/organization', {params: {search, size, page, sort}});
    }

    public static createOrganization(name: string): AxiosPromise<Response<Organization>> {
        log.debug(`create createOrganization request`);
        return axios.post('/organization', {name: name});
    }

    public static lockOrganization(id: string, locked: boolean): AxiosPromise<Response<Organization>> {
        log.debug(`create lockOrganization request`);
        return axios.post(`/organization/${id}/locked`, {locked: locked});
    }
}
