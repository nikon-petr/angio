<template>
    <v-layout justify-center align-center>
        <v-flex xs12 sm8 md7 lg5 xl4>
            <BaseSingleFormContainer v-bind:title="$t('notification.component.newNotificationForm.title')">
                <template slot="form">
                    <v-form
                            ref="form"
                            v-model="valid"
                            v-on:submit.prevent="sendNotification"
                            id="new-notification-form"
                    >
                        <v-autocomplete
                                v-model="syncedType"
                                v-bind:items="notificationTypes"
                                v-bind:label="$t('notification.component.newNotificationForm.field.type.label')"
                                v-bind:rules="[v => !!v && v.length > 0 || $t('notification.component.newNotificationForm.validation.type.NotEmpty')]"
                                v-bind:disabled="fetching"
                                deletable-chips
                                clearable
                                outline
                        ></v-autocomplete>
                        <v-textarea
                                v-model="syncedMessage"
                                v-bind:label="$t('notification.component.newNotificationForm.field.message')"
                                v-bind:rules="[v => !!v || $t('notification.component.newNotificationForm.validation.message.NotEmpty')]"
                                v-bind:disabled="fetching"
                                browser-autocomplete="off"
                                maxlength="120"
                                name="message"
                                type="text"
                                required
                                outline
                        ></v-textarea>
                        <span class="py-2 body-1">* {{ $t('notification.component.newNotificationForm.hint') }}</span>
                        <BuiltInErrorMessage
                                v-bind:error-messages="errorMessages"
                        ></BuiltInErrorMessage>
                    </v-form>
                </template>
                <template slot="buttons">
                    <v-spacer></v-spacer>
                    <v-btn
                            v-bind:disabled="!valid"
                            form="new-notification-form"
                            type="submit"
                            color="success"
                            round
                    >
                        {{ $t('notification.component.newNotificationForm.button.send') }}
                    </v-btn>
                </template>
            </BaseSingleFormContainer>
        </v-flex>
    </v-layout>
</template>

<script lang="ts">
    import BaseSingleFormContainer from '@/modules/common/components/BaseSingleFormContainer.vue';
    import BuiltInErrorMessage from '@/modules/common/components/BuiltInErrorMessage.vue';
    import {NotificationType} from '@/modules/notification/store/notificationState';
    import {Component, Vue, Prop, PropSync, Emit} from 'vue-property-decorator';

    @Component({
        components: {BuiltInErrorMessage, BaseSingleFormContainer}
    })
    export default class NewNotificationForm extends Vue {

        @Prop()
        public readonly message!: string;

        @PropSync('message')
        public syncedMessage!: string;

        @Prop()
        public readonly fetching?: boolean;

        @Prop()
        public readonly type!: NotificationType;

        @PropSync('type')
        public syncedType!: string;

        @Prop()
        public readonly errorMessages!: string[];

        @Emit()
        public sendNotification() {}

        public valid: boolean = false;

        get notificationTypes()  {
            return [
                {
                    value: NotificationType.INFO.toString(),
                    text: this.$t('notification.component.newNotificationForm.field.type.value.info').toString()
                },
                {
                    value: NotificationType.SUCCESS.toString(),
                    text: this.$t('notification.component.newNotificationForm.field.type.value.success').toString()
                },
                {
                    value: NotificationType.WARNING.toString(),
                    text: this.$t('notification.component.newNotificationForm.field.type.value.warning').toString()
                },
                {
                    value: NotificationType.ERROR.toString(),
                    text: this.$t('notification.component.newNotificationForm.field.type.value.error').toString()
                }
            ]
        }
    }
</script>
