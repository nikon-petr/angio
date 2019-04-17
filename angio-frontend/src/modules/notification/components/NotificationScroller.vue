<template>
    <div>
        <v-subheader>{{ $t('notification.component.notificationScroller.subheader') }}</v-subheader>

        <v-divider></v-divider>

        <BaseScroller
                height="250px"
                v-bind:notifications="notifications"
        >
            <template v-if="notifications.length > 0">
                <transition-group name="scale-transition">
                    <NotificationMessage
                            v-for="{id, type, body, date, read} in notifications"
                            v-on:mouseover.once.native="!read ? addNotificationToRead(id) : null"
                            v-bind:key="id"
                            v-bind:type="type"
                            v-bind:body="body"
                            v-bind:date="date"
                            v-bind:read="read"
                    ></NotificationMessage>
                </transition-group>
            </template>
            <template v-else>
                <NoNotificationsMessage></NoNotificationsMessage>
            </template>
        </BaseScroller>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import BaseScroller from '@/modules/common/components/BaseScroller.vue';
    import NotificationMessage from '@/modules/notification/components/NotificationMessage.vue';
    import NoNotificationsMessage from '@/modules/notification/components/NoNotificationsMessage.vue';
    import {Notification} from "@/modules/notification/store/notificationState";
    import {throttle} from "helpful-decorators";

    @Component({
        components: {
            BaseScroller,
            NotificationMessage,
            NoNotificationsMessage
        },
    })
    export default class NotificationScroller extends Vue {

        @Prop()
        public readonly notifications!: Notification[];

        @Prop()
        public readonly readNotifications!: (ids: string[]) => any;

        public notificationIdQueue: string[] = [];

        addNotificationToRead(id: string) {
            this.notificationIdQueue.push(id);
            this.readNotificationThrottle();
        }

        @throttle(2000)
        readNotificationThrottle() {
            this.readNotifications([...this.notificationIdQueue]);
            this.notificationIdQueue.length = 0;
        }
    }
</script>

<style scoped>

</style>