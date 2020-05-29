<template>
    <v-layout row wrap>
        <v-flex xs12>
            <SubtitleSeparator icon="build">
                {{ $t('dashboard.component.actions.title') }}
            </SubtitleSeparator>
        </v-flex>

        <v-flex xs12>
            <div class="text-xs-center">
                <v-btn
                        v-on:click="deleteAnalyses()"
                        color="error"
                        round
                >
                    <v-icon left>delete_forever</v-icon>
                    {{ $t('dashboard.component.actions.deleteAnalyses') }}
                </v-btn>
                <v-btn
                        v-on:click="deleteFiles()"
                        color="error"
                        round
                >
                    <v-icon left>delete_forever</v-icon>
                    {{ $t('dashboard.component.actions.deleteFiles') }}
                </v-btn>
            </div>
        </v-flex>
    </v-layout>
</template>

<script lang="ts">
    import {ConfirmType} from '@/modules/analyse/helpers/confirm';
    import {AnalyseApiService} from '@/modules/analyse/services/analyseApiService';
    import {FileUploadService} from '@/modules/common/service/fileUploadService';
    import {Component, Vue} from 'vue-property-decorator';
    import SubtitleSeparator from '@/modules/common/components/SubtitleSeparator.vue';

    @Component({
        components: {SubtitleSeparator}
    })
    export default class Dashboard extends Vue {

        public async deleteAnalyses() {
            const title = this.$t('dashboard.component.actions.dialogDeleteAnalyses.title').toString();
            const text = this.$t('dashboard.component.actions.dialogDeleteAnalyses.text').toString();
            if (await this.$confirm(title, ConfirmType.DELETE, text)) {
                AnalyseApiService
                    .purgeDeletedAnalyses()
                    .then(response => {
                        const successCount: number = response.data.data;
                        const text = this.$t('dashboard.component.actions.dialogDeleteAnalyses.success', [successCount]).toString();
                        this.$confirm(title, ConfirmType.INFO, text);
                    })
                    .catch(error => {
                        const text = this.$t('dashboard.component.actions.dialogDeleteAnalyses.error').toString();
                        this.$confirm(title, ConfirmType.ERROR, text);
                        return this.$logger.error(error);
                    });
            }
        }

        public async deleteFiles() {
            const title = this.$t('dashboard.component.actions.dialogDeleteFiles.title').toString();
            const text = this.$t('dashboard.component.actions.dialogDeleteFiles.text').toString();
            if (await this.$confirm(title, ConfirmType.DELETE, text)) {
                FileUploadService
                    .purgeUploadedImages()
                    .then(response => {
                        const successCount: number = response.data.data;
                        const text = this.$t('dashboard.component.actions.dialogDeleteFiles.success', [successCount]).toString();
                        this.$confirm(title, ConfirmType.INFO, text);
                    })
                    .catch(error => {
                        const text = this.$t('dashboard.component.actions.dialogDeleteFiles.error').toString();
                        this.$confirm(title, ConfirmType.ERROR, text);
                        return this.$logger.error(error);
                    });
            }
        }
    }
</script>
