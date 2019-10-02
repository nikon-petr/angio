<template>
    <CentredLayout>
        <v-flex xs12 sm6 md5 lg4 xl3>
            <RegistrationForm
                    v-on:send-form="sendForm"
                    v-bind:fetching="fetching"
                    v-bind:error-messages="errorMessages"
            ></RegistrationForm>
        </v-flex>
    </CentredLayout>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import CentredLayout from '@/modules/common/components/CentredLayout.vue';
    import {throttle} from 'helpful-decorators';
    import RegistrationForm from '@/modules/user/components/RegistrationForm.vue';
    import {UserRegisterModel} from "@/modules/user/models/user";
    import {UserApiService} from '@/modules/user/services/userApiService';

    @Component({
        components: {
            RegistrationForm,
            CentredLayout,
        },
    })
    export default class Registration extends Vue {

        public fetching: boolean = false;

        public errorMessages: string[] = [];

        @throttle(1000)
        public sendForm(model: UserRegisterModel) {
            this.fetching = true;
            this.errorMessages = [];
            UserApiService
                .register(model)
                .then((response) => {
                    this.$router.push({path: '/login'});
                })
                .catch((error) => {
                    this.errorMessages = ['user.component.registrationForm.error'];
                    this.$logger.error(error)
                })
                .finally(() => this.fetching = false)
        }
    }
</script>