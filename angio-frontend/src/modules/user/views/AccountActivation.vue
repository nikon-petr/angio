<template>
    <CentredLayout>
        <v-flex xs12 sm6 md5 lg4 xl3>
            <AccountActivationForm
                    v-on:send-form="sendForm"
                    v-bind:fetching="fetching"
                    v-bind:step="step"
                    v-bind:error-messages="errorMessages"
            ></AccountActivationForm>
        </v-flex>
    </CentredLayout>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import CentredLayout from '@/modules/common/components/CentredLayout.vue';
    import AccountActivationForm from '@/modules/user/components/AccountActivationForm.vue';
    import {throttle} from 'helpful-decorators';
    import {UserApiService} from '@/modules/user/services/userApiService';
    import {UserActivationModel} from '@/modules/user/models/user';

    @Component({
        components: {
            CentredLayout,
            AccountActivationForm
        },
    })
    export default class AccountActivation extends Vue {

        @Prop()
        public readonly userId!: string;

        public fetching: boolean = false;

        public step: number = 1;

        public errorMessages: string[] = [];

        @throttle(1000)
        public sendForm(model: UserActivationModel) {
            this.fetching = true;
            this.errorMessages = [];
            UserApiService
                .activate(this.userId, model)
                .then((response) => {
                    this.step = 2;
                })
                .catch((error) => {
                    this.step = 1;
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
