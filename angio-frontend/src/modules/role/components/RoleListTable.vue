<template>
    <v-layout row wrap>
        <v-flex xs12>
            <v-text-field
                    v-model="searchString"
                    v-bind:label="$t('role.component.roleListTable.search')"
                    v-bind:loading="fetching"
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
                        v-bind:search="searchString"
                        v-bind:headers="headers"
                        v-bind:items="rolesDictionary"
                        v-bind:expand="expand"
                        item-key="id"
                        sort-icon="arrow_drop_down"
                        disable-initial-sort
                        hide-actions
                >
                    <template slot="headerCell" slot-scope="props">
                        {{ $t(props.header.text) }}
                    </template>
                    <template v-slot:items="props">
                        <tr
                                v-bind:key="props.item.id"
                                v-bind:active="props.selected"
                                v-on:click="props.expanded = !props.expanded"
                        >
                            <td>
                                <text-highlight v-bind:queries="getSearchString">
                                    {{ props.item.id }}
                                </text-highlight>
                            </td>
                            <td>
                                <text-highlight v-bind:queries="getSearchString">
                                    {{ props.item.description }}
                                </text-highlight>
                            </td>
                            <td class="text-xs-center">
                                <v-icon v-if="props.item.systemRole">check</v-icon>
                            </td>
                        </tr>
                    </template>
                    <template v-slot:expand="props">
                        <RoleListTableDetails
                                v-on:edit-role="editRole"
                                v-on:delete-role="deleteRole"
                                v-bind:role="props.item"
                                v-bind:show-edit-button="showEditButton"
                                v-bind:show-delete-button="showDeleteButton"
                        ></RoleListTableDetails>
                    </template>
                </v-data-table>
            </v-card>
        </v-flex>
    </v-layout>
</template>

<script lang="ts">
    import RoleListTableDetails from '@/modules/role/components/RoleListTableDetails.vue';
    import {Role} from '@/modules/role/models/role';
    import {Component, Emit, Prop, Vue} from 'vue-property-decorator';

    @Component({
        components: {RoleListTableDetails}
    })
    export default class RoleListTable extends Vue {

        @Prop()
        public showEditButton!: boolean;

        @Prop()
        public showDeleteButton!: boolean;

        @Prop()
        public rolesDictionary!: Role[];

        @Prop()
        public fetching!: boolean;

        @Emit()
        public editRole(roleId: number) {
            return roleId;
        }

        @Emit()
        public deleteRole(roleId: number) {
            return roleId;
        }

        public expand: boolean = false;

        public searchString: string = '';

        public headers = [
            {
                text: 'role.component.roleListTable.table.header.id',
                align: 'left',
                value: 'id',
                width: '10%',
                sortable: true
            },
            {
                text: 'role.component.roleListTable.table.header.name',
                align: 'left',
                value: 'description',
                width: '50%',
                sortable: true
            },
            {
                text: 'role.component.roleListTable.table.header.systemRole',
                align: 'center',
                value: 'systemRole',
                width: '40%',
                sortable: true
            }
        ];

        get getSearchString(): string {
            return this.searchString ? this.searchString : '';
        }
    }
</script>
