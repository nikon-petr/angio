<template>
    <CentredLayout>
        <v-flex xs12 sm6 md5 lg4 xl3>
            <ChangePasswordForm
                    v-on:send-form="sendForm"
                    v-bind:fetching="fetching"
                    v-bind:error-messages="errorMessages"
            ></ChangePasswordForm>
        </v-flex>
    </CentredLayout>
</template>

<script lang="ts">
    import {Component, Vue, Prop} from 'vue-property-decorator';
    import CentredLayout from '@/modules/common/components/CentredLayout.vue';
    import ChangePasswordForm from "@/modules/user/components/ChangePasswordForm.vue";
    import {throttle} from "helpful-decorators";
    import {ChangePasswordModel} from "@/modules/user/models/user";
    import {UserApiService} from "@/modules/user/services/userApiService";

    @Component({
        components: {
            ChangePasswordForm,
            CentredLayout
        }
    })
    export default class ChangePassword extends Vue {

        public fetching: boolean = false;

        public errorMessages: string[] = [];

        @throttle(1000)
        public sendForm(model: ChangePasswordModel) {
            this.fetching = true;
            this.errorMessages = [];
            UserApiService
                .changePassword(model)
                .then((response) => {
                    this.$router.push({path: '/user/settings'});
                })
                .catch((error) => {
                    if (error.response.status >= 400 && error.response.status < 500) {
                        this.errorMessages = [error.response.data.error.message];
                    } else if (error.response.status >= 500) {
                        this.errorMessages = ['common.form.error.500'];
                    }
                    this.$logger.error(error)
                })
                .finally(() => this.fetching = false)
        }
    }
</script>
