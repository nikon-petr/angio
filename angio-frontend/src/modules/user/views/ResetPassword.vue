<template>
    <CentredLayout>
        <v-flex xs12 sm6 md5 lg4 xl3>
            <ResetPasswordForm
                    v-on:send-form="sendForm"
                    v-bind:fetching="fetching"
                    v-bind:step="step"
                    v-bind:error-messages="errorMessages"
            ></ResetPasswordForm>
        </v-flex>
    </CentredLayout>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import CentredLayout from '@/modules/common/components/CentredLayout.vue';
    import {UserApiService} from '@/modules/user/services/userApiService';
    import ResetPasswordForm from '@/modules/user/components/ResetPasswordForm.vue';
    import {throttle} from 'helpful-decorators';
    import {EmailModel} from '@/modules/user/models/user';

    @Component({
        components: {
            ResetPasswordForm,
            CentredLayout,
        },
    })
    export default class ResetPassword extends Vue {

        public fetching: boolean = false;

        public step: number = 1;

        public errorMessages: string[] = [];

        @throttle(1000)
        public sendForm(model: EmailModel) {
            this.fetching = true;
            this.errorMessages = [];
            UserApiService
                .resetPassword(model)
                .then((response) => {
                    this.step = 2;
                })
                .catch((error) => {
                    this.step = 1;
                    this.errorMessages = ['user.component.resetPassword.resetPasswordStep.error'];
                    this.$logger.error(error)
                })
                .finally(() => this.fetching = false)
        }
    }
</script>
