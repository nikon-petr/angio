<template>
    <v-toolbar app fixed clipped-left>
        <v-toolbar-side-icon v-on:click.stop="toggleDrawer"></v-toolbar-side-icon>
        <v-toolbar-title>AngioVision</v-toolbar-title>
        <v-spacer></v-spacer>
        <div v-show="isAuthenticated">
            <v-btn
                    v-on:click.stop="logout"
                    flat
                    small
            >{{ $t('common.component.theHeader.button.logout') }}</v-btn>
        </div>
        <div v-show="!isAuthenticated">
            <v-btn
                    to="/login"
                    exact
                    active-class
                    flat
                    small
            >{{ $t('common.component.theHeader.button.login') }}</v-btn>
            <v-btn
                    to="/register"
                    exact
                    active-class
                    flat
                    small
            >{{ $t('common.component.theHeader.button.register') }}</v-btn>
        </div>
    </v-toolbar>
</template>

<script lang="ts">
    import {Component, Emit, Prop, Vue} from 'vue-property-decorator';
    import {BusEvent} from '@/modules/common/helpers/busEvent';

    @Component
    export default class TheHeader extends Vue {

        @Prop()
        readonly isAuthenticated!: boolean;

        @Emit()
        logout() {}

        toggleDrawer() {
            this.$bus.emit(BusEvent.TOGGLE_DRAWER_VISIBILITY_EVENT);
        }
    }
</script>

<style scoped>

</style>