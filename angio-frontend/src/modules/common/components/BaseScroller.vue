<template>
    <div class="scrollable" v-bind:style="{maxHeight: height}">
        <slot></slot>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';

    @Component
    export default class BaseScroller extends Vue {

        @Prop()
        public readonly height!: string;
    }
</script>

<style scoped>
    .scrollable {
        overflow: auto;
    }
</style>