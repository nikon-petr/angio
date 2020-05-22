import {RouteConfig} from 'vue-router';
import {AuthPredicate} from '@/modules/common/helpers/authPredicate';
import {UserPermission} from '@/modules/user/store/userState';
import OrganizationList from '@/modules/organization/views/OrganizationList.vue';

export const organizationRouteConfig: RouteConfig[] = [
    {
        path: '/organization',
        name: 'organization-list',
        component: OrganizationList,
        meta: {
            title: 'organization.view.organizationList.title',
            auth: AuthPredicate.hasPermissions([UserPermission.ORGANIZATION_VIEW])
        },
    }
];
