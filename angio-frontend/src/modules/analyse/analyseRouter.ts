import {RouteConfig} from 'vue-router';
import AnalyseList from '@/modules/analyse/views/AnalyseList.vue';
import {AuthPredicate} from '@/modules/common/helpers/authPredicate';
import {UserPermission} from '@/modules/user/store/userState';

export const analyseRouterConfig: RouteConfig[] = [
    {path: '/analyse', component: AnalyseList, meta: {auth: AuthPredicate.hasPermissions([UserPermission.ANALYSE_VIEW ])}}
];
