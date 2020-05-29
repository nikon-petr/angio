<template>
    <v-layout row>
        <v-flex xs12>
            <v-card>
                <v-card-text>
                    <v-tabs centered>
                        <v-tab ripple v-if="analyse.executionConfiguration.geometric">
                            {{ $t('analyse.component.details.analyseTabs.geometricAnalyse.tabName') }}
                        </v-tab>
                        <v-tab-item v-if="analyse.executionConfiguration.geometric">
                            <AnalyseGeometricTab
                                    v-bind:analyse-id="analyse.id"
                                    v-bind:original-image="analyse.originalImage"
                                    v-bind:geometric-analyse="analyse.geometricAnalyse"
                                    v-bind:has-permissions="hasPermissions"
                                    v-bind:delete-vessel="deleteVessel"
                            ></AnalyseGeometricTab>
                        </v-tab-item>
                        <v-tab ripple v-if="analyse.executionConfiguration.maculaBloodFlow">
                            {{ $t('analyse.component.details.analyseTabs.ischemiaAndMacula.tabName') }}
                        </v-tab>
                        <v-tab-item v-if="analyse.executionConfiguration.maculaBloodFlow">
                            <AnalyseIschemiaAndMaculaTab
                                    v-bind:ischemia-image="analyse.bloodFlowAnalyse.ischemiaImage"
                                    v-bind:ischemias="analyse.bloodFlowAnalyse.ischemias"
                                    v-bind:macula="analyse.bloodFlowAnalyse.macula"
                            ></AnalyseIschemiaAndMaculaTab>
                        </v-tab-item>
                        <v-tab ripple v-if="analyse.executionConfiguration.maculaBloodFlow">
                            {{ $t('analyse.component.details.analyseTabs.density.title.macula') }}
                        </v-tab>
                        <v-tab-item v-if="analyse.executionConfiguration.maculaBloodFlow">
                            <AnalyseDensityTab
                                    v-bind:density-image="analyse.bloodFlowAnalyse.densityImage"
                                    v-bind:densities="analyse.bloodFlowAnalyse.densities"
                            ></AnalyseDensityTab>
                        </v-tab-item>
                        <v-tab ripple v-if="analyse.executionConfiguration.opticDiskBloodFlow">
                            {{ $t('analyse.component.details.analyseTabs.density.title.opticDisk') }}
                        </v-tab>
                        <v-tab-item v-if="analyse.executionConfiguration.opticDiskBloodFlow">
                            <AnalyseDensityTab
                                    v-bind:density-image="analyse.bloodFlowAnalyse.densityImage"
                                    v-bind:densities="analyse.bloodFlowAnalyse.densities"
                            ></AnalyseDensityTab>
                        </v-tab-item>
                        <v-tab ripple v-if="analyse.executionConfiguration.profileCysticVolume">
                            {{ $t('analyse.component.details.analyseTabs.profileCysticVolume.tabName') }}
                        </v-tab>
                        <v-tab-item v-if="analyse.executionConfiguration.profileCysticVolume">
                            <AnalyseProfileCysticVolumeTab
                                    v-bind:density-image="analyse.bloodFlowAnalyse.densityImage"
                                    v-bind:cystic-volume="analyse.profileAnalyse.cysticVolume"
                            ></AnalyseProfileCysticVolumeTab>
                        </v-tab-item>
                    </v-tabs>
                </v-card-text>
            </v-card>
        </v-flex>
    </v-layout>
</template>

<script lang="ts">
    import AnalyseProfileCysticVolumeTab from '@/modules/analyse/components/AnalyseProfileCysticVolumeTab.vue';
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import {Analyse} from '@/modules/analyse/models/analyse';
    import AnalyseDensityTab from '@/modules/analyse/components/AnalyseDensityTab.vue';
    import AnalyseIschemiaAndMaculaTab from '@/modules/analyse/components/AnalyseIschemiaAndMaculaTab.vue';
    import AnalyseGeometricTab from '@/modules/analyse/components/AnalyseGeometricTab.vue';
    import {UserPermission} from '@/modules/user/store/userState';

    @Component({
        components: {AnalyseProfileCysticVolumeTab, AnalyseGeometricTab, AnalyseIschemiaAndMaculaTab,
            AnalyseDensityTab}
    })
    export default class AnalyseTabsContainer extends Vue {

        @Prop()
        public analyse!: Analyse;

        @Prop()
        public readonly hasPermissions!: (permissions: UserPermission[]) => boolean;

        @Prop()
        public readonly deleteVessel!: (analyseId: number, vesselId: number) => Promise<void>;

    }
</script>
