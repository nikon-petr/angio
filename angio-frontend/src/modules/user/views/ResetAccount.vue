<template>
    <CentredLayout>
        <v-flex xs12 sm6 md5 lg4 xl3>
            <ResetAccountForm
                    v-on:send-form="sendForm"
                    v-bind:fetching="fetching"
                    v-bind:error-messages="errorMessages"
            ></ResetAccountForm>
        </v-flex>
    </CentredLayout>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import CentredLayout from '@/modules/common/components/CentredLayout.vue';
    import ResetAccountForm from '@/modules/user/components/ResetAccountForm.vue';
    import {throttle} from 'helpful-decorators';
    import {UserApiService} from '@/modules/user/services/userApiService';
    import {UserResetAccountModel} from '@/modules/user/models/user';

    @Component({
        components: {
            ResetAccountForm,
            CentredLayout,
        },
    })
    export default class ResetAccount extends Vue {

        @Prop()
        public readonly userId!: string;

        public fetching: boolean = false;

        public errorMessages: string[] = [];

        @throttle(1000)
        public sendForm(model: UserResetAccountModel) {
            this.fetching = true;
            this.errorMessages = [];
            UserApiService
                .resetAccount(this.userId, model)
                .then((response) => {
                    this.$router.push({path: '/login'});
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
