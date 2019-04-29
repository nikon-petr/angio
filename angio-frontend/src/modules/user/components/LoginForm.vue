<template lang="html">
    <v-layout justify-center align-center>
        <v-flex xs12 sm6 md5 lg4 xl3>
            <BaseSingleFormContainer v-bind:title="$t('user.component.loginForm.title')">
                <template slot="form">
                    <v-form v-model="valid" ref="form" v-on:submit.prevent="submitForm" id="login-form">
                        <v-text-field
                                v-model="form.username"
                                v-bind:label="$t('user.component.loginForm.username.field')"
                                v-bind:rules="[v => !!v || $t('user.component.loginForm.username.validation.NotEmpty')]"
                                v-bind:disabled="fetching"
                                type="text"
                                name="username"
                                prepend-inner-icon="person"
                                counter
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
                                type="password"
                                id="password"
                                name="password"
                                prepend-inner-icon="lock"
                                counter
                                maxlength="120"
                                outline
                                required
                        ></v-text-field>
                        <v-expand-transition>
                            <ul v-if="unauthorized">
                                <li class="red--text body-1">
                                    {{ $t('user.component.loginForm.password.validation.Unauthorized') }}
                                </li>
                            </ul>
                        </v-expand-transition>
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
    import {Component, Emit, Prop, Vue} from 'vue-property-decorator';
    import {UserCredentialsModel} from '@/modules/user/models/user';
    import {throttle} from 'helpful-decorators';
    import BaseSingleFormContainer from '@/modules/common/components/BaseSingleFormContainer.vue';

    @Component({
        components: {BaseSingleFormContainer}
    })
    export default class LoginForm extends Vue {

        @Prop()
        public readonly fetching?: boolean;

        @Prop()
        public readonly next?: string;

        @Prop()
        public readonly submit!: (form: UserCredentialsModel) => Promise<any>;

        public unauthorized: boolean = false;

        public valid: boolean = false;
        public form: UserCredentialsModel = {
            username: '',
            password: '',
        };

        @throttle(1000)
        public submitForm() {
            this.unauthorized = false;
            return this.submit(this.form)
                .then((reponse) => {
                    if (this.next) {
                        this.$router.push({path: this.next});
                    } else {
                        this.$router.push({path: '/'});
                    }
                })
                .catch((error) => {
                    if (error.response.status == 400) {
                        this.unauthorized = true;
                    }
                });
        }
    }
</script>