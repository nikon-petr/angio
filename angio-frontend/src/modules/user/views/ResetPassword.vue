<template>
    <CentredLayout>
        <v-flex xs12 sm6 md5 lg4 xl3>
            <ResetPasswordForm
                    v-on:send-form="sendForm"
                    v-bind:fetching="fetching"
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

    @Component({
        components: {
            ResetPasswordForm,
            CentredLayout,
        },
    })
    export default class ResetPassword extends Vue {

        public fetching: boolean = false;

        public errorMessages: string[] = [];

        @throttle(1000)
        public sendForm(email: string) {
            this.fetching = true;
            this.errorMessages = [];
            UserApiService
                .resetPassword(email)
                .then((response) => {
                    this.$router.push({path: '/password/reset'});
                })
                .catch((error) => {
                    this.errorMessages = ['user.component.resetPassword.error']
                })
                .finally(() => this.fetching = false)
        }
    }
</script>
