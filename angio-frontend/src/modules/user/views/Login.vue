<template>
    <LoginForm v-on:send-form="sendForm"></LoginForm>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import LoginForm from '../components/LoginForm.vue';
    import store from '@/store';
    import {authUser} from '@/modules/user/store/userStore';
    import {UserCredentialsModel} from '@/modules/user/models/user';

    @Component({
        components: {
            LoginForm
        }
    })
    export default class Login extends Vue {
        sendForm(form: UserCredentialsModel) {
            authUser(store, form);
        }
    }
</script>

<style scoped>

</style>