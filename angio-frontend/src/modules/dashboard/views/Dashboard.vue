<template>
    <StackLayout>
        <v-flex xs12>
            <BaseSubheader
                    v-bind:value="$t('dashboard.view.dashboard.title')"
            ></BaseSubheader>
        </v-flex>

        <v-flex xs12>
            <AnalyseInfo
                    v-bind:dashboard="dashboard"
                    v-bind:fetching="fetching"
            ></AnalyseInfo>
        </v-flex>

        <v-flex xs12>
            <OrganizationsAndUsersInfo
                    v-bind:dashboard="dashboard"
                    v-bind:fetching="fetching"
            ></OrganizationsAndUsersInfo>
        </v-flex>

        <v-flex xs12>
            <Actions></Actions>
        </v-flex>
    </StackLayout>
</template>

<script lang="ts">
    import BuiltInErrorMessage from '@/modules/common/components/BuiltInErrorMessage.vue';
    import Actions from '@/modules/dashboard/components/Actions.vue';
    import AnalyseInfo from '@/modules/dashboard/components/AnalyseInfo.vue';
    import OrganizationsAndUsersInfo from '@/modules/dashboard/components/OrganizationsAndUsersInfo.vue';
    import {DashboardApiService} from '@/modules/dashboard/services/dashboardApiService';
    import {Component, Vue} from 'vue-property-decorator';
    import StackLayout from '@/modules/common/components/StackLayout.vue';
    import BaseSubheader from '@/modules/common/components/BaseSubheader.vue';
    import SubtitleSeparator from '@/modules/common/components/SubtitleSeparator.vue';

    @Component({
        components: {BuiltInErrorMessage, Actions, OrganizationsAndUsersInfo, StackLayout,
            BaseSubheader, SubtitleSeparator, AnalyseInfo}
    })
    export default class Dashboard extends Vue {

        public fetching: boolean = false;

        public dashboard = {
            statistics: {
                analyse: {
                    totalCount: 0,
                    successCount: 0,
                    successPercent: 0,
                    failedCount: 0,
                    failedPercent: 0
                },
                user: {
                    totalCount: 0
                },
                organization: {
                    totalCount: 0
                }
            }
        };

        public mounted() {
            if (!this.fetching) {
                this.fetching = true;
            }

            DashboardApiService
                .getDashboard()
                .then((response) => {
                    this.dashboard = response.data.data;
                })
                .catch((error) => {
                    this.$logger.error(error)
                })
                .finally(() => this.fetching = false)
        }
    }
</script>
