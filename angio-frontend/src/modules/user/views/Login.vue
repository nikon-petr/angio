<template>
    <CentredLayout>
        <LoginForm
                v-bind:submit="authUser"
                v-bind:next="next"
                v-bind:fetching="fetching"
        ></LoginForm>
    </CentredLayout>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import {Action, State} from 'vuex-class';
    import LoginForm from '@/modules/user/components/LoginForm.vue';
    import {UserAction} from '@/modules/user/store/userStore';
    import {UserCredentialsModel} from '@/modules/user/models/user';
    import CentredLayout from '@/modules/common/components/CentredLayout.vue';

    @Component({
        components: {
            CentredLayout,
            LoginForm,
        },
    })
    export default class Login extends Vue {

        @Prop()
        public readonly next?: string;

        @State((state) => state.user.fetching)
        public readonly fetching!: boolean;

        @Action(UserAction.AUTH_USER)
        public readonly authUser!: (form: UserCredentialsModel) => Promise<void>;
    }
</script>

<style scoped>

</style>