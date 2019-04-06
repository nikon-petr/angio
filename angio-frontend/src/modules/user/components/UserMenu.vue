<template>
    <v-card>
        <UserMenuAvatar
                v-bind:full-name="user.info.fullName"
                v-bind:organization="user.info.organizationName"
        ></UserMenuAvatar>

        <UserMenuActions
                v-bind:logout="logout"
        ></UserMenuActions>

        <v-subheader>Уведомления</v-subheader>

        <v-divider></v-divider>

        <UserMenuNotificationScroller
                v-bind:notifications="notifications"
        ></UserMenuNotificationScroller>
    </v-card>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import UserMenuAvatar from '@/modules/user/components/UserMenuAvatar.vue';
    import UserMenuActions from '@/modules/user/components/UserMenuActions.vue';
    import {UserInfo} from '@/modules/user/store/userState';
    import UserMenuNotificationScroller from '@/modules/user/components/UserMenuNotificationScroller.vue';

    @Component({
        components: {
            UserMenuNotificationScroller,
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
        public readonly notifications: any;

    }
</script>

<style scoped>

</style>