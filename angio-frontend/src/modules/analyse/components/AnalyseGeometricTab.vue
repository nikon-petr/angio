<template>
    <v-card flat>
        <v-card-text>
            <v-layout row wrap align-center>
                <v-flex xs12 class="text-xs-center" pb-0 mb-0>
                    <p class="headline pb-0 mb-0">
                        {{ $t('analyse.component.details.analyseTabs.geometricAnalyse.images.title') }}
                    </p>
                </v-flex>
                <v-flex xs4 class="text-xs-center">
                    <p class="subtitle-1">
                        {{ $t('analyse.component.details.analyseTabs.geometricAnalyse.images.data.original') }}
                    </p>
                    <AnalyseListTablePreviewImage
                            v-bind:src="originalImage.url"
                            v-bind:max-size="500"
                    ></AnalyseListTablePreviewImage>
                </v-flex>
                <v-flex xs4 class="text-xs-center">
                    <p class="subtitle-1">
                        {{ $t('analyse.component.details.analyseTabs.geometricAnalyse.images.data.binarized') }}
                    </p>
                    <AnalyseListTablePreviewImage
                            v-bind:src="geometricAnalyse.binarizedImage.url"
                            v-bind:max-size="500"
                    ></AnalyseListTablePreviewImage>
                </v-flex>
                <v-flex xs4 class="text-xs-center">
                    <p class="subtitle-1">
                        {{ $t('analyse.component.details.analyseTabs.geometricAnalyse.images.data.skeleton') }}
                    </p>
                    <AnalyseListTablePreviewImage
                            v-bind:src="geometricAnalyse.skeletonizedImage.url"
                            v-bind:max-size="500"
                    ></AnalyseListTablePreviewImage>
                </v-flex>
                <v-flex xs12 class="text-xs-center" pb-0 mb-0>
                    <p class="headline pb-0 mb-0">
                        {{ $t('analyse.component.details.analyseTabs.geometricAnalyse.separatedVessels.title') }}
                    </p>
                </v-flex>
                <v-flex xs12>
                    <v-data-table
                            v-bind:headers="headers"
                            v-bind:items="geometricAnalyse.vessels"
                            class="elevation-1"
                            v-bind:rows-per-page-items="rowNumbersVessels"
                            v-bind:no-data-text="$t('analyse.component.details.analyseTabs.geometricAnalyse.separatedVessels.table.noData')"
                    >
                        <template slot="headerCell" slot-scope="props">
                            {{ $t(props.header.text) }}
                        </template>
                        <template slot="items" slot-scope="props">
                            <td>{{ props.item.id }}</td>
                            <td>
                                <AnalyseListTablePreviewImage
                                    v-bind:src="props.item.vesselImage.url"
                                    v-bind:max-size="500"
                                ></AnalyseListTablePreviewImage>
                            </td>
                            <td>
                                <AnalyseListTablePreviewImage
                                        v-bind:src="props.item.mainVesselImage.url"
                                        v-bind:max-size="500"
                                ></AnalyseListTablePreviewImage>
                            </td>
                            <td>{{ props.item.countOfBranches }}</td>
                            <td class="text-xs-right">{{ props.item.tortuosityDegree.toFixed(4) }}</td>
                            <td class="text-xs-right">{{ props.item.branchingDegree.toFixed(4) }}</td>
                            <td class="text-xs-right">{{ props.item.area }}</td>
                            <td class="text-xs-right">{{ props.item.areaPercent.toFixed(4) }}</td>
                            <td class="center">
                                <DeleteVesselButton
                                        v-if="hasPermissions(['ANALYSE_EDIT'])"
                                        v-bind:analyse-id="analyseId"
                                        v-bind:vessel-id="props.item.id"
                                        v-bind:delete-vessel="deleteVessel"
                                        class="ma-0 pa-0"
                                ></DeleteVesselButton>
                            </td>
                        </template>
                        <template slot="pageText" slot-scope="props" >
                            {{ $t('analyse.component.details.analyseTabs.geometricAnalyse.separatedVessels.table.info',
                                [props.pageStart, props.pageStop, props.itemsLength])
                            }}
                        </template>
                    </v-data-table>
                </v-flex>
                <v-flex xs12 class="text-xs-center" pb-0 mb-0>
                    <p class="headline pb-0 mb-0">
                        {{ $t('analyse.component.details.analyseTabs.geometricAnalyse.commonVessels.title') }}
                    </p>
                </v-flex>
                <v-flex xs12>
                    <v-data-table
                            v-bind:headers="headers_result"
                            v-bind:items="result"
                            hide-actions
                            class="elevation-1"
                    >
                        <template slot="headerCell" slot-scope="props">
                            {{ $t(props.header.text) }}
                        </template>
                        <template slot="items" slot-scope="props">
                            <td>{{ props.item.sumId }}</td>
                            <td>{{ props.item.sumCountOfBranches }}</td>
                            <td class="text-xs-right">{{ props.item.avgTortuosityDegree.toFixed(4) }}</td>
                            <td class="text-xs-right">{{ props.item.avgBranchingDegree.toFixed(4) }}</td>
                            <td class="text-xs-right">{{ props.item.sumArea.toFixed(4) }}</td>
                            <td class="text-xs-right">{{ props.item.sumAreaPercent.toFixed(4) }}</td>
                        </template>
                    </v-data-table>
                </v-flex>
            </v-layout>
        </v-card-text>
    </v-card>
</template>

