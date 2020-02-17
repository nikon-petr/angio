<template>
    <v-list>
        <v-list-tile to="/user/settings" v-ripple>
            <v-list-tile-title>{{ $t('user.component.userMenuActions.button.settings') }}</v-list-tile-title>
        </v-list-tile>
        <v-list-tile v-on:click="logout" v-ripple>
            <v-list-tile-title>{{ $t('user.component.userMenuActions.button.logout') }}</v-list-tile-title>
        </v-list-tile>
    </v-list>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';

    @Component
    export default class UserMenuActions extends Vue {

        @Prop()
        public readonly logout!: () => any;
    }
</script>

<style scoped>

</style>