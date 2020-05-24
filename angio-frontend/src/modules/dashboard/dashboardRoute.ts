import Dashboard from '@/modules/dashboard/views/Dashboard.vue';
import {RouteConfig} from 'vue-router';
import {AuthPredicate} from '@/modules/common/helpers/authPredicate';
import {UserPermission} from '@/modules/user/store/userState';

export const dashboardRouteConfig: RouteConfig[] = [
    {
        path: '/dashboard',
        name: 'dashboard',
        component: Dashboard,
        meta: {
            title: 'dashboard.view.dashboard.title',
            auth: AuthPredicate.hasPermissions([UserPermission.DASHBOARD_VIEW])
        },
    }
];
