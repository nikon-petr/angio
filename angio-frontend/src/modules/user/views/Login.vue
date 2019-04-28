<template>
    <CentredLayout>
        <LoginForm
                v-bind:submit="sendForm()"
                v-bind:fetching="fetching"
        ></LoginForm>
    </CentredLayout>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import LoginForm from '@/modules/user/components/LoginForm.vue';
    import store from '@/store';
    import {authUser} from '@/modules/user/store/userStore';
    import {UserCredentialsModel} from '@/modules/user/models/user';
    import {State} from 'vuex-class';
    import CentredLayout from '@/modules/common/components/CentredLayout.vue';

    @Component({
        components: {
            CentredLayout,
            LoginForm,
        },
    })
    export default class Login extends Vue {

        @State((state) => state.user.fetching)
        public readonly fetching!: boolean;

        public sendForm() {
            return (form: UserCredentialsModel) => authUser(store, form);
        }
    }
</script>

<style scoped>

</style>