<template>
    <v-card flat>
        <v-card-text>
            <v-layout row wrap>
                <v-spacer></v-spacer>
                <v-flex xs6>
                    <AnalyseListTablePreviewImage
                            v-bind:src="ischemiaImage.url"
                            v-bind:max-size="500"
                    ></AnalyseListTablePreviewImage>
                </v-flex>
                <v-flex xs6>
                    <p class="headline">
                        {{ $t('analyse.component.details.analyseTabs.ischemiaAndMacula.macula.title') }}
                    </p>
                    <div>
                        {{ $t('analyse.component.details.analyseTabs.ischemiaAndMacula.macula.info.area',
                            [macula.area])
                        }}
                        <br>
                        {{ $t('analyse.component.details.analyseTabs.ischemiaAndMacula.macula.info.radius',
                            [(Math.round(macula.radius * 100) / 100)])
                        }}
                        <br>
                        {{ $t('analyse.component.details.analyseTabs.ischemiaAndMacula.macula.info.center',
                            [(Math.round(macula.x * 100) / 100), (Math.round(macula.y * 100) / 100)])
                        }}
                    </div>
                    <div class="pt-3">
                        <p class="headline">
                            {{ $t('analyse.component.details.analyseTabs.ischemiaAndMacula.ischemia.title') }}
                        </p>
                    </div>
                    <div>
                        <v-data-table
                                v-bind:headers="ischemiaTableHeaders"
                                v-bind:items="ischemias"
                                class="elevation-1"
                                v-bind:rows-per-page-items="rowNumbersIcshemia"
                                v-bind:no-data-text="$t('analyse.component.details.analyseTabs.ischemiaAndMacula.ischemia.table.noData')"
                        >
                            <template slot="headerCell" slot-scope="props">
                                {{ $t(props.header.text) }}
                            </template>
                            <template slot="items" slot-scope="props">
                                <td>{{ props.item.zoneNumber }}</td>
                                <td class="text-xs-center">{{ props.item.area }}</td>
                                <td class="text-xs-center">{{ Math.round(props.item.x * 100) / 100 }}</td>
                                <td class="text-xs-center">{{ Math.round(props.item.y * 100) / 100 }}</td>
                            </template>
                            <template slot="pageText" slot-scope="props" >
                                {{ $t('analyse.component.details.analyseTabs.ischemiaAndMacula.ischemia.table.info',
                                    [props.pageStart, props.pageStop, props.itemsLength])
                                }}
                            </template>
                        </v-data-table>
                    </div>
                </v-flex>
                <v-spacer></v-spacer>
            </v-layout>
        </v-card-text>
    </v-card>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import AnalyseListTablePreviewImage from '@/modules/analyse/components/AnalysePreviewImage.vue';
    import StaticFile from '@/modules/common/models/staticFile';
    import {Ischemia, Macula} from '@/modules/analyse/models/analyse';

    @Component({
        components: {AnalyseListTablePreviewImage},
    })
    export default class AnalyseIschemiaAndMaculaTab extends Vue {

        @Prop()
        public readonly ischemiaImage!: StaticFile;

        @Prop()
        public readonly ischemias!: Ischemia[];

        @Prop()
        public readonly macula!: Macula;

        public rowNumbersIcshemia = [5];

        public ischemiaTableHeaders = [
            {
                text: 'analyse.component.details.analyseTabs.ischemiaAndMacula.ischemia.table.sectorNum',
                value: 'zoneNumber',
                sortable: true
            },
            {
                text: 'analyse.component.details.analyseTabs.ischemiaAndMacula.ischemia.table.area',
                value: 'area',
                sortable: true
            },
            {
                text: 'analyse.component.details.analyseTabs.ischemiaAndMacula.ischemia.table.x',
                sortable: false
            },
            {
                text: 'analyse.component.details.analyseTabs.ischemiaAndMacula.ischemia.table.y',
                sortable: false
            },
        ];
    }
</script>
