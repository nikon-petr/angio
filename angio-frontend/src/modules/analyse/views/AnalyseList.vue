<template>
    <StackLayout>
        <v-flex xs12>
            <BaseSubheader
                    v-bind:value="$t('analyse.view.analyseList.subheader')"
            ></BaseSubheader>
        </v-flex>

        <v-flex xs12>
            <AnalyseListFilter
                    v-bind:locale="locale"
                    v-bind:fetching="fetching"
                    v-on:send-form="filter"
            ></AnalyseListFilter>
        </v-flex>

        <v-flex xs12>
            <AnalyseListTable
                    v-bind:analyse-page-content="analysePageContent"
                    v-bind:has-permissions="hasPermissions"
                    v-bind:sort.sync="sort"
                    v-bind:total-items="totalItems"
                    v-bind:search="analyseFilter.search"
            ></AnalyseListTable>
        </v-flex>

        <v-flex xs12>
            <AnalyseListTablePagination
                    v-bind:page.sync="page"
                    v-bind:rows-per-page="rowsPerPage"
                    v-bind:total-items="totalItems"
            ></AnalyseListTablePagination>
        </v-flex>
    </StackLayout>
</template>

<script lang="ts">
    import {Component, Vue, Watch} from 'vue-property-decorator';
    import {Getter, State} from 'vuex-class';
    import {throttle} from 'helpful-decorators';
    import BaseSubheader from '@/modules/common/components/BaseSubheader.vue';
    import AnalyseListFilter from '@/modules/analyse/components/AnalyseListFilter.vue';
    import {Locale, UserPermission} from '@/modules/user/store/userState';
    import AnalyseItem, {AnalyseFilterModel} from '@/modules/analyse/models/analyse';
    import AnalyseListTable from '@/modules/analyse/components/AnalyseListTable.vue';
    import StackLayout from '@/modules/common/components/StackLayout.vue';
    import {AnalyseApiService} from '@/modules/analyse/services/AnalyseApiService';
    import {UserGetter} from '@/modules/user/store/userStore';
    import AnalyseListTablePagination from '@/modules/analyse/components/AnalyseListTablePagination.vue';
    import {Dictionary} from 'vue-router/types/router';

    @Component({
        components: {AnalyseListTablePagination, StackLayout, AnalyseListTable, BaseSubheader, AnalyseListFilter},
    })
    export default class AnalyseList extends Vue {

        public static readonly ROWS_PER_PAGE = 30;

        @State((state) => state.user.settings.locale)
        public readonly locale!: Locale;

        @Getter(UserGetter.HAS_PERMISSIONS)
        public readonly hasPermissions!: (permissions: UserPermission[]) => boolean;

        public analysePageContent: AnalyseItem[] = [];

        public analyseFilter!: AnalyseFilterModel;

        public sort?: string;

        public page!: number;

        public rowsPerPage: number = AnalyseList.ROWS_PER_PAGE;

        public totalItems: number = 0;

        public fetching: boolean = false;

        public data() {
            return {
                analyseFilter: {
                    search: undefined,
                    statuses: [],
                    startDate: undefined,
                    endDate: undefined,
                    singleDate: undefined,
                    isStarred: undefined
                },
                sort: this.$route.query && this.$route.query.sort ? this.$route.query.sort as string : undefined,
                page: this.$route.query && this.$route.query.page ? parseInt(this.$route.query.page as string) : 1,
            }
        }

        @Watch('page')
        public onPaginationChange(newVal: number, oldVal: number) {
            const paginationQuery: Dictionary<string> = {
                page: newVal ? newVal.toString() : undefined,
            } as Dictionary<string>;

            this.$router.replace({
                path: this.$route.path,
                query: {...this.$route.query, ...paginationQuery}
            });

            this.getAnalysePage()
        }

        @Watch('sort')
        public onSortChange(newVal: string | undefined, oldVal: string | undefined) {
            const sortQuery: Dictionary<string> = {
                sort: newVal ? newVal.toString() : undefined,
            } as Dictionary<string>;

            this.$router.replace({
                path: this.$route.path,
                query: {...this.$route.query, ...sortQuery}
            });

            this.getAnalysePage()
        }

        @throttle(1000)
        public filter(filter: AnalyseFilterModel) {
            this.analyseFilter = filter;
            this.getAnalysePage();
        }

        public getAnalysePage() {
            this.fetching = true;
            AnalyseApiService
                .getAnalyseFilter(this.analyseFilter, this.rowsPerPage, this.page - 1 , this.sort)
                .then((response) => {
                    this.analysePageContent = [...response.data.data.content];
                    this.totalItems = response.data.data.totalElements;
                })
                .catch((error) => this.$logger.error(error))
                .finally(() => this.fetching = false)
        }

        public created() {
            if (this.$route.query && !this.$route.query.page) {
                const paginationQuery: Dictionary<string> = {
                    page: this.page ? this.page.toString() : undefined,
                } as Dictionary<string>;

                this.$router.replace({
                    path: this.$route.path,
                    query: {...this.$route.query, ...paginationQuery}
                });
            }

            if (this.$route.query && !this.$route.query.sort) {
                const sortQuery: Dictionary<string> = {
                    sort: this.sort ? this.sort.toString() : undefined,
                } as Dictionary<string>;

                this.$router.replace({
                    path: this.$route.path,
                    query: {...this.$route.query, ...sortQuery}
                });
            }
        }
    }
</script>
