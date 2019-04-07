<template>
    <div>
        <v-card flat>
            <v-card-title class="pl-0">
                <v-flex xs2 class="text-xs-center">
                    <v-icon color="success" medium v-if="type === notificationType.SUCCESS.toString()">
                        check_circle_outline
                    </v-icon>
                    <v-icon color="error" medium v-if="type === notificationType.ERROR.toString()">
                        error_outline
                    </v-icon>
                    <v-icon color="primary" medium v-if="type === notificationType.INFO.toString()">
                        info_outline
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
    }
</script>

<style scoped>

</style>