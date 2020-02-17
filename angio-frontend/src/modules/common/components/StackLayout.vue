<template>
    <v-container
            fluid
            grid-list-xl
            align-start
            justify-space-between
            mb-5
            px-5
    >
        <v-layout row wrap>
            <slot></slot>
        </v-layout>
    </v-container>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';

    @Component
    export default class StackLayout extends Vue {
    }
</script>

<style scoped>

</style>