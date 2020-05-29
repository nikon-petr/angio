<template>
    <v-layout row>
        <v-flex xs12>
            <v-card>
                <v-card-text>
                    <v-layout row justify-space-between>
                        <v-flex v-if="analyse.originalImage.type == 'IMAGE'" xs4>
                            <div class="subheading">
                                <div>
                                    <span class="font-weight-medium headline">
                                        {{ $t('analyse.component.details.additionalInfo.originalImage') }}
                                    </span>
                                </div>
                            </div>
                            <div v-if="$vuetify.breakpoint.smAndUp">
                                <AnalyseListTablePreviewImage
                                        v-bind:src="analyse.originalImage.url"
                                        v-bind:max-size="400"
                                ></AnalyseListTablePreviewImage>
                            </div>
                        </v-flex>
                        <v-flex xs8>
                            <div class="subheading">
                                <div>
                                    <span class="font-weight-medium headline">
                                        {{ $t('analyse.component.details.additionalInfo.info.title') }}
                                    </span>
                                </div>
                            </div>
                            <div v-if="isDiagnosticianInfoEnabled()">
                                <span class="font-weight-medium text--secondary">
                                    {{ $t('analyse.component.details.additionalInfo.info.diagnostician') }}
                                </span>
                            </div>
                            <div v-if="isDiagnosticianInfoEnabled()">
                                <span class="font-weight-medium subtitle-1">
                                    {{ fullName(analyse.additionalInfo.diagnostician.fullName) }}
                                </span>
                            </div>
                            <div v-if="isAnalyseDateInfoEnabled()"><br/></div>
                            <div v-if="analyse.analyseDate">
                                <span class="font-weight-medium text--secondary">
                                    {{ $t('analyse.component.details.additionalInfo.info.analyseDate') }}
                                </span>
                            </div>
                            <div v-if="analyse.analyseDate">
                                <span class="font-weight-medium subtitle-1">
                                    {{ analyse.analyseDate | moment('DD.MM.YYYY hh:MM') }}
                                </span>
                            </div>
                            <div v-if="isAnalyseTypeInfoEnabled()"><br/></div>
                            <div>
                                <span class="font-weight-medium text--secondary">
                                    {{ $t('analyse.component.details.additionalInfo.info.type') }}
                                </span>
                            </div>
                            <div>
                                <span class="font-weight-medium subtitle-1">
                                    {{ $t('analyse.model.analyseType.' + analyse.additionalInfo.type) }}
                                </span>
                            </div>
                            <div><br/></div>
                            <div>
                                <span class="font-weight-medium text--secondary">
                                    {{ $t('analyse.component.details.additionalInfo.info.shortDescription') }}
                                </span>
                            </div>
                            <div>
                                <span class="font-weight-medium subtitle-1">
                                    {{ analyse.additionalInfo.shortDescription }}
                                </span>
                            </div>
                            <div v-if="isNotEmpty(analyse.additionalInfo.fullDescription)"><br/></div>
                            <div v-if="isNotEmpty(analyse.additionalInfo.fullDescription)">
                                <span class="font-weight-medium text--secondary">
                                    {{ $t('analyse.component.details.additionalInfo.info.fullDescription') }}
                                </span>
                            </div>
                            <div v-if="isNotEmpty(analyse.additionalInfo.fullDescription)">
                                <span class="font-weight-medium subtitle-1">
                                    {{ analyse.additionalInfo.fullDescription }}
                                </span>
                            </div>
                            <div v-if="isNotEmpty(analyse.additionalInfo.comment)"><br/></div>
                            <div v-if="isNotEmpty(analyse.additionalInfo.comment)">
                                <span class="font-weight-medium text--secondary">
                                    {{ $t('analyse.component.details.additionalInfo.info.comment') }}
                                </span>
                            </div>
                            <div v-if="isNotEmpty(analyse.additionalInfo.comment)">
                                <span class="font-weight-medium subtitle-1">
                                    {{ analyse.additionalInfo.comment }}
                                </span>
                            </div>
                            <div v-if="isNotEmpty(analyse.additionalInfo.conclusion)"><br/></div>
                            <div v-if="isNotEmpty(analyse.additionalInfo.conclusion)">
                                <span class="font-weight-medium text--secondary">
                                    {{ $t('analyse.component.details.additionalInfo.info.conclusion') }}
                                </span>
                            </div>
                            <div v-if="isNotEmpty(analyse.additionalInfo.conclusion)">
                                <span class="font-weight-medium subtitle-1">
                                    {{ analyse.additionalInfo.conclusion }}
                                </span>
                            </div>
                        </v-flex>
                    </v-layout>
                </v-card-text>
            </v-card>
        </v-flex>
    </v-layout>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import {Analyse} from '@/modules/analyse/models/analyse';
    import AnalyseListTablePreviewImage from '@/modules/analyse/components/AnalysePreviewImage.vue';
    import FullName from '@/modules/common/models/fullName';
    import StringUtils from '@/utils/stringUtils';

    @Component({
        components: {AnalyseListTablePreviewImage}
    })
    export default class AnalyseDetailsAdditionalInfo extends Vue {

        @Prop()
        public analyse!: Analyse;

        public fullName(name: FullName): string {
            return StringUtils.fullName(name);
        }

        public isNotEmpty(data?: string): boolean {
            return StringUtils.isNotEmpty(data);
        }

        public isDiagnosticianInfoEnabled(): boolean {
            return this.analyse.additionalInfo.diagnostician != null
                && this.analyse.additionalInfo.diagnostician.fullName != null;
        }

        public isAnalyseDateInfoEnabled(): boolean {
            return this.analyse.additionalInfo.diagnostician != null
                && this.analyse.additionalInfo.diagnostician.fullName != null
                && this.analyse.analyseDate != null;
        }

        public isAnalyseTypeInfoEnabled(): boolean {
            return (this.analyse.analyseDate != null
                || (this.analyse.additionalInfo.diagnostician != null
                    && this.analyse.additionalInfo.diagnostician.fullName != null));
        }
    }
</script>
