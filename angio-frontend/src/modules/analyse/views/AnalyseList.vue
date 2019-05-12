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
                    v-bind:analyse-page="analysePage"
                    v-bind:has-permissions="hasPermissions"
                    v-bind:pagination.sync="pagination"
            ></AnalyseListTable>
        </v-flex>

        <v-flex xs12>
            <AnalyseListTablePagination
                    v-bind:pagination.sync="pagination"
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
    import Page from '@/modules/common/models/page';
    import {UserGetter} from '@/modules/user/store/userStore';
    import AnalyseListTablePagination from '@/modules/analyse/components/AnalyseListTablePagination.vue';
    import Pagination from '@/modules/common/helpers/pagination';

    @Component({
        components: {AnalyseListTablePagination, StackLayout, AnalyseListTable, BaseSubheader, AnalyseListFilter},
    })
    export default class AnalyseList extends Vue {

        public static readonly ROWS_PER_PAGE = 30;

        @State((state) => state.user.settings.locale)
        public readonly locale!: Locale;

        @Getter(UserGetter.HAS_PERMISSIONS)
        public readonly hasPermissions!: (permissions: UserPermission[]) => boolean;

        public analysePage!: Page<Array<AnalyseItem>>;

        public analyseFilter!: AnalyseFilterModel;

        public pagination!: Pagination;

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
                analysePage: {
                    content: [],
                    pageable: {
                        sort: {
                            sorted: false,
                            unsorted: true,
                        },
                        offset: 0,
                        pageSize: AnalyseList.ROWS_PER_PAGE,
                        pageNumber: 0,
                        paged: true,
                        unpaged: false
                    },
                    last: true,
                    totalPages: 0,
                    totalElements: 0,
                    size: AnalyseList.ROWS_PER_PAGE,
                    number: 0,
                    numberOfElements: 0,
                    first: true,
                    sort: {
                        sorted: false,
                        unsorted: true
                    }
                },
                pagination: {
                    sortBy: null,
                    descending: null,
                    page: 1,
                    rowsPerPage: AnalyseList.ROWS_PER_PAGE,
                    totalItems: 0
                }
            }
        }

        @Watch('pagination', {deep: true})
        public onPaginationChange(newVal: Pagination, oldVal: Pagination) {
            this.getAnalysePage()
        }

        @throttle(1000)
        public filter(filter: AnalyseFilterModel) {
            this.analyseFilter = filter;
            this.getAnalysePage();
        }

        public getAnalysePage() {
            this.fetching = true;
            const page: number | undefined = this.pagination.page
                ? this.pagination.page - 1
                : undefined;
            const sort: string | undefined = this.pagination.sortBy
                ? `${this.pagination.sortBy},${this.pagination.descending ? 'desc' : 'asc'}`
                : undefined;
            AnalyseApiService
                .getAnalyseFilter(this.analyseFilter, AnalyseList.ROWS_PER_PAGE, page, sort)
                .then((response) => {
                    this.analysePage = response.data.data;
                    this.pagination.totalItems = response.data.data.totalElements;
                })
                .catch((error) => this.$logger.error(error))
                .finally(() => this.fetching = false)
        }
    }
</script>
