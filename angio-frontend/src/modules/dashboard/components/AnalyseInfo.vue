<template>
    <v-layout row wrap>
        <v-flex xs12>
            <SubtitleSeparator icon="info">
                {{ $t('dashboard.component.analysesInfo.title') }}
            </SubtitleSeparator>
        </v-flex>

        <v-flex xs4>
            <BlockInfo
                    v-bind:title="$t('dashboard.component.analysesInfo.totalCount')"
                    v-bind:content="dashboard.statistics.analyse.totalCount.toString()"
                    v-bind:fetching="fetching"
                    color="cyan"
                    color-variant="lighten-1"
            ></BlockInfo>
        </v-flex>

        <v-flex xs4>
            <BlockInfo
                    v-bind:title="$t('dashboard.component.analysesInfo.successCount')"
                    v-bind:content="formatSuccessValueWithPercent()"
                    v-bind:fetching="fetching"
                    color="green"
                    color-variant="lighten-1"
            ></BlockInfo>
        </v-flex>

        <v-flex xs4>
            <BlockInfo
                    v-bind:title="$t('dashboard.component.analysesInfo.failedCount')"
                    v-bind:content="formatFailedValueWithPercent()"
                    v-bind:fetching="fetching"
                    color="red"
                    color-variant="lighten-1"
            ></BlockInfo>
        </v-flex>
    </v-layout>
</template>

<script lang="ts">
    import BlockInfo from '@/modules/dashboard/components/BlockInfo.vue';
    import {DashboardModel} from '@/modules/dashboard/models/dashboard';
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import SubtitleSeparator from '@/modules/common/components/SubtitleSeparator.vue';

    @Component({
        components: {BlockInfo, SubtitleSeparator}
    })
    export default class AnalyseInfo extends Vue {

        @Prop()
        public readonly fetching!: boolean;

        @Prop()
        public readonly dashboard!: DashboardModel;

        public formatSuccessValueWithPercent(): string {
            return this.formatValueWithPercent(
                this.dashboard.statistics.analyse.successCount,
                this.dashboard.statistics.analyse.successPercent
            );
        }

        public formatFailedValueWithPercent(): string {
            return this.formatValueWithPercent(
                this.dashboard.statistics.analyse.failedCount,
                this.dashboard.statistics.analyse.failedPercent
            );
        }

        public formatValueWithPercent(value: number, percent: number): string {
            return `${value} (${(isNaN(percent) ? 0 : percent.toFixed(2))}%)`;
        }
    }
</script>
