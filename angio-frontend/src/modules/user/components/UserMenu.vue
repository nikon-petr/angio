<template>
    <v-card>
        <UserMenuAvatar
                v-bind:full-name="user.info.fullName"
                v-bind:organization="user.info.organizationName"
        ></UserMenuAvatar>

        <UserMenuActions
                v-bind:logout="logout"
        ></UserMenuActions>

        <NotificationScroller
                v-bind:notifications="notifications"
        ></NotificationScroller>
    </v-card>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import UserMenuAvatar from '@/modules/user/components/UserMenuAvatar.vue';
    import UserMenuActions from '@/modules/user/components/UserMenuActions.vue';
    import {UserInfo} from '@/modules/user/store/userState';
    import NotificationScroller from '@/modules/notification/components/NotificationScroller.vue';

    @Component({
        components: {
            NotificationScroller,
            UserMenuActions,
            UserMenuAvatar,
        },
    })
    export default class UserMenu extends Vue {

        @Prop()
        public readonly user!: UserInfo;

        @Prop()
        public readonly logout!: () => any;

        @Prop()
        public readonly notifications!: Notification[];

    }
</script>

<style scoped>

</style>