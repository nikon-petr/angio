<template>
    <CentredLayout>
        <NewNotificationForm
                v-on:send-notification="sendPushNotification"
                v-bind:fetching="fetching"
                v-bind:message.sync="notification.dataModel.text"
                v-bind:type.sync="notification.type"
                v-bind:error-messages="errorMessages"
        ></NewNotificationForm>
    </CentredLayout>
</template>

<script lang="ts">
    import CentredLayout from '@/modules/common/components/CentredLayout.vue';
    import NewNotificationForm from '@/modules/notification/components/NewNotificationForm.vue';
    import {NewNotificationModel, PlainTextDataModel, Templates} from '@/modules/notification/models/notification';
    import {NotificationApiService} from '@/modules/notification/services/notificationApiService';
    import {NotificationType, SubjectName} from '@/modules/notification/store/notificationState';
    import {Component, Vue} from 'vue-property-decorator';

    @Component({
        components: {CentredLayout, NewNotificationForm}
    })
    export default class NewNotification extends Vue {

        public notification!: NewNotificationModel<PlainTextDataModel>;

        public fetching: boolean = false;

        public errorMessages: string[] = [];

        public sendPushNotification() {
            this.fetching = true;
            NotificationApiService.sendNotification(this.notification)
                .then(response => {
                    this.errorMessages = [];
                    this.reset();
                })
                .catch(error => {
                    this.errorMessages = [];
                    this.$logger.debug(error);
                })
                .finally(() => this.fetching = false);
        }

        public reset(): NewNotificationModel<PlainTextDataModel> {
            return {
                date: undefined,
                type: NotificationType.INFO,
                templateName: Templates.PLAIN_TEXT,
                subject: {
                    name: SubjectName.COMMON
                },
                dataModel: {
                    text: ''
                }
            };
        }

        public data() {
            return {
                notification: this.reset()
            };
        }
    }
</script>
