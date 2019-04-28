<template>
    <v-container
            fluid
            fill-height
            grid-list-xl
            justify-center align-center
            mb-5
            px-5
    >
        <slot></slot>
    </v-container>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';

    @Component
    export default class CentredLayout extends Vue {
    }
</script>
