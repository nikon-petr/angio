<template>
    <v-card
            v-bind:color="translateNotificationTypeToColor(type)"
            class="my-1 mr-2 elevation-1"
    >
        <v-card-title class="pl-0">
            <v-flex xs2 class="text-xs-center">
                <v-icon medium>
                    {{ translateNotificationTypeToIcon(type) }}
                </v-icon>
            </v-flex>
            <v-flex xs10>
                <v-layout row wrap>
                    <v-flex xs12>
                        <span>{{ body }}</span>
                    </v-flex>
                </v-layout>
            </v-flex>
        </v-card-title>
    </v-card>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import {NotificationType} from "../store/notificationState";

    @Component
    export default class BaseNotification extends Vue {

        @Prop()
        public readonly type!: string;

        @Prop()
        public readonly body!: string;

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
    }
</script>
