<template>
    <v-flex xs12 sm8 md4>
        <v-card class="elevation-12" max-width="500px">

            <v-card-title primary-title>
                <span class="headline mb-0">{{ $t('user.component.loginForm.title') }}</span>
            </v-card-title>
            <v-card-text>
                <v-form v-model="valid" ref="form">
                    <v-text-field
                            type="text"
                            name="username"
                            v-model="form.username"
                            :label="$t('user.component.loginForm.username.field')"
                            :rules="[v => !!v || $t('user.component.loginForm.username.validation.NotEmpty')]"
                            prepend-icon="person"
                            counter
                            maxlength="120"
                            required
                            outline
                    ></v-text-field>
                    <v-text-field
                            type="password"
                            id="password"
                            name="password"
                            v-model="form.password"
                            :label="$t('user.component.loginForm.password.field')"
                            :rules="[v => !!v || $t('user.component.loginForm.password.validation.NotEmpty')]"
                            prepend-icon="lock"
                            counter
                            maxlength="120"
                            required
                            outline
                    ></v-text-field>
                </v-form>
            </v-card-text>

            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                        v-on:click="submit"
                        :disabled="!valid"
                        color="success"
                        flat
                >{{ $t('user.component.loginForm.button.submit') }}</v-btn>
            </v-card-actions>

        </v-card>
    </v-flex>
</template>

<script lang="ts">
    import {Component, Emit, Vue} from "vue-property-decorator";
    import {UserCredentialsModel} from "@/modules/user/models/user";
    import {CommonEvent} from "@/modules/common/helpers/commonEvent";

    @Component
    export default class LoginForm extends Vue {
        valid: boolean = false;
        form: UserCredentialsModel = {
            username: '',
            password: ''
        };

        @Emit(CommonEvent.SEND_FORM)
        submit() {
            return this.form;
        }
    }
</script>

<style scoped>

</style>