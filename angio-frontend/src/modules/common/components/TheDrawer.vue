<template>
    <v-navigation-drawer
            v-model="drawer"
            temporary
            clipped
            fixed
            app
    >
        <v-list dense>
            <PreAuthorize is-authenticated>
                <v-list-tile v-on:click="">

                    <v-list-tile-action>
                        <v-icon>settings</v-icon>
                    </v-list-tile-action>

                    <v-list-tile-content>
                        <v-list-tile-title>Settings</v-list-tile-title>
                    </v-list-tile-content>

                </v-list-tile>
            </PreAuthorize>
        </v-list>
    </v-navigation-drawer>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import {BusEvent} from '@/modules/common/helpers/busEvent';
    import PreAuthorize from "@/modules/common/components/PreAuthorize.vue";

    @Component({
        components: {PreAuthorize}
    })
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