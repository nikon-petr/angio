<template>
    <div class="scrollable" v-bind:style="{height: height}">
        <slot></slot>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';

    @Component
    export default class BaseScroller extends Vue {

        @Prop()
        height!: string;

        @Prop()
        notifications!: any;
    }
</script>

<style scoped>
    .scrollable {
        overflow: auto;
    }
</style>