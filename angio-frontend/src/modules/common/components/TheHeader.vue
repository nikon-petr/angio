<template>
    <v-toolbar app fixed clipped-left>

        <v-toolbar-side-icon v-on:click.stop="toggleDrawer"></v-toolbar-side-icon>
        <v-toolbar-title>{{ $t('common.appName') }}</v-toolbar-title>

        <v-spacer></v-spacer>

        <PreAuthorize is-authenticated>

            <UserMenuButton></UserMenuButton>

            <template slot="not-authorized">

                <v-btn to="/register" active-class flat icon>
                    <v-icon>person_add</v-icon>
                </v-btn>

                <v-btn to="/login" active-class flat icon>
                    <v-icon>exit_to_app</v-icon>
                </v-btn>

            </template>

        </PreAuthorize>

    </v-toolbar>
</template>

<script lang="ts">
    import {Component, Emit, Prop, Vue} from 'vue-property-decorator';
    import {BusEvent} from '@/modules/common/helpers/busEvent';
    import UserMenuButton from '@/modules/user/components/UserMenuButton.vue';
    import PreAuthorize from "@/modules/common/components/PreAuthorize.vue";

    @Component({
        components: {PreAuthorize, UserMenuButton},
    })
    export default class TheHeader extends Vue {

        @Prop()
        public readonly isAuthenticated!: boolean;

        @Emit()
        public logout() {
        }

        public toggleDrawer() {
            this.$bus.emit(BusEvent.TOGGLE_DRAWER_VISIBILITY_EVENT);
        }
    }
</script>

<style scoped>

</style>