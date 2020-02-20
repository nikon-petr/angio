<template>
    <v-card flat>
        <v-card-text>
            <v-layout row wrap align-center>
                <v-spacer></v-spacer>
                <v-flex xs6>
                    <AnalyseListTablePreviewImage
                            v-bind:src="densityImage.url"
                            v-bind:max-size="500"
                    ></AnalyseListTablePreviewImage>
                </v-flex>
                <v-flex xs4>
                    <v-data-table
                            v-bind:headers="densityTableHeaders"
                            v-bind:items="densities"
                            class="elevation-1"
                            hide-actions
                    >
                        <template slot="headerCell" slot-scope="props">
                            {{ $t(props.header.text) }}
                        </template>
                        <template slot="items" slot-scope="props">
                            <td>{{ props.item.sectorNumber }}</td>
                            <td class="text-xs-center">{{ Math.round(props.item.density  * 100) / 100 }}</td>
                        </template>
                    </v-data-table>
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
                sortable: true
            },
            {
                text: 'analyse.component.details.analyseTabs.density.table.density',
                value: 'density',
                sortable: true
            }
        ];
    }
</script>
