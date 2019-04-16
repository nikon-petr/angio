<template lang="html">
    <v-flex xs12 sm8 md4>
        <v-card class="elevation-12" max-width="500px">

            <v-card-title class="title font-weight-regular justify-space-between">
                <span>{{ $t('user.component.loginForm.title') }}</span>
                <v-spacer></v-spacer>
            </v-card-title>

            <v-card-text>
                <v-form v-model="valid" ref="form" v-on:submit.prevent="submit" id="login-form">
                    <v-text-field
                            type="text"
                            name="username"
                            v-model="form.username"
                            v-bind:label="$t('user.component.loginForm.username.field')"
                            v-bind:rules="[v => !!v || $t('user.component.loginForm.username.validation.NotEmpty')]"
                            v-bind:disabled="fetching"
                            prepend-inner-icon="person"
                            counter
                            maxlength="120"
                            required
                    ></v-text-field>
                    <v-text-field
                            type="password"
                            id="password"
                            name="password"
                            v-model="form.password"
                            v-bind:label="$t('user.component.loginForm.password.field')"
                            v-bind:rules="[v => !!v || $t('user.component.loginForm.password.validation.NotEmpty')]"
                            v-bind:disabled="fetching"
                            prepend-inner-icon="lock"
                            counter
                            maxlength="120"
                            required
                    ></v-text-field>
                </v-form>
            </v-card-text>

            <v-card-actions>
                <v-btn
                        to="/user/reset"
                        color="primary"
                        flat
                        round
                >{{ $t('user.component.loginForm.button.reset') }}
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
                >{{ $t('user.component.loginForm.button.submit') }}
                </v-btn>
            </v-card-actions>

        </v-card>
    </v-flex>
</template>

<script lang="ts">
    import {Component, Emit, Prop, Vue} from 'vue-property-decorator';
    import {UserCredentialsModel} from '@/modules/user/models/user';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';
    import {throttle} from 'helpful-decorators';

    @Component
    export default class LoginForm extends Vue {

        @Prop()
        public readonly fetching?: boolean;

        public valid: boolean = false;
        public form: UserCredentialsModel = {
            username: '',
            password: '',
        };

        @throttle(1000)
        @Emit(CommonEvent.SEND_FORM)
        public submit() {
            return this.form;
        }
    }
</script>