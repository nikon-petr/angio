<template>
    <div>
        <v-card flat v-bind:color="color(read)">
            <v-card-title class="pl-0">
                <v-flex xs2 class="text-xs-center">
                    <v-icon v-bind:color="translateNotificationTypeToColor(type)" medium>
                        {{ translateNotificationTypeToIcon(type) }}
                    </v-icon>
                </v-flex>
                <v-flex xs10>
                    <v-layout row wrap>
                        <v-flex xs12>
                            <span>{{ body }}</span>
                        </v-flex>
                        <v-flex xs12 class="text--secondary">
                            <span>{{ date | moment('from', 'now') }}</span>
                        </v-flex>
                    </v-layout>
                </v-flex>
            </v-card-title>
        </v-card>

        <v-divider></v-divider>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import {NotificationType} from '@/modules/notification/store/notificationState';

    @Component
    export default class NotificationMessage extends Vue {

        public notificationType: any = NotificationType;

        @Prop()
        public readonly type!: string;

        @Prop()
        public readonly body!: string;

        @Prop()
        public readonly date!: Date;

        @Prop()
        public readonly read!: boolean;

        public translateNotificationTypeToColor(notificationType: NotificationType): string {
            switch (notificationType) {
                case NotificationType.INFO:
                    return 'primary';
                case NotificationType.SUCCESS:
                    return 'success';
                case NotificationType.WARNING:
                    return 'warning';
                case NotificationType.ERROR:
                    return 'error';
            }
        }

        public translateNotificationTypeToIcon(notificationType: NotificationType): string {
            switch (notificationType) {
                case NotificationType.INFO:
                    return 'info_outline';
                case NotificationType.SUCCESS:
                    return 'check_circle_outline';
                case NotificationType.WARNING:
                    return 'error_outline';
                case NotificationType.ERROR:
                    return 'error_outline';
            }
        }

        public color(read: boolean) {

            // @ts-ignore
            if (this.$vuetify.dark) {
                return read ? undefined : 'secondary lighten-1';
            } else {
                return read ? undefined : 'grey lighten-2'
            }
        }
    }
</script>

<style scoped>

</style>