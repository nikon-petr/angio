<template>
    <BaseScroller
            height="250px"
            v-bind:notifications="notifications"
    >
        <template v-if="notifications.length > 0">
            <UserMenuNotificationMessage
                    v-for="notification in notifications"
                    v-bind:key="notification.id"
                    v-bind:type="notification.type"
                    v-bind:text="notification.text"
                    v-bind:date="notification.date"
            ></UserMenuNotificationMessage>
        </template>
        <template v-else>
            <UserMenuNonNotifications></UserMenuNonNotifications>
        </template>
    </BaseScroller>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import BaseScroller from '@/modules/common/components/BaseScroller.vue';
    import UserMenuNonNotifications from '@/modules/user/components/UserMenuNonNotifications.vue';
    import UserMenuNotificationMessage from '@/modules/user/components/UserMenuNotificationMessage.vue';

    @Component({
        components: {
            BaseScroller,
            UserMenuNonNotifications,
            UserMenuNotificationMessage,
        },
    })
    export default class UserMenuNotificationScroller extends Vue {

        @Prop()
        public notifications: any;
    }
</script>

<style scoped>

</style>