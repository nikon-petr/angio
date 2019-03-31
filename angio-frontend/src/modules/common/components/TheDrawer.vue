<template>
    <v-navigation-drawer

            v-model="drawer"
            clipped
            fixed
            app
    >
        <v-list dense>
            <v-list-tile v-on:click="">

                <v-list-tile-action>
                    <v-icon>dashboard</v-icon>
                </v-list-tile-action>

                <v-list-tile-content>
                    <v-list-tile-title>Dashboard</v-list-tile-title>
                </v-list-tile-content>

            </v-list-tile>

            <v-list-tile v-on:click="">

                <v-list-tile-action>
                    <v-icon>settings</v-icon>
                </v-list-tile-action>

                <v-list-tile-content>
                    <v-list-tile-title>Settings</v-list-tile-title>
                </v-list-tile-content>

            </v-list-tile>
        </v-list>
    </v-navigation-drawer>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import {BusEvents} from '@/modules/common/helpers/busEvents';

    @Component
    export default class TheDrawer extends Vue {
        drawer: boolean = false;

        setDrawerVisibility() {
            this.drawer = !this.drawer;
        }

        created() {
            this.$bus.on(BusEvents.TOGGLE_DRAWER_VISIBILITY_EVENT, this.setDrawerVisibility);
        }

        beforeDestroy() {
            this.$bus.off(BusEvents.TOGGLE_DRAWER_VISIBILITY_EVENT, this.setDrawerVisibility);
        }
    }
</script>

<style scoped>

</style>