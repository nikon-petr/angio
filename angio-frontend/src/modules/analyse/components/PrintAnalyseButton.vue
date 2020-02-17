<template lang="html">
    <v-tooltip bottom>
        <template v-slot:activator="{ on }">
            <v-btn
                    v-bind:loading="fetching"
                    v-on:click="onPrintAnalyseReport"
                    v-on="on"
                    class="ma-0"
                    ripple
                    icon
                    flat
            >
                <v-icon>print</v-icon>
            </v-btn>
        </template>
        <span>{{ $t('analyse.component.analyseListTablePreview.button.print.tooltip') }}</span>
    </v-tooltip>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import {AnalyseApiService} from '@/modules/analyse/services/analyseApiService';

    @Component
    export default class PrintAnalyseButton extends Vue {

        @Prop()
        public readonly analyseId!: number;

        public fetching: boolean = false;

        public onPrintAnalyseReport() {
            this.fetching = true;
            AnalyseApiService.printAnalyseReport(this.analyseId)
                .finally(() => this.fetching = false);
        }
    }
</script>
