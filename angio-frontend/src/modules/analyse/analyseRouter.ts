import {RouteConfig} from 'vue-router';
import AnalyseList from '@/modules/analyse/views/AnalyseList.vue';
import {AuthPredicate} from '@/modules/common/helpers/authPredicate';
import {UserPermission} from '@/modules/user/store/userState';
import AnalyseDetails from '@/modules/analyse/views/AnalyseDetails.vue';
import AnalyseNew from '@/modules/analyse/views/AnalyseNew.vue';

export const analyseRouterConfig: RouteConfig[] = [
    {
        path: '/analyse',
        name: 'analyse-list',
        component: AnalyseList,
        meta: {
            title: 'analyse.view.analyseList.title',
            auth: AuthPredicate.hasPermissions([UserPermission.ANALYSE_VIEW])
        },
    },
    {
        path: '/analyse/new',
        component: AnalyseNew,
        meta: {
            title: 'analyse.view.analyseNew.title',
            auth: AuthPredicate.hasPermissions([
                UserPermission.ANALYSE_CREATE,
                UserPermission.PATIENT_CREATE,
                UserPermission.PATIENT_VIEW,
                UserPermission.IMAGE_UPLOAD,
                UserPermission.VIDEO_UPLOAD
            ])
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
