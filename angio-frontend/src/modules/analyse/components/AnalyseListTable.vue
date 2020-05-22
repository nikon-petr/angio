<template>
    <div>
        <v-data-table
                v-bind:headers="adoptedHeaders"
                v-bind:items="analysePageContent"
                v-bind:expand="expand"
                v-bind:pagination.sync="pagination"
                v-bind:total-items="totalItems"
                item-key="id"
                class="elevation-2"
                sort-icon="arrow_drop_down"
                disable-initial-sort
                hide-actions
        >
            <template slot="headerCell" slot-scope="props">
                {{ $t(props.header.text) }}
            </template>
            <template v-slot:expand="props">
                <AnalyseListTablePreview
                        v-bind:id="props.item.id"
                        v-bind:name="props.item.name"
                        v-bind:diagnostician="props.item.diagnostician"
                        v-bind:patient="props.item.patient.fullName"
                        v-bind:short-description="props.item.shortDescription"
                        v-bind:analyse-type="props.item.analyseType"
                        v-bind:status="props.item.status"
                        v-bind:original-image-url="props.item.originalImage.url"
                        v-bind:has-permissions="hasPermissions"
                        v-bind:delete-analyse="deleteAnalyse"
                ></AnalyseListTablePreview>
            </template>
            <template v-slot:items="props">
                <tr
                        v-bind:key="props.item.id"
                        v-bind:active="props.selected"
                        v-on:click="props.expanded = !props.expanded"
                >
                    <td>
                        <v-checkbox
                                v-bind:input-value="props.item.starred"
                                v-on:click.stop="setStarredAnalyse(props.item.id, !props.item.starred)"
                                color="yellow accent-3"
                                on-icon="star"
                                off-icon="star_border"
                                primary
                                hide-details
                        ></v-checkbox>
                    </td>
                    <td class="text-xs-left">
                        <text-highlight v-bind:queries="searchForHighlight">
                            {{ props.item.id }}
                        </text-highlight>
                    </td>
                    <td class="text-xs-left">
                        <text-highlight v-bind:queries="searchForHighlight">
                            {{ props.item.name }}
                        </text-highlight>
                    </td>
                    <td v-if="!$vuetify.breakpoint.xs" class="text-xs-left">
                        {{ props.item.analyseDate | moment('DD.MM.YYYY') }}
                    </td>
                    <td v-if="!$vuetify.breakpoint.smAndDown" class="text-xs-left" >
                        <text-highlight v-bind:queries="searchForHighlight">
                            {{ props.item.patient.fullName | compactFullName() }}
                        </text-highlight>
                    </td>
                    <td v-if="!$vuetify.breakpoint.mdAndDown" class="text-xs-left" >
                        <text-highlight v-bind:queries="searchForHighlight">
                            {{ props.item.diagnostician | compactFullName() }}
                        </text-highlight>
                    </td>
                    <td class="text-xs-center">
                        <v-icon v-bind:color="statuseIconsColors[props.item.status.type]">
                            {{ statuseIcons[props.item.status.type] }}
                        </v-icon>
                    </td>
                </tr>
            </template>
        </v-data-table>
    </div>
</template>

<script lang="ts">
    import {Component, Emit, Prop, Vue} from 'vue-property-decorator';
    import {SortingDirection} from '@/modules/common/models/page';
    import AnalyseItem, {AnalyseStatusType} from '@/modules/analyse/models/analyse';
    import {UserPermission} from '@/modules/user/store/userState';
    import AnalyseListTablePreview from '@/modules/analyse/components/AnalyseListTablePreview.vue';
    import Pagination from '@/modules/common/helpers/pagination';

    @Component({
        components: {AnalyseListTablePreview}
    })
    export default class AnalyseListTable extends Vue {

        @Prop()
        public readonly analysePageContent!: AnalyseItem[];

        @Prop()
        public readonly hasPermissions!: (permissions: UserPermission[]) => boolean;

        @Prop()
        public readonly sort?: string; // format: fieldName,asc|desc

        @Prop()
        public readonly totalItems!: number;

        @Prop()
        public readonly search?: string;

        @Prop()
        public readonly setStarredAnalyse!: (id: number, starred: boolean) => void;

        @Prop()
        public readonly deleteAnalyse!: (id: number) => Promise<void>;

        public expand: boolean = false;

        public statuseIcons = {
            [AnalyseStatusType.CREATED]: 'cloud_done',
            [AnalyseStatusType.SUCCESS]: 'done',
            [AnalyseStatusType.IN_PROGRESS]: 'schedule',
            [AnalyseStatusType.FAILED]: 'clear'
        };

        public statuseIconsColors = {
            [AnalyseStatusType.CREATED]: 'accent',
            [AnalyseStatusType.SUCCESS]: 'success',
            [AnalyseStatusType.IN_PROGRESS]: 'accent',
            [AnalyseStatusType.FAILED]: 'error'
        };

        public headers = [
            {text: '', value: 'starred', align: 'left', sortable: false},
            {text: 'analyse.component.analyseListTable.column.id', value: 'id', class: 'text-uppercase', align: 'left'},
            {text: 'analyse.component.analyseListTable.column.name', value: 'name', class: 'text-uppercase', align: 'left'},
            {
                text: 'analyse.component.analyseListTable.column.date',
                value: 'analyseDate',
                class: 'text-uppercase',
                align: 'left',
                hide: 'xs'
            },
            {
                text: 'analyse.component.analyseListTable.column.patient',
                value: 'patient',
                class: 'text-uppercase',
                align: 'left',
                hide: 'smAndDown',
                sortable: false
            },
            {
                text: 'analyse.component.analyseListTable.column.diagnostician',
                value: 'diagnostician',
                class: 'text-uppercase',
                align: 'left',
                hide: 'mdAndDown',
                sortable: false
            },
            {
                text: 'analyse.component.analyseListTable.column.statusType',
                value: 'status.type',
                class: 'text-uppercase',
                align: 'center',
                sortable: false
            }
        ];

        get adoptedHeaders() {
            return this.headers.filter(h => !h.hide || !this.$vuetify.breakpoint[h.hide]);
        }

        get searchForHighlight(): string {
            return this.search ? this.search : '';
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
            return sort;
        }

        public mapSortingForApi(sortBy: string | null, descending: boolean | null): string | undefined {
            if (sortBy && descending !== null) {
                return `${sortBy},${descending ? SortingDirection.DESC : SortingDirection.ASC}`;
            } else {
                return undefined;
            }
        }
    }
</script>
