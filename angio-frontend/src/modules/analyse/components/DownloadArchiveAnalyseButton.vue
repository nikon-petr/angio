<template lang="html">
    <v-tooltip bottom>
        <template v-slot:activator="{ on }">
            <v-btn
                    v-bind:loading="fetching"
                    v-on:click="onDownloadAnalyseArchive"
                    v-on="on"
                    class="ma-0"
                    ripple
                    icon
                    flat
            >
                <v-icon>archive</v-icon>
            </v-btn>
        </template>
        <span>{{ $t('analyse.component.analyseListTablePreview.button.downloadZip.tooltip') }}</span>
    </v-tooltip>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import {AnalyseApiService} from '@/modules/analyse/services/analyseApiService';

    @Component
    export default class DownloadArchiveAnalyseButton extends Vue {

        @Prop()
        public readonly analyseId!: number;

        public fetching: boolean = false;

        public onDownloadAnalyseArchive() {
            this.fetching = true;
            AnalyseApiService.downloadArchive(this.analyseId)
                .finally(() => this.fetching = false);
        }
    }
</script>
