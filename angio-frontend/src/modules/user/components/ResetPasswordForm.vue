<template>
    <v-card class="elevation-12 pa-3 ma-4">
        <v-window v-model="step">
            <v-window-item v-bind:value="Step.FORM">
                <v-card-title class="headline font-weight-regular justify-space-between">
                    <span>{{ $t('user.component.resetPassword.resetPasswordStep.title') }}</span>
                    <v-spacer></v-spacer>
                </v-card-title>
                <v-card-text>
                    <v-form
                            v-model="valid"
                            v-on:submit.prevent="submit"
                            ref="form"
                            id="reset-password-form"
                    >
                        <v-text-field
                                v-model="email"
                                v-bind:label="$t('user.component.resetPassword.resetPasswordStep.email.field')"
                                v-bind:rules="[v => !!v || $t('user.component.resetPassword.resetPasswordStep.email.validation.NotEmpty')]"
                                v-bind:disabled="fetching"
                                v-bind:hint="$t('user.component.resetPassword.resetPasswordStep.email.hint')"
                                persistent-hint
                                type="text"
                                name="email"
                                prepend-inner-icon="person"
                                browser-autocomplete="off"
                                outline
                                required
                        ></v-text-field>
                        <BuiltInErrorMessage
                                v-bind:error-messages="mapErrorMessages"
                        ></BuiltInErrorMessage>
                    </v-form>
                </v-card-text>
            </v-window-item>

            <v-window-item v-bind:value="Step.SUCCESS_PAGE">
                <v-card-title class="headline font-weight-regular justify-center">
                    <span>{{ $t('user.component.resetPassword.resetPasswordSuccessStep.title') }}</span>
                </v-card-title>
                <v-card-text class="body-2 font-weight-light text-xs-center">
                    <v-icon size="100">
                        done
                    </v-icon>
                    <v-spacer></v-spacer>
                    <span>{{ $t('user.component.resetPassword.resetPasswordSuccessStep.content') }}</span>
                </v-card-text>
            </v-window-item>
        </v-window>

        <v-card-actions>
            <v-spacer v-if="step === Step.FORM"></v-spacer>
            <v-btn
                    v-if="step === Step.FORM"
                    v-bind:disabled="!valid"
                    v-bind:loading="fetching"
                    form="reset-password-form"
                    type="submit"
                    color="success"
                    flat
                    round
            >
                {{ $t('user.component.resetPassword.resetPasswordStep.button.submit') }}
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script lang="ts">
    import {Component, Emit, Prop, Vue} from 'vue-property-decorator';
    import BaseSingleFormContainer from '@/modules/common/components/BaseSingleFormContainer.vue';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';
    import BuiltInErrorMessage from '@/modules/common/components/BuiltInErrorMessage.vue';

    @Component({
        components: {
            BaseSingleFormContainer,
            BuiltInErrorMessage,
        },
    })
    export default class ResetPasswordForm extends Vue {

        @Prop()
        public readonly fetching!: boolean;

        @Prop()
        public readonly step!: number;

        @Prop()
        public readonly errorMessages!: string[];

        public Step = ResetPasswordState;

        public valid: boolean = false;

        public email: string = '';

        get mapErrorMessages() {
            return this.errorMessages.map(message => this.$t(message))
        }

        @Emit(CommonEvent.SEND_FORM)
        public submit() {
            return this.email;
        }
    }

    enum ResetPasswordState {
        FORM = 1,
        SUCCESS_PAGE = 2
    }
</script>
