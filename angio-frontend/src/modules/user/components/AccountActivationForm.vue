<template>
    <v-card class="elevation-12 pa-3 ma-4">
        <v-window v-model="step">
            <v-window-item v-bind:value="Step.FORM">
                <v-card-title class="headline font-weight-regular justify-space-between">
                    <span>{{ $t('user.component.activation.activationFormStep.title') }}</span>
                    <v-spacer></v-spacer>
                </v-card-title>
                <v-card-text>
                    <v-form
                            v-model="valid"
                            v-on:submit.prevent="submit"
                            ref="form"
                            id="activation-form"
                            data-test-id="accountActivation__form"
                    >
                        <v-text-field
                                v-model="form.enablingCode"
                                v-bind:label="$t('user.component.activation.activationFormStep.enablingCode.field')"
                                v-bind:rules="[v => !!v || $t('user.component.activation.activationFormStep.enablingCode.validation.NotEmpty')]"
                                v-bind:disabled="fetching"
                                data-test-id="enablingCode__input"
                                type="text"
                                name="enablingCode"
                                prepend-inner-icon="new_releases"
                                browser-autocomplete="off"
                                outline
                                required
                        ></v-text-field>
                        <v-text-field
                                v-model="form.fullName.firstname"
                                v-bind:label="$t('user.component.activation.activationFormStep.fullName.firstname.field')"
                                v-bind:rules="[v => !!v || $t('user.component.activation.activationFormStep.fullName.firstname.validation.NotEmpty')]"
                                v-bind:disabled="fetching"
                                data-test-id="firstname__input"
                                type="text"
                                name="firstname"
                                prepend-inner-icon="person"
                                maxlength="30"
                                outline
                                required
                        ></v-text-field>
                        <v-text-field
                                v-model="form.fullName.lastname"
                                v-bind:label="$t('user.component.activation.activationFormStep.fullName.lastname.field')"
                                v-bind:rules="[v => !!v || $t('user.component.activation.activationFormStep.fullName.lastname.validation.NotEmpty')]"
                                v-bind:disabled="fetching"
                                data-test-id="lastname__input"
                                type="text"
                                name="lastname"
                                prepend-inner-icon="person"
                                maxlength="30"
                                outline
                                required
                        ></v-text-field>
                        <v-text-field
                                v-model="form.fullName.patronymic"
                                v-bind:label="$t('user.component.activation.activationFormStep.fullName.patronymic.field')"
                                v-bind:disabled="fetching"
                                data-test-id="patronymic__input"
                                type="text"
                                name="patronymic"
                                prepend-inner-icon="person"
                                maxlength="30"
                                outline
                        ></v-text-field>
                        <v-text-field
                                v-model="form.newPassword"
                                v-bind:label="$t('user.component.activation.activationFormStep.newPassword.field')"
                                v-bind:rules="[v => !!v || $t('user.component.activation.activationFormStep.newPassword.validation.NotEmpty'),
                                    v => v.length >= 8 || $t('user.component.activation.activationFormStep.newPassword.validation.MinLength'),
                                    form.newPassword === newPasswordRepeat || $t('user.component.activation.activationFormStep.newPassword.validation.DoesNotMatch')]"
                                v-bind:disabled="fetching"
                                data-test-id="newPassword__input"
                                type="password"
                                name="password"
                                prepend-inner-icon="lock"
                                maxlength="120"
                                outline
                                required
                        ></v-text-field>
                        <v-text-field
                                v-model="newPasswordRepeat"
                                v-bind:label="$t('user.component.activation.activationFormStep.newPasswordRepeat.field')"
                                v-bind:rules="[v => !!v || $t('user.component.activation.activationFormStep.newPassword.validation.NotEmpty'),
                                    v => v.length >= 8 || $t('user.component.activation.activationFormStep.newPassword.validation.MinLength'),
                                    form.newPassword === newPasswordRepeat || $t('user.component.activation.activationFormStep.newPassword.validation.DoesNotMatch')]"
                                v-bind:disabled="fetching"
                                data-test-id="newPasswordRepeat__input"
                                type="password"
                                name="password"
                                prepend-inner-icon="lock"
                                maxlength="120"
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
                    <span>{{ $t('user.component.activation.activationSuccessStep.title') }}</span>
                </v-card-title>
                <v-card-text class="body-2 font-weight-light text-xs-center">
                    <v-icon size="100">
                        done
                    </v-icon>
                    <v-spacer></v-spacer>
                    <span>{{ $t('user.component.activation.activationSuccessStep.content') }}</span>
                </v-card-text>
            </v-window-item>
        </v-window>

        <v-card-actions>
            <v-spacer v-if="step === Step.FORM"></v-spacer>
            <v-btn
                    v-if="step === Step.FORM"
                    v-bind:disabled="!valid"
                    v-bind:loading="fetching"
                    data-test-id="submit__button"
                    form="activation-form"
                    type="submit"
                    color="primary"
                    flat
                    round
            >
                {{ $t('user.component.activation.activationFormStep.button.send') }}
            </v-btn>
            <v-btn
                    v-if="step === Step.SUCCESS_PAGE"
                    data-test-id="login__button"
                    to="/login"
                    color="success"
                    flat
                    round
                    class="ma-auto"
            >
                {{ $t('user.component.activation.activationSuccessStep.button.login') }}
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script lang="ts">
    import {Component, Emit, Prop, Vue} from 'vue-property-decorator';
    import BaseSingleFormContainer from '@/modules/common/components/BaseSingleFormContainer.vue';
    import {UserActivationModel} from '@/modules/user/models/user';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';
    import BuiltInErrorMessage from '@/modules/common/components/BuiltInErrorMessage.vue';

    @Component({
        components: {
            BaseSingleFormContainer,
            BuiltInErrorMessage
        }
    })
    export default class AccountActivationForm extends Vue {

        @Prop()
        public readonly fetching!: boolean;

        @Prop()
        public readonly step!: number;

        @Prop()
        public readonly errorMessages!: string[];

        public Step = AccountActivationStep;

        public valid: boolean = false;

        public form: UserActivationModel = {
            fullName: {
                firstname: '',
                lastname: '',
                patronymic: ''
            },
            enablingCode: '',
            newPassword: ''
        };

        public newPasswordRepeat: string = '';

        get mapErrorMessages() {
            return this.errorMessages.map(message => this.$t(message))
        }

        @Emit(CommonEvent.SEND_FORM)
        public submit() {
            return this.form;
        }
    }

    enum AccountActivationStep {
        FORM = 1,
        SUCCESS_PAGE = 2
    }
</script>
