<template>
    <BaseSingleFormContainer v-bind:title="$t('user.component.changePassword.title')">
        <template slot="form">
            <v-form
                    v-model="valid"
                    v-on:submit.prevent="submit"
                    ref="form"
                    id="change-password-form"
                    data-test-id="change-password__form"
            >
                <v-text-field
                        v-model="form.oldPassword"
                        v-bind:label="$t('user.component.changePassword.oldPassword.field')"
                        v-bind:rules="[v => !!v || $t('user.component.changePassword.oldPassword.validation.NotEmpty')]"
                        v-bind:disabled="fetching"
                        data-test-id="email__input"
                        type="password"
                        name="oldPassword"
                        browser-autocomplete="off"
                        maxlength="120"
                        outline
                        required
                ></v-text-field>
                <v-text-field
                        v-model="form.newPassword"
                        v-bind:label="$t('user.component.changePassword.newPassword.field')"
                        v-bind:rules="[v => !!v || $t('user.component.changePassword.newPassword.validation.NotEmpty'),
                                v => v.length >= 8 || $t('user.component.changePassword.newPassword.validation.MinLength'),
                                v => v != form.oldPassword|| $t('user.component.changePassword.newPassword.validation.Equal')]"
                        v-bind:disabled="fetching"
                        data-test-id="email__input"
                        type="password"
                        name="newPassword"
                        browser-autocomplete="off"
                        maxlength="120"
                        outline
                        required
                ></v-text-field>
                <v-text-field
                        v-model="confirmPassword"
                        v-bind:label="$t('user.component.changePassword.confirmPassword.field')"
                        v-bind:rules="[v => !!v || $t('user.component.changePassword.confirmPassword.validation.NotEmpty'),
                                v => v == form.newPassword || $t('user.component.changePassword.confirmPassword.validation.NotEqual')]"
                        v-bind:disabled="fetching"
                        data-test-id="email__input"
                        type="password"
                        name="oldPassword"
                        browser-autocomplete="off"
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
                    form="change-password-form"
                    data-test-id="submit__button"
                    type="submit"
                    color="success"
                    flat
                    round
            >
                {{ $t('user.component.changePassword.button.change') }}
            </v-btn>
        </template>
    </BaseSingleFormContainer>
</template>

<script lang="ts">
    import {Component, Vue, Prop, Emit} from 'vue-property-decorator';
    import BaseSingleFormContainer from '@/modules/common/components/BaseSingleFormContainer.vue';
    import BuiltInErrorMessage from '@/modules/common/components/BuiltInErrorMessage.vue';
    import {ChangePasswordModel} from "@/modules/user/models/user";
    import {CommonEvent} from "@/modules/common/helpers/commonEvent";

    @Component({
        components: {
            BuiltInErrorMessage,
            BaseSingleFormContainer,
        }
    })
    export default class ChangePasswordForm extends Vue {

        @Prop()
        public readonly fetching!: boolean;

        @Prop()
        public readonly errorMessages!: string[];

        public confirmPassword: string = '';

        public valid: boolean = false;

        public form: ChangePasswordModel = {
            oldPassword: '',
            newPassword: ''
        };

        get mapErrorMessages() {
            return this.errorMessages.map(message => this.$t(message))
        }

        @Emit(CommonEvent.SEND_FORM)
        public submit() {
            return this.form
        }
    }
</script>
