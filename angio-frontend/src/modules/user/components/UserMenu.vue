<template>
    <v-card>

        <UserMenuAvatar
                v-bind:full-name="user.fullName"
                v-bind:organization="user.organization"
        ></UserMenuAvatar>

        <v-divider></v-divider>

        <UserMenuActions
                v-bind:logout="logout"
        ></UserMenuActions>

        <v-divider></v-divider>

        <BaseScroller
                height="250px"
                v-bind:notifications="notifications">
            <UserMenuNotificationMessage
                    v-for="notification in notifications"
                    v-bind:key="notification.id"
                    v-bind:type="notification.type"
                    v-bind:text="notification.text"
                    v-bind:date="notification.date"
            ></UserMenuNotificationMessage>
        </BaseScroller>

    </v-card>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import UserMenuAvatar from "@/modules/user/components/UserMenuAvatar.vue";
    import UserMenuActions from "@/modules/user/components/UserMenuActions.vue";
    import BaseScroller from "@/modules/common/components/BaseScroller.vue";
    import UserMenuNotificationMessage from "@/modules/user/components/UserMenuNotificationMessage.vue";
    import {UserInfo} from "../store/userState";

    @Component({
        components: {
            BaseScroller,
            UserMenuNotificationMessage,
            UserMenuActions,
            UserMenuAvatar
        }
    })
    export default class UserMenu extends Vue {

        @Prop()
        user: UserInfo;

        @Prop()
        logout: () => any;

        @Prop()
        notifications: any;

    }
</script>

<style scoped>

</style>