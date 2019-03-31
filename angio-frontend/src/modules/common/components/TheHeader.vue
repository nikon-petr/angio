<template>
    <v-toolbar app fixed clipped-left>
        <v-toolbar-side-icon v-on:click.stop="toggleDrawer"></v-toolbar-side-icon>
        <v-toolbar-title>AngioVision</v-toolbar-title>
    </v-toolbar>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import {BusEvents} from '@/modules/common/helpers/busEvents';

    @Component
    export default class TheHeader extends Vue {

        toggleDrawer(){
            this.$bus.emit(BusEvents.TOGGLE_DRAWER_VISIBILITY_EVENT);
        }
    }
</script>

<style scoped>

</style>