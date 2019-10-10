<template>
    <BaseSingleFormContainer v-bind:title="$t('user.component.registrationForm.title')">
        <template slot="form">
            <v-form
                    v-model="valid"
                    v-on:submit.prevent="submit"
                    ref="form"
                    id="registration-form"
                    data-test-id="registration__form"
            >
                <v-text-field
                        v-model="form.email"
                        v-bind:label="$t('user.component.registrationForm.email.field')"
                        v-bind:rules="[v => !!v || $t('user.component.registrationForm.email.validation.NotEmpty'),
                            v => v.match(this.emailPattern) || $t('common.validation.IncorrectEmailFormat')]"
                        v-bind:disabled="fetching"
                        v-bind:hint="$t('user.component.registrationForm.email.hint')"
                        data-test-id="email__input"
                        persistent-hint
                        type="text"
                        name="email"
                        prepend-inner-icon="person"
                        browser-autocomplete="off"
                        maxlength="30"
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
                    form="registration-form"
                    data-test-id="submit__button"
                    type="submit"
                    color="success"
                    flat
                    round
            >
                {{ $t('user.component.registrationForm.button.register') }}
            </v-btn>
        </template>
    </BaseSingleFormContainer>
</template>

<script lang="ts">
    import {Component, Emit, Prop, Vue} from 'vue-property-decorator';
    import BaseSingleFormContainer from '@/modules/common/components/BaseSingleFormContainer.vue';
    import BuiltInErrorMessage from '@/modules/common/components/BuiltInErrorMessage.vue';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';
    import {EmailModel} from '@/modules/user/models/user';
    import RegexUtils from '@/utils/regexUtils';

    @Component({
        components: {
            BuiltInErrorMessage,
            BaseSingleFormContainer,
        }
    })
    export default class RegistrationForm extends Vue {

        @Prop()
        public readonly fetching!: boolean;

        @Prop()
        public readonly errorMessages: string[];

        public emailPattern = RegexUtils.EMAIL_PATTERN;

        public valid: boolean = false;

        public form: EmailModel = {
            email: ''
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
