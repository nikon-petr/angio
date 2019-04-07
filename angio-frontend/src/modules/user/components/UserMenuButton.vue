<template lang="html" xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
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
                            v-bind:value="hasUnreadNotifications()">
                        <v-icon>person</v-icon>
                    </NotificationBadge>
                </v-btn>
            </template>
            <UserMenu
                    v-bind:user="user"
                    v-bind:logout="logout"
                    v-bind:notifications="notification.list"
            ></UserMenu>
        </v-menu>
    </div>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';

    import UserMenu from '@/modules/user/components/UserMenu.vue';
    import NotificationBadge from '@/modules/notification/components/NotificationBadge.vue';
    import {UserState} from '@/modules/user/store/userState';
    import {logout} from '@/modules/user/store/userStore';
    import {State} from 'vuex-class';
    import {NotificationState} from "@/modules/notification/store/notificationState";
    import {hasUnreadNotifications} from "@/modules/notification/store/notificationStore";

    @Component({
        components: {
            UserMenu,
            NotificationBadge,
        },
    })
    export default class UserMenuButton extends Vue {

        @State((state) => state.user)
        public readonly user!: UserState;

        @State((state) => state.notification)
        public readonly notification!: NotificationState;

        public hasUnreadNotifications(): boolean {
            return hasUnreadNotifications(this.$store);
        }

        public logout() {
            logout(this.$store);
        }
    }
</script>

<style scoped>

</style>