<template lang="html">
    <v-layout justify-center align-center>
        <v-flex xs12 sm6 md5 lg4 xl3>
            <BaseSingleFormContainer v-bind:title="$t('user.component.loginForm.title')">
                <template slot="form">
                    <v-form
                            v-model="valid"
                            v-on:submit.prevent="submitForm"
                            ref="form"
                            id="login-form"
                            data-test-id="login__form"
                    >
                        <v-text-field
                                v-model="form.username"
                                v-bind:label="$t('user.component.loginForm.username.field')"
                                v-bind:rules="[v => !!v || $t('user.component.loginForm.username.validation.NotEmpty'),
                                    v => v.match(this.emailPattern) || $t('common.validation.IncorrectEmailFormat')]"
                                v-bind:disabled="fetching"
                                data-test-id="username__input"
                                type="text"
                                name="username"
                                prepend-inner-icon="person"
                                maxlength="120"
                                browser-autocomplete="off"
                                outline
                                required
                        ></v-text-field>
                        <v-text-field
                                v-model="form.password"
                                v-bind:label="$t('user.component.loginForm.password.field')"
                                v-bind:rules="[v => !!v || $t('user.component.loginForm.password.validation.NotEmpty')]"
                                v-bind:disabled="fetching"
                                data-test-id="password__input"
                                type="password"
                                id="password"
                                name="password"
                                prepend-inner-icon="lock"
                                maxlength="120"
                                outline
                                required
                        ></v-text-field>
                        <BuiltInErrorMessage
                                v-bind:error-messages="errorMessages"
                        ></BuiltInErrorMessage>
                    </v-form>
                </template>
                <template slot="buttons">
                    <v-btn
                            to="/user/reset"
                            color="primary"
                            flat
                            round
                    >
                        {{ $t('user.component.loginForm.button.reset') }}
                    </v-btn>
                    <v-spacer></v-spacer>
                    <v-btn
                            v-bind:disabled="!valid"
                            v-bind:loading="fetching"
                            data-test-id="submit__button"
                            form="login-form"
                            type="submit"
                            color="success"
                            flat
                            round
                    >
                        {{ $t('user.component.loginForm.button.submit') }}
                    </v-btn>
                </template>
            </BaseSingleFormContainer>
        </v-flex>
    </v-layout>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import {throttle} from 'helpful-decorators';
    import {UserCredentialsModel} from '@/modules/user/models/user';
    import BaseSingleFormContainer from '@/modules/common/components/BaseSingleFormContainer.vue';
    import BuiltInErrorMessage from '@/modules/common/components/BuiltInErrorMessage.vue';
    import RegexUtils from '@/utils/regexUtils';

    @Component({
        components: {BuiltInErrorMessage, BaseSingleFormContainer}
    })
    export default class LoginForm extends Vue {

        @Prop()
        public readonly fetching?: boolean;

        @Prop()
        public readonly next?: string;

        @Prop()
        public readonly submit!: (form: UserCredentialsModel) => Promise<any>;

        public errorMessages: string[] = [];

        public valid: boolean = false;

        public emailPattern = RegexUtils.EMAIL_PATTERN;

        public form: UserCredentialsModel = {
            username: '',
            password: '',
        };

        @throttle(1000)
        public submitForm() {
            this.errorMessages = [];
            return this.submit(this.form)
                .then(() => {
                    if (this.next) {
                        this.$router.push({path: this.next});
                    } else {
                        this.$router.push({path: '/'});
                    }
                })
                .catch((error) => {
                    if (error.response.status == 401) {
                        this.errorMessages = [
                            this.$t('user.component.loginForm.password.validation.Unauthorized').toString()
                        ];
                    }
                    if (error.response.status == 400) {
                        this.errorMessages = [error.response.data.error_description]
                    }
                });
        }
    }
</script>
