import axios, {AxiosPromise} from 'axios';
import root from 'loglevel';
import {Response} from '@/modules/common/models/response';
import Page from '@/modules/common/models/page';
import {Organization} from '@/modules/organization/models/organization';

export class OrganizationApiService {
    private static log = root.getLogger('OrganizationApiService');

    public static getOrganizationsFilter(size?: number, page?: number, sort?: string, search?: string): AxiosPromise<Response<Page<Organization>>> {
        OrganizationApiService.log.debug(`create getOrganizationsFilter request`);
        return axios.get<Response<Page<Organization>>>('/organization', {params: {search, size, page, sort}});
    }

    public static createOrganization(name: string): AxiosPromise<Response<Organization>> {
        OrganizationApiService.log.debug(`create createOrganization request`);
        return axios.post('/organization', {name});
    }

    public static lockOrganization(id: string, locked: boolean): AxiosPromise<Response<Organization>> {
        OrganizationApiService.log.debug(`create lockOrganization request`);
        return axios.post(`/organization/${id}/locked`, {locked});
    }
}
