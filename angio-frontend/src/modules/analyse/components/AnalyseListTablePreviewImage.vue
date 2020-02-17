<template lang="html">
    <v-img
            v-bind:src="src"
            class="elevation-2"
            max-height="250px"
            max-width="250px"
            aspect-ratio="1"
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
    export default class AnalyseListTablePreviewImage extends Vue {

        @Prop()
        public readonly src!: string;
    }
</script>
