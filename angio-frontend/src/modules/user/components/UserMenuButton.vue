<template>
    <div class="text-xs-center">
        <v-menu
                min-width="400"
                max-width="300"
                offset-y
                offset-x
                nudge-bottom="25"
                bottom
        >
            <template v-slot:activator="{ on }">
                <v-btn icon flat v-on="on">
                    <NotificationBadge
                            v-bind:value="hasUnreadNotifications">
                        <v-icon>person</v-icon>
                    </NotificationBadge>
                </v-btn>
            </template>
            <UserMenu
                    v-bind:user="user"
                    v-bind:logout="logout"
                    v-bind:notifications="notification.list"
                    v-bind:read-notifications="readNotifications"
            ></UserMenu>
        </v-menu>
    </div>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import {Action, Getter, State} from 'vuex-class';

    import UserMenu from '@/modules/user/components/UserMenu.vue';
    import NotificationBadge from '@/modules/notification/components/NotificationBadge.vue';
    import {UserState} from '@/modules/user/store/userState';
    import {UserAction} from '@/modules/user/store/userStore';
    import {NotificationState} from '@/modules/notification/store/notificationState';
    import {NotificationAction, NotificationGetter} from '@/modules/notification/store/notificationStore';

    @Component({
        components: {
            UserMenu,
            NotificationBadge,
        },
    })
    export default class UserMenuButton extends Vue {

        @State((state) => state.notification)
        public readonly notification!: NotificationState;

        @Action(NotificationAction.READ_NOTIFICATIONS)
        public readonly readNotifications!: (ids: string[]) => Promise<void>;

        @Getter(NotificationGetter.HAS_UNREAD_NOTIFICATIONS)
        public readonly hasUnreadNotifications!: boolean;

        @State((state) => state.user)
        public readonly user!: UserState;

        @Action(UserAction.LOGOUT)
        public readonly logout!: () => Promise<void>;
    }
</script>

<style scoped>

</style>