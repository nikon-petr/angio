<template>
    <v-toolbar app fixed clipped-left>

        <v-toolbar-side-icon v-on:click.stop="toggleDrawer"></v-toolbar-side-icon>
        <v-toolbar-title>{{ $t('common.appName') }}</v-toolbar-title>

        <v-spacer></v-spacer>

        <div v-show="isAuthenticated">
            <UserMenu></UserMenu>
        </div>

        <div v-show="!isAuthenticated">

            <v-btn to="/register" active-class flat icon>
                <v-icon>person_add</v-icon>
            </v-btn>

            <v-btn to="/login" active-class flat icon>
                <v-icon>exit_to_app</v-icon>
            </v-btn>

        </div>

    </v-toolbar>
</template>

<script lang="ts">
    import {Component, Emit, Prop, Vue} from 'vue-property-decorator';
    import {BusEvent} from '@/modules/common/helpers/busEvent';
    import UserMenu from "@/modules/user/components/UserMenu.vue";
    @Component({
        components: {UserMenu}
    })
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