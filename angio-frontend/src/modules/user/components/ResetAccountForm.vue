<template>
    <BaseSingleFormContainer v-bind:title="$t('user.component.resetAccount.title')">
        <template slot="form">
            <v-form
                    v-model="valid"
                    v-on:submit.prevent="submit"
                    data-test-id="resetAccount__form"
                    ref="form"
                    id="reset-account-form"
            >
                <v-text-field
                        v-model="form.resetCode"
                        v-bind:label="$t('user.component.resetAccount.resetCode.field')"
                        v-bind:rules="[v => !!v || $t('user.component.resetAccount.resetCode.validation.NotEmpty')]"
                        v-bind:disabled="fetching"
                        v-bind:hint="$t('user.component.resetAccount.resetCode.hint')"
                        data-test-id="resetCode__input"
                        persistent-hint
                        type="text"
                        name="resetCode"
                        prepend-inner-icon="new_releases"
                        browser-autocomplete="off"
                        outline
                        required
                ></v-text-field>
                <v-text-field
                        v-model="form.newPassword"
                        v-bind:label="$t('user.component.resetAccount.newPassword.field')"
                        v-bind:rules="[v => !!v || $t('user.component.resetAccount.newPassword.validation.NotEmpty'),
                                    v => v.length >= 8 || $t('user.component.resetAccount.newPassword.validation.MinLength'),
                                    form.newPassword === newPasswordRepeat || $t('user.component.resetAccount.newPassword.validation.DoesNotMatch')]"
                        v-bind:disabled="fetching"
                        v-bind:hint="$t('user.component.resetAccount.newPassword.hint')"
                        data-test-id="newPassword__input"
                        persistent-hint
                        type="password"
                        name="password"
                        prepend-inner-icon="lock"
                        counter
                        maxlength="120"
                        outline
                        required
                ></v-text-field>
                <v-text-field
                        v-model="newPasswordRepeat"
                        v-bind:label="$t('user.component.resetAccount.newPasswordRepeat.field')"
                        v-bind:rules="[v => !!v || $t('user.component.resetAccount.newPassword.validation.NotEmpty'),
                                    v => v.length >= 8 || $t('user.component.resetAccount.newPassword.validation.MinLength'),
                                    form.newPassword === newPasswordRepeat || $t('user.component.resetAccount.newPassword.validation.DoesNotMatch')]"
                        v-bind:disabled="fetching"
                        data-test-id="newPasswordRepeat__input"
                        type="password"
                        name="password"
                        prepend-inner-icon="lock"
                        counter
                        maxlength="120"
                        outline
                        required
                ></v-text-field>
                <BuiltInErrorMessage
                        v-bind:error-messages="mapErrorMessages"
                ></BuiltInErrorMessage>
            </v-form>
        </template>
        <template slot="buttons">
            <v-spacer></v-spacer>
            <v-btn
                    v-bind:disabled="!valid"
                    v-bind:loading="fetching"
                    data-test-id="submit__button"
                    form="reset-account-form"
                    type="submit"
                    color="success"
                    flat
                    round
            >
                {{ $t('user.component.resetAccount.button.submit') }}
            </v-btn>
        </template>
    </BaseSingleFormContainer>
</template>

<script lang="ts">
    import {Component, Emit, Prop, Vue} from 'vue-property-decorator';
    import BaseSingleFormContainer from '@/modules/common/components/BaseSingleFormContainer.vue';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';
    import BuiltInErrorMessage from '@/modules/common/components/BuiltInErrorMessage.vue';
    import {UserResetAccountModel} from "@/modules/user/models/user";

    @Component({
        components: {
            BaseSingleFormContainer,
            BuiltInErrorMessage,
        },
    })
    export default class ResetAccountForm extends Vue {

        @Prop()
        public readonly fetching!: boolean;

        @Prop()
        public readonly errorMessages: string[];

        public valid: boolean = false;

        public form: UserResetAccountModel = {
            resetCode: '',
            newPassword: '',
        };

        public newPasswordRepeat: string = '';

        get mapErrorMessages() {
            return this.errorMessages.map(message => this.$t(message))
        }

        @Emit(CommonEvent.SEND_FORM)
        public submit() {
            return this.form
        }
    }
</script>
