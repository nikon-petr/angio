<template>
    <v-layout row>
        <v-flex xs12>
            <v-card>
                <v-card-text>
                    <v-layout row justify-space-between>
                        <v-flex xs4>
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
                            <div v-if="analyse.additionalInfo.diagnostician && analyse.additionalInfo.diagnostician.fullName">
                                <span class="font-weight-medium text--secondary">
                                    {{ $t('analyse.component.details.additionalInfo.info.diagnostician') }}
                                </span>
                            </div>
                            <div v-if="analyse.additionalInfo.diagnostician && analyse.additionalInfo.diagnostician.fullName">
                                <span class="font-weight-medium subtitle-1">
                                    {{ fullName(analyse.additionalInfo.diagnostician.fullName) }}
                                </span>
                            </div>
                            <div v-if="analyse.additionalInfo.diagnostician && analyse.additionalInfo.diagnostician.fullName && analyse.analyseDate"><br/></div>
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
                            <div v-if="analyse.analyseDate || (analyse.additionalInfo.diagnostician && analyse.additionalInfo.diagnostician.fullName)"><br/></div>
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

    @Component({
        components: {AnalyseListTablePreviewImage}
    })
    export default class AnalyseDetailsAdditionalInfo extends Vue {

        @Prop()
        public analyse!: Analyse;

        public fullName(name: FullName): string {
            const patronymic: string = name.patronymic ? name.patronymic : '';
            return `${name.lastname} ${name.firstname} ${patronymic}`;
        }

        public isNotEmpty(data?: string): boolean {
            if (data) {
                return data.length > 0;
            } else {
                return false;
            }
        }
    }
</script>
