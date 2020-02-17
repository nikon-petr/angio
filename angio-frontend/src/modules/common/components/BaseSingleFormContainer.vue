<template>
    <v-card class="elevation-12 pa-3 ma-4">

        <v-card-title class="headline font-weight-regular justify-space-between">
            <span>{{ title }}</span>
            <v-spacer></v-spacer>
        </v-card-title>

        <v-card-text>
            <slot name="form"></slot>
        </v-card-text>
        <v-card-actions>
            <slot name="buttons"></slot>
        </v-card-actions>
    </v-card>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';

    @Component
    export default class BaseSingleFormContainer extends Vue {

        @Prop()
        public readonly title!: string;
    }
</script>
