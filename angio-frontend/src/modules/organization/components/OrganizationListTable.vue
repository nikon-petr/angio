<template>
    <v-layout row wrap>
        <v-flex xs12>
            <v-text-field
                    v-bind:label="$t('organization.component.organizationListTable.table.searchField')"
                    v-model="searchField"
                    append-icon="mdi-magnify"
                    type="search"
                    browser-autocomplete="off"
                    clearable
                    hide-details
                    solo
            ></v-text-field>
        </v-flex>

        <v-flex xs12>
            <v-card>
                <v-data-table
                        v-bind:headers="headers"
                        v-bind:items="organizations"
                        v-bind:loading="fetching"
                        v-bind:pagination.sync="pagination"
                        v-bind:total-items="totalItems"
                        v-bind:no-data-text="$t('organization.component.organizationListTable.table.noDataText')"
                        item-key="id"
                        sort-icon="arrow_drop_down"
                        disable-initial-sort
                        hide-actions
                >
                    <template slot="headerCell" slot-scope="props">
                        {{ $t(props.header.text) }}
                    </template>
                    <template v-slot:items="props">
                        <td>
                            <text-highlight v-bind:queries="searchForHighlight">
                                {{ props.item.id }}
                            </text-highlight>
                        </td>
                        <td>
                            <text-highlight v-bind:queries="searchForHighlight">
                                {{ props.item.name }}
                            </text-highlight>
                        </td>
                    </template>
                </v-data-table>
            </v-card>
        </v-flex>

        <v-flex class="pt-2" xs12>
            <BasePagination
                    v-bind:page.sync="page"
                    v-bind:rows-per-page="rowsPerPage"
                    v-bind:total-items="totalItems"
            ></BasePagination>
        </v-flex>
    </v-layout>
</template>

<script lang="ts">
    import {Component, Emit, Vue, Watch} from 'vue-property-decorator';
    import {State} from 'vuex-class';
    import {Locale} from '@/modules/user/store/userState';
    import Pagination from '@/modules/common/helpers/pagination';
    import {SortingDirection} from '@/modules/common/models/page';
    import {OrganizationApiService} from '@/modules/organization/services/organizationApiService';
    import BasePagination from '@/modules/common/components/BasePagination.vue';
    import {Organization} from '@/modules/organization/models/organization';

    @Component({
        components: {BasePagination}
    })
    export default class OrganizationListTable extends Vue {

        public static readonly ROWS_PER_PAGE = 20;

        @State((state) => state.user.settings.locale)
        public readonly locale!: Locale;

        public fetching: boolean = false;

        public headers = [
            {
                text: 'organization.component.organizationListTable.table.headers.id',
                align: 'left',
                value: 'id',
                width: '10%',
                sortable: true
            },
            {
                text: 'organization.component.organizationListTable.table.headers.name',
                align: 'left',
                value: 'name',
                width: '90%',
                sortable: true
            }
        ];

        public organizations: Organization[] = [];

        public searchField: string = '';

        public sort?: string = ''; // format: property,asc|desc

        public page: number = 1;

        public totalItems: number = 0;

        public rowsPerPage: number = OrganizationListTable.ROWS_PER_PAGE;

        @Watch('searchField')
        public onSearchChange(newVal: string | undefined, oldVal: string | undefined) {
            this.findOrganizations();
        }

        @Watch('page')
        public onPaginationChange(newVal: number, oldVal: number) {
            this.findOrganizations();
        }

        @Watch('sort')
        public onSortChange(newVal: string | undefined, oldVal: string | undefined) {
            this.findOrganizations();
        }

        public findOrganizations() {
            let isSearchFieldEmpty = this.searchField == undefined;

            if (!isSearchFieldEmpty && !this.fetching) {
                this.fetching = true;
            }

            OrganizationApiService
                .getOrganizationsFilter(this.rowsPerPage, (this.page - 1), this.sort, this.searchField)
                .then((response) => {
                    this.organizations = response.data.data.content;
                    this.totalItems = response.data.data.totalElements;
                    if (this.totalItems <= this.rowsPerPage) {
                        this.page = 1;
                    }
                })
                .catch((error) => this.$logger.error(error))
                .finally(() => this.fetching = false);
        }

        public update() {
            this.findOrganizations();
        }

        get pagination(): Pagination {
            return {
                sortBy: this.sort ? this.sort.split(',')[0] : null,
                descending: this.sort ? this.sort.split(',')[1] === SortingDirection.DESC : null,
                page: null,
                totalItems: this.totalItems,
                rowsPerPage: null
            }
        }

        set pagination(pagination: Pagination) {
            this.updatePagination(this.mapSortingForApi(pagination.sortBy, pagination.descending));
        }

        @Emit('update:sort')
        public updatePagination(sort: string | undefined) {
            this.sort = sort;

            return sort;
        }

        public mapSortingForApi(sortBy: string | null, descending: boolean | null): string | undefined {
            if (sortBy && descending !== null) {
                return `${sortBy},${descending ? SortingDirection.DESC : SortingDirection.ASC}`;
            } else {
                return undefined;
            }
        }

        get searchForHighlight(): string {
            return this.searchField ? this.searchField : '';
        }
    }
</script>
