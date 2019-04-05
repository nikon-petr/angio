<template>
    <v-navigation-drawer
            v-model="drawer"
            temporary
            clipped
            fixed
            app
    >
        <v-list dense>
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
    import {BusEvent} from '@/modules/common/helpers/busEvent';

    @Component
    export default class TheDrawer extends Vue {

        public drawer: boolean = false;

        public toggleDrawerVisibility() {
            this.drawer = !this.drawer;
        }

        public created() {
            this.$bus.on(BusEvent.TOGGLE_DRAWER_VISIBILITY_EVENT, this.toggleDrawerVisibility);
        }

        public beforeDestroy() {
            this.$bus.off(BusEvent.TOGGLE_DRAWER_VISIBILITY_EVENT, this.toggleDrawerVisibility);
        }
    }
</script>

<style scoped>

</style>