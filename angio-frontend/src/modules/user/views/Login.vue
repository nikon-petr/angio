<template>
        <LoginForm></LoginForm>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import LoginForm from '../components/LoginForm.vue';

    @Component({
        components: {LoginForm}
    })
    export default class Login extends Vue {}
</script>

<style scoped>

</style>