<script lang="ts">
    import {Component, Prop, Vue, Watch} from 'vue-property-decorator';
    import {UserPermission} from '@/modules/user/store/userState';
    import StaticFile from '@/modules/common/models/staticFile';
    import AnalyseListTablePreviewImage from '@/modules/analyse/components/AnalysePreviewImage.vue';
    import {GeometricAnalyse} from '@/modules/analyse/models/analyse';
    import DeleteVesselButton from '@/modules/analyse/components/DeleteVesselButton.vue';

    @Component({
        components: {DeleteVesselButton, AnalyseListTablePreviewImage}
    })
    export default class AnalyseGeometricTab extends Vue {

        @Prop()
        public readonly analyseId!: number;

        @Prop()
        public readonly originalImage!: StaticFile;

        @Prop()
        public readonly geometricAnalyse!: GeometricAnalyse;

        @Prop()
        public readonly deleteVessel!: (analyseId: number, vesselId: number) => Promise<void>;

        @Prop()
        public readonly hasPermissions!: (permissions: UserPermission[]) => boolean;

        public rowNumbersVessels = [5];

        public headers = [
            {
                text: 'analyse.component.details.analyseTabs.geometricAnalyse.separatedVessels.table.vesselNum',
                align: 'left',
                value: 'id',
                width: '5%'
            },
            {
                text: 'analyse.component.details.analyseTabs.geometricAnalyse.separatedVessels.table.vesselImage',
                align: 'left',
                value: 'vesselImage',
                sortable: false,
                width: '20%'
            },
            {
                text: 'analyse.component.details.analyseTabs.geometricAnalyse.separatedVessels.table.mainVesselImage',
                align: 'left',
                value: 'mainVesselImage',
                sortable: false,
                width: '20%'
            },
            {
                text: 'analyse.component.details.analyseTabs.geometricAnalyse.separatedVessels.table.countOfBranches',
                align: 'left',
                value: 'countOfBranches',
                width: '10%'
            },
            {
                text: 'analyse.component.details.analyseTabs.geometricAnalyse.separatedVessels.table.tortuosityDegree',
                align: 'left',
                value: 'tortuosityDegree',
                width: '10%'
            },
            {
                text: 'analyse.component.details.analyseTabs.geometricAnalyse.separatedVessels.table.branchingDegree',
                align: 'left',
                value: 'branchingDegree',
                width: '10%'
            },
            {
                text: 'analyse.component.details.analyseTabs.geometricAnalyse.separatedVessels.table.area',
                align: 'left',
                value: 'area',
                width: '10%'
            },
            {
                text: 'analyse.component.details.analyseTabs.geometricAnalyse.separatedVessels.table.areaPercent',
                align: 'left',
                value: 'areaPercent',
                width: '10%'
            },
            {
                text: 'analyse.component.details.analyseTabs.geometricAnalyse.separatedVessels.table.actions',
                align: 'left',
                value: 'actions',
                sortable: false,
                width: '5%'
            }
        ];

        public headers_result = [
            {
                text: 'analyse.component.details.analyseTabs.geometricAnalyse.commonVessels.table.vesselsCount',
                align: 'left',
                value: 'sum_id',
                sortable: false,
                width: '20%'
            },
            {
                text: 'analyse.component.details.analyseTabs.geometricAnalyse.commonVessels.table.branchesCount',
                align: 'left',
                value: 'sum_count_of_branches',
                sortable: false,
                width: '20%'
            },
            {
                text: 'analyse.component.details.analyseTabs.geometricAnalyse.commonVessels.table.avgTortuosity',
                align: 'left',
                value: 'avg_tortuosity_degree',
                sortable: false,
                width: '20%'
            },
            {
                text: 'analyse.component.details.analyseTabs.geometricAnalyse.commonVessels.table.avgBranching',
                align: 'left',
                value: 'avg_branching_degree',
                sortable: false,
                width: '200%'
            },
            {
                text: 'analyse.component.details.analyseTabs.geometricAnalyse.commonVessels.table.commonSInPx',
                align: 'left',
                value: 'sum_area',
                sortable: false,
                width: '10%'
            },
            {
                text: 'analyse.component.details.analyseTabs.geometricAnalyse.commonVessels.table.commonSInPercent',
                align: 'left',
                value: 'sum_area_percent',
                sortable: false,
                width: '10%'
            }
        ];

        public result = this.calculateResult(this.geometricAnalyse);

        @Watch('geometricAnalyse', {deep: true, immediate: true})
        public onGeometricAnalyseChange(newVal: GeometricAnalyse, oldVal: GeometricAnalyse) {
            this.result = this.calculateResult(newVal);
            this.$emit('update:result', undefined);
        }

        public calculateResult(geometricAnalyse: GeometricAnalyse) {
            return [
                {
                    sumId: geometricAnalyse.vessels.length,
                    sumCountOfBranches: geometricAnalyse.vessels
                        .reduce((sum, vessel) => sum + vessel.countOfBranches, 0),
                    avgTortuosityDegree: geometricAnalyse.vessels
                        .reduce((sum, vessel) => sum + (vessel.tortuosityDegree / this.geometricAnalyse.vessels.length), 0),
                    avgBranchingDegree: geometricAnalyse.vessels
                        .reduce((sum, vessel) => sum + (vessel.branchingDegree / this.geometricAnalyse.vessels.length), 0),
                    sumArea: geometricAnalyse.vessels
                        .reduce((sum, vessel) => sum + vessel.area, 0),
                    sumAreaPercent: geometricAnalyse.vessels
                        .reduce((sum, vessel) => sum + vessel.areaPercent, 0)
                }
            ]
        }
    }
</script>
