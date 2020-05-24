import NewNotification from '@/modules/notification/views/NewNotification.vue';
import {RouteConfig} from 'vue-router';
import {AuthPredicate} from '@/modules/common/helpers/authPredicate';
import {UserPermission} from '@/modules/user/store/userState';

export const notificationRouteConfig: RouteConfig[] = [
    {
        path: '/notification/new',
        component: NewNotification,
        meta: {
            title: 'notification.view.newNotification.title',
            auth: AuthPredicate.hasPermissions([UserPermission.PUSH_NOTIFICATION_SEND])
        },
    }
];
