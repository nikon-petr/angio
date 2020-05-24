<template>
    <v-layout row wrap>
        <v-flex xs12>
            <SubtitleSeparator icon="settings">
                {{ $t('dashboard.component.actions.title') }}
            </SubtitleSeparator>
        </v-flex>

        <v-flex xs12>
            <div class="text-xs-center">
                <v-btn
                        v-on:click="deleteAnalyses()"
                        v-bind:round="true"
                        v-bind:outline="true"
                        class="ma-2"
                        color="error"
                >
                    {{ $t('dashboard.component.actions.deleteAnalyses') }}
                </v-btn>
                <v-btn
                        v-on:click="deleteFiles()"
                        v-bind:round="true"
                        v-bind:outline="true"
                        class="ma-2"
                        color="error"
                >
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
                    .deleteAnalyses()
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
                    .deleteImages()
                    .catch(error => {
                        const text = this.$t('dashboard.component.actions.dialogDeleteFiles.error').toString();
                        this.$confirm(title, ConfirmType.ERROR, text);
                        return this.$logger.error(error);
                    });
            }
        }
    }
</script>
