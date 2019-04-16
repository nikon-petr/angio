<template>
    <v-layout mb-4>
        <h3 class="display-1 font-weight-regular">{{ value }}</h3>
    </v-layout>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';

    @Component
    export default class BaseSubheader extends Vue {

        @Prop()
        public readonly value!: string;
    }
</script>