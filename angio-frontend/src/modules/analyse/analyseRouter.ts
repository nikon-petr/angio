import {RouteConfig} from 'vue-router';
import AnalyseList from '@/modules/analyse/views/AnalyseList.vue';
import {AuthPredicate} from '@/modules/common/helpers/authPredicate';
import {UserPermission} from '@/modules/user/store/userState';
import AnalyseDetails from "@/modules/analyse/views/AnalyseDetails.vue";

export const analyseRouterConfig: RouteConfig[] = [
    {
        path: '/analyse',
        component: AnalyseList,
        meta: {
            title: 'analyse.view.analyseList.title',
            auth: AuthPredicate.hasPermissions([UserPermission.ANALYSE_VIEW])
        }
    },
    {
        path: '/analyse/:analyseId',
        component: AnalyseDetails,
        props: (route) => ({ analyseId: Number(route.params.analyseId) }),
        meta: {
            auth: AuthPredicate.hasPermissions([UserPermission.ANALYSE_VIEW])
        }
    }
];
