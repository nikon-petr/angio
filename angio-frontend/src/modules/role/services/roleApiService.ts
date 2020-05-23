import {Response} from '@/modules/common/models/response';
import {Permission, Role, UpdateRoleModel} from '@/modules/role/models/role';
import axios, {AxiosPromise} from 'axios';
import root from 'loglevel';

export class RoleApiService {
    private static log = root.getLogger(RoleApiService.name);

    public static getAllRoles(): AxiosPromise<Response<Array<Role>>> {
        RoleApiService.log.debug('create getAllRoles request');
        return axios.get<Response<Array<Role>>>('/role');
    }

    public static createRole(role: UpdateRoleModel): AxiosPromise<Response<Role>> {
        RoleApiService.log.debug(`create createRole request with data ${JSON.stringify(role)}`);
        return axios.post('/role', role);
    }

    public static updateRole(roleId: number, role: UpdateRoleModel): AxiosPromise<Response<Role>> {
        RoleApiService.log.debug(`create updateRole request with data ${JSON.stringify(role)} for role ${roleId}`);
        return axios.patch(`/role/${roleId}`, role);
    }

    public static deleteRole(roleId: number): AxiosPromise<Response<void>> {
        RoleApiService.log.debug(`create deleteRole request for role ${roleId}`);
        return axios.delete(`/role/${roleId}`);
    }

    public static getAllPermissions(): AxiosPromise<Response<Array<Permission>>> {
        RoleApiService.log.debug('create getAllPermissions request');
        return axios.get<Response<Array<Permission>>>('/permission');
    }
}
