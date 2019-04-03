<template>
    <v-list>
        <v-list-tile to="/user/settings" v-ripple>
            <v-list-tile-title>Настройки</v-list-tile-title>
        </v-list-tile>
        <v-list-tile v-on:click="logout" v-ripple>
            <v-list-tile-title>Выйти из приложения</v-list-tile-title>
        </v-list-tile>
        <v-subheader>Уведомления</v-subheader>
    </v-list>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';

    @Component
    export default class UserMenuActions extends Vue{

        @Prop()
        logout!: () => any;
    }
</script>

<style scoped>

</style>