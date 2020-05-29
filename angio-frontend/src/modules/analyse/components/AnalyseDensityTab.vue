<template>
    <v-card flat>
        <v-card-text>
            <v-layout row wrap>
                <v-flex xs6 align-self-center>
                    <AnalyseListTablePreviewImage
                            v-bind:src="densityImage.url"
                            v-bind:max-size="500"
                    ></AnalyseListTablePreviewImage>
                </v-flex>
                <v-flex xs6 v-if="densities.length > 1">
                    <p class="headline">
                        {{ $t('analyse.component.details.analyseTabs.density.title.macula') }}
                    </p>
                    <v-data-table
                            v-bind:headers="densityTableHeaders"
                            v-bind:items="densities"
                            class="elevation-0"
                            hide-actions
                    >
                        <template slot="headerCell" slot-scope="props">
                            {{ $t(props.header.text) }}
                        </template>
                        <template slot="items" slot-scope="props">
                            <td>{{ props.item.sectorNumber }}</td>
                            <td class="text-xs-center">{{ props.item.density | round() }}</td>
                        </template>
                    </v-data-table>
                </v-flex>
                <v-flex xs4 v-else>
                    <p class="headline">
                        {{ $t('analyse.component.details.analyseTabs.density.title.opticDisk') }}
                    </p>
                    <span class="font-weight-medium text--secondary">
                        {{ $t('analyse.component.details.analyseTabs.density.singleValue') }}
                    </span>
                    <span>
                        {{ densities[0].density | round() }}%
                    </span>
                </v-flex>
                <v-spacer></v-spacer>
            </v-layout>
        </v-card-text>
    </v-card>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import StaticFile from '@/modules/common/models/staticFile';
    import {Density} from '@/modules/analyse/models/analyse';
    import AnalyseListTablePreviewImage from '@/modules/analyse/components/AnalysePreviewImage.vue';

    @Component({
        components: {AnalyseListTablePreviewImage}
    })
    export default class AnalyseDensityTab extends Vue {

        @Prop()
        public readonly densityImage!: StaticFile;

        @Prop()
        public readonly densities!: Density[];

        public densityTableHeaders = [
            {
                text: 'analyse.component.details.analyseTabs.density.table.sectorNum',
                value: 'sectorNumber',
                class: 'text-uppercase',
                sortable: true
            },
            {
                text: 'analyse.component.details.analyseTabs.density.table.density',
                value: 'density',
                class: 'text-uppercase',
                sortable: true
            }
        ];
    }
</script>
