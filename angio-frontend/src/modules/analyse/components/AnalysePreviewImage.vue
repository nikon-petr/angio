<template lang="html">
    <v-img
            v-bind:src="src"
            v-bind:max-height="maxSize"
            v-bind:min-height="minSize"
            v-bind:max-width="maxSize"
            v-bind:min-width="minSize"
            class="elevation-2"
            ma-0
            pa-0
    >
        <template v-slot:placeholder>
            <v-layout
                    fill-height
                    align-center
                    justify-center
                    ma-0
                    pa-0
            >
                <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
            </v-layout>
        </template>
    </v-img>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';

    @Component
    export default class AnalysePreviewImage extends Vue {

        @Prop()
        public readonly src!: string;

        @Prop()
        public readonly maxSize!: number;

        @Prop({default: undefined})
        public readonly minSize?: number;
    }
</script>
