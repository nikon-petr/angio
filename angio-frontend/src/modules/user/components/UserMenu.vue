<template>
    <v-card>
        <UserMenuAvatar
                v-bind:full-name="user.fullName"
                v-bind:organization="user.organization"
        ></UserMenuAvatar>

        <UserMenuActions
                v-bind:logout="logout"
        ></UserMenuActions>

        <v-divider></v-divider>

        <UserMenuNotificationScroller
                v-bind:notifications="notifications"
        ></UserMenuNotificationScroller>
    </v-card>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import UserMenuAvatar from "@/modules/user/components/UserMenuAvatar.vue";
    import UserMenuActions from "@/modules/user/components/UserMenuActions.vue";
    import {UserInfo} from "../store/userState";
    import UserMenuNotificationScroller from "@/modules/user/components/UserMenuNotificationScroller.vue";

    @Component({
        components: {
            UserMenuNotificationScroller,
            UserMenuActions,
            UserMenuAvatar
        }
    })
    export default class UserMenu extends Vue {

        @Prop()
        user!: UserInfo;

        @Prop()
        logout!: () => any;

        @Prop()
        notifications: any;

    }
</script>

<style scoped>

</style>