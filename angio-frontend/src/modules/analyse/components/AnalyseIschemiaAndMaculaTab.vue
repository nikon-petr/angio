<template>
    <v-card flat>
        <v-card-text>
            <v-layout row wrap>
                <v-flex shrink align-self-center>
                    <AnalyseListTablePreviewImage
                            v-bind:src="ischemiaImage.url"
                            v-bind:max-size="600"
                            v-bind:min-size="600"
                    ></AnalyseListTablePreviewImage>
                </v-flex>
                <v-flex xs4>
                    <h4 class="headline">
                        {{ $t('analyse.component.details.analyseTabs.ischemiaAndMacula.macula.title') }}
                    </h4>
                    <p class="pt-3">
                        <span class="font-weight-medium text--secondary">
                            {{ $t('analyse.component.details.analyseTabs.ischemiaAndMacula.macula.info.area') }}
                        </span>
                        <span>
                            {{ macula.area }}
                        </span>
                        <br>
                        <span class="font-weight-medium text--secondary">
                            {{ $t('analyse.component.details.analyseTabs.ischemiaAndMacula.macula.info.radius') }}
                        </span>
                        <span>
                            {{ macula.radius | trunc() }}
                        </span>
                        <br>
                        <span class="font-weight-medium text--secondary">
                            {{ $t('analyse.component.details.analyseTabs.ischemiaAndMacula.macula.info.center') }}
                        </span>
                        <span>
                            ({{ macula.x | trunc() }}; {{ macula.y | trunc() }})
                        </span>
                    </p>
                    <h4 class="headline">
                        {{ $t('analyse.component.details.analyseTabs.ischemiaAndMacula.ischemia.title') }}
                    </h4>
                    <div>
                        <v-data-table
                                v-bind:headers="ischemiaTableHeaders"
                                v-bind:items="ischemias"
                                v-bind:rows-per-page-items="rowNumbersIcshemia"
                                v-bind:no-data-text="$t('analyse.component.details.analyseTabs.ischemiaAndMacula.ischemia.table.noData')"
                                class="elevation-0 pt-3"
                        >
                            <template slot="headerCell" slot-scope="props">
                                {{ $t(props.header.text) }}
                            </template>
                            <template slot="items" slot-scope="props">
                                <td>{{ props.item.zoneNumber }}</td>
                                <td class="text-xs-center">{{ props.item.area }}</td>
                                <td class="text-xs-center">{{ props.item.x | trunc() }}</td>
                                <td class="text-xs-center">{{ props.item.y | trunc() }}</td>
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
                class: 'text-uppercase',
                sortable: true
            },
            {
                text: 'analyse.component.details.analyseTabs.ischemiaAndMacula.ischemia.table.area',
                value: 'area',
                class: 'text-uppercase',
                sortable: true
            },
            {
                text: 'analyse.component.details.analyseTabs.ischemiaAndMacula.ischemia.table.x',
                class: 'text-uppercase',
                sortable: false
            },
            {
                text: 'analyse.component.details.analyseTabs.ischemiaAndMacula.ischemia.table.y',
                class: 'text-uppercase',
                sortable: false
            },
        ];
    }
</script>
