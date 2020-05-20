<template>
    <div>
        <v-data-table
                v-bind:headers="adoptedHeaders"
                v-bind:items="users"
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
                <div>
                    <UserListTableDetails
                            v-on:open-role-editor="openRoleEditor"
                            v-bind:lock-user="lockUser"
                            v-bind:user="props.item"
                    ></UserListTableDetails>
                </div>
            </template>
            <template v-slot:items="props">
                <tr
                        v-on:click="props.expanded = !props.expanded"
                        v-bind:key="props.item.id"
                        v-bind:active="props.selected"
                >
                    <td class="text-xs-left">
                        <text-highlight v-bind:queries="searchForHighlight">
                            {{ props.item.id }}
                        </text-highlight>
                    </td>
                    <td class="text-xs-left" >
                        <text-highlight v-bind:queries="searchForHighlight">
                            {{ props.item.fullName | compactFullName() }}
                        </text-highlight>
                    </td>
                    <td class="text-xs-left">
                        <text-highlight v-bind:queries="searchForHighlight">
                            {{ props.item.email }}
                        </text-highlight>
                    </td>
                    <td v-if="!$vuetify.breakpoint.smAndDown"  class="text-xs-left">
                        <text-highlight v-bind:queries="searchForHighlight">
                            {{ organizationName(props.item.organization) }}
                        </text-highlight>
                    </td>
                </tr>
            </template>
        </v-data-table>
        <RoleEditorForm
                ref="roleEditorForm"
                v-bind:roles-dictionary="rolesDictionary"
                v-bind:owned-roles-to-manage="ownedRolesToManage"
        ></RoleEditorForm>
    </div>
</template>

<script lang="ts">
    import {Component, Vue, Prop, Emit, Ref} from 'vue-property-decorator';
    import Pagination from '@/modules/common/helpers/pagination';
    import {SortingDirection} from '@/modules/common/models/page';
    import {Role, UserDetailsModel} from '@/modules/user/models/user';
    import {Organization} from '@/modules/organization/models/organization';
    import UserListTableDetails from '@/modules/user/components/UserListTableDetails.vue';
    import RoleEditorForm from '@/modules/user/components/RoleEditorForm.vue';

    @Component({
        components: {RoleEditorForm, UserListTableDetails}
    })
    export default class UserListTable extends Vue {

        @Ref('roleEditorForm')
        public roleEditorForm!: RoleEditorForm;

        @Prop()
        public rolesDictionary!: Role[];

        @Prop()
        public ownedRolesToManage!: Role[];

        @Prop()
        public readonly lockUser!: (id: string, locked: boolean) => Promise<void>;

        @Prop()
        public readonly users!: UserDetailsModel[];

        @Prop()
        public readonly sort?: string; // format: fieldName,asc|desc

        @Prop()
        public readonly totalItems!: number;

        @Prop()
        public readonly search?: string;

        public expand: boolean = false;

        public headers = [
            {text: 'user.component.userListTable.column.id', value: 'id', class: 'text-uppercase', align: 'left'},
            {text: 'user.component.userListTable.column.fullName', value: 'fullName', class: 'text-uppercase', align: 'left'},
            {
                text: 'user.component.userListTable.column.email',
                value: 'email',
                class: 'text-uppercase',
                align: 'left',
                hide: 'xs'
            },
            {
                text: 'user.component.userListTable.column.organizationName',
                value: 'organization.name',
                class: 'text-uppercase',
                align: 'left',
                hide: 'smAndDown',
            },
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

        public organizationName(organization: Organization): string {
            return organization ? organization.name : this.$t('user.component.userListTable.value.undefined.organizationName').toString();
        }

        public mapSortingForApi(sortBy: string | null, descending: boolean | null): string | undefined {
            if (sortBy && descending !== null) {
                return `${sortBy},${descending ? SortingDirection.DESC : SortingDirection.ASC}`;
            } else {
                return undefined;
            }
        }

        public openRoleEditor(user: UserDetailsModel) {
            // @ts-ignore
            this.roleEditorForm.open(user);
        }
    }
</script>
