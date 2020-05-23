import {AuthPredicate} from '@/modules/common/helpers/authPredicate';
import RoleList from '@/modules/role/views/RoleList.vue';
import {UserPermission} from '@/modules/user/store/userState';
import {RouteConfig} from 'vue-router';

export const roleRouterConfig: RouteConfig[] = [
    {
        path: '/role',
        component: RoleList,
        meta: {
            title: 'role.view.roleList.title',
            auth: AuthPredicate.hasPermissions([UserPermission.ROLE_VIEW])
        },
    }
];
