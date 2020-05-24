<template>
    <v-layout row wrap>
        <v-flex xs12>
            <SubtitleSeparator icon="person">
                {{ $t('dashboard.component.organizationsAndUsers.title') }}
            </SubtitleSeparator>
        </v-flex>

        <v-flex xs6>
            <BlockInfo
                    v-bind:title="$t('dashboard.component.organizationsAndUsers.organizationsCount')"
                    v-bind:content="dashboard.statistics.organization.totalCount.toString()"
                    v-bind:fetching="fetching"
                    color="light-blue"
            ></BlockInfo>
        </v-flex>

        <v-flex xs6>
            <BlockInfo
                    v-bind:title="$t('dashboard.component.organizationsAndUsers.usersCount')"
                    v-bind:content="dashboard.statistics.user.totalCount.toString()"
                    v-bind:fetching="fetching"
                    color="blue"
                    color-variant="darken-1"
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
    export default class Dashboard extends Vue {

        @Prop()
        public readonly fetching!: boolean;

        @Prop()
        public readonly dashboard!: DashboardModel;

        public organizationsCount!: string;

        public usersCount!: string;

        public data() {
            return {
                organizationsCount: this.dashboard.statistics.organization.totalCount.toString(),
                usersCount: this.dashboard.statistics.user.totalCount.toString()
            }
        }
    }
</script>
