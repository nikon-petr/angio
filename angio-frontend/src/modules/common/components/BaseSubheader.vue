<template>
    <h3 class="display-1 font-weight-regular text-capitalize">{{ value }}</h3>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';

    @Component
    export default class BaseSubheader extends Vue {

        @Prop()
        public readonly value!: string;
    }
</script>