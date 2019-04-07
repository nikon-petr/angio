<template>
    <div>
        <v-subheader>{{ $t('notification.component.notificationScroller.subheader') }}</v-subheader>

        <v-divider></v-divider>

        <BaseScroller
                height="250px"
                v-bind:notifications="notifications"
        >
            <template v-if="notifications.length > 0">
                <NotificationMessage
                        v-for="notification in notifications"
                        v-bind:key="notification.id"
                        v-bind:type="notification.type"
                        v-bind:body="notification.body"
                        v-bind:date="notification.date"
                ></NotificationMessage>
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

    @Component({
        components: {
            BaseScroller,
            NotificationMessage,
            NoNotificationsMessage
        },
    })
    export default class NotificationScroller extends Vue {

        @Prop()
        public notifications!: Notification[];
    }
</script>

<style scoped>

</style>