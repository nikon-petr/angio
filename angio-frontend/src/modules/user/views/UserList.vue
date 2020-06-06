<template>
    <StackLayout>
        <v-flex xs12>
            <BaseSubheader
                    v-on:append-button-click="openAddUserForm"
                    v-bind:value="$t('user.view.userManager.subheader')"
                    v-bind:is-button-enabled="hasPermissions(['USER_CREATE'])"
            ></BaseSubheader>
        </v-flex>

        <v-flex xs12>
            <UserListFilter
                    v-on:submit="fetchFilteredUsers"
                    v-bind.sync="filter"
                    v-bind:fetching="usersFetching"
                    v-bind:organizations-dictionary="organizationsDictionary"
                    v-bind:organizations-fetch="dictionaryFetching"
                    v-bind:roles-dictionary="rolesDictionary"
            ></UserListFilter>
        </v-flex>

        <v-flex xs12>
            <UserListTable
                    v-on:open-role-editor="openEditUserRolesForm"
                    v-on:open-owned-role-editor="openEditUserOwnedRolesForm"
                    v-bind:lock-user="lockUser"
                    v-bind:users="users"
                    v-bind:sort.sync="sort"
                    v-bind:total-items="totalItems"
                    v-bind:search="filter.search"
                    v-bind:show-editor-buttons="hasPermissions(['USER_EDIT'])"
            ></UserListTable>
        </v-flex>

        <v-flex xs12>
            <BasePagination
                    v-bind:page.sync="page"
                    v-bind:rows-per-page="rowsPerPage"
                    v-bind:total-items="totalItems"
            ></BasePagination>
        </v-flex>

        <v-flex xs12>
            <AddUserFrom
                    ref="addUserForm"
                    v-on:users-saved="updateUserList"
                    v-bind:roles-dictionary="rolesDictionary"
                    v-bind:organizations-dictionary="organizationsDictionary"
                    v-bind:owned-roles-to-manage="currentUser && currentUser.ownedRolesToManage"
            ></AddUserFrom>
        </v-flex>

        <v-flex xs12>
            <RoleEditorForm
                    v-on:save="saveRoles"
                    v-bind:title="$t('user.component.roleEditorForm.title')"
                    v-bind:active.sync="roleEditorFormActive"
                    v-bind:roles="editUser && editUser.roles || []"
                    v-bind:fetching="roleEditorFetching"
                    v-bind:error-messages="roleEditorErrorMessages"
                    v-bind:roles-dictionary="rolesDictionary"
                    v-bind:owned-roles-to-manage="currentUser && currentUser.ownedRolesToManage"
            ></RoleEditorForm>
            <RoleEditorForm
                    v-on:save="saveOwnedRoles"
                    v-bind:title="$t('user.component.ownedRoleEditorForm.title')"
                    v-bind:active.sync="ownedRoleEditorFormActive"
                    v-bind:roles="editUser && editUser.ownedRolesToManage || []"
                    v-bind:fetching="ownedRoleEditorFetching"
                    v-bind:error-messages="ownedRoleEditorErrorMessages"
                    v-bind:roles-dictionary="rolesDictionary"
                    v-bind:owned-roles-to-manage="currentUser && currentUser.ownedRolesToManage"
                    allow-empty
            ></RoleEditorForm>
        </v-flex>
    </StackLayout>
</template>

<script lang="ts">
    import BasePagination from '@/modules/common/components/BasePagination.vue';
    import BaseSubheader from '@/modules/common/components/BaseSubheader.vue';
    import StackLayout from '@/modules/common/components/StackLayout.vue';
    import {Organization} from '@/modules/organization/models/organization';
    import {OrganizationApiService} from '@/modules/organization/services/organizationApiService';
    import {Role} from '@/modules/role/models/role';
    import {RoleApiService} from '@/modules/role/services/roleApiService';
    import AddUserFrom from '@/modules/user/components/AddUserFrom.vue';
    import RoleEditorForm from '@/modules/user/components/RoleEditorForm.vue';
    import UserListFilter from '@/modules/user/components/UserListFilter.vue';
    import UserListTable from '@/modules/user/components/UserListTable.vue';
    import {UserDetailsModel, UserFilterModel} from '@/modules/user/models/user';
    import {UserApiService} from '@/modules/user/services/userApiService';
    import {UserPermission} from '@/modules/user/store/userState';
    import {UserGetter} from '@/modules/user/store/userStore';
    import CollectionUtils from '@/utils/collectionUtils';
    import {Component, Ref, Vue, Watch} from 'vue-property-decorator';
    import {Getter, State} from 'vuex-class';

    @Component({
        components: {
            RoleEditorForm,
            AddUserFrom, BasePagination, UserListTable, UserListFilter, StackLayout, BaseSubheader}
    })
    export default class UserList extends Vue {

        public static readonly ROWS_PER_PAGE = 10;

        @Ref('addUserForm')
        public addUserForm!: AddUserFrom;

        @State((state) => state.user.info.id)
        public currentUserId!: string;

        @Getter(UserGetter.HAS_PERMISSIONS)
        public readonly hasPermissions!: (permissions: UserPermission[]) => boolean;

        // begin dicts
        public currentUser!: UserDetailsModel;

        public rolesDictionary!: Role[];

        public organizationsDictionary: Organization[] = [];

        public dictionaryFetching: boolean = false;
        // end dicts

        // role editor state begin
        public roleEditorFormActive = false;
        public ownedRoleEditorFormActive = false;

        public editUser: UserDetailsModel | null = null;

        public roleEditorErrorMessages: string[] = [];
        public ownedRoleEditorErrorMessages: string[] = [];

        public roleEditorFetching = false;
        public ownedRoleEditorFetching = false;
        // role editor state end

        public filter!: UserFilterModel;

        public users: UserDetailsModel[] = [];

        public usersFetching: boolean = false;

        public sort?: string;

        public page: number = 1;

        public rowsPerPage: number = UserList.ROWS_PER_PAGE;

        public totalItems: number = 0;

        @Watch('page')
        public onPaginationChange() {
            this.fetchFilteredUsers();
        }

        @Watch('sort')
        public onSortChange() {
            this.fetchFilteredUsers();
        }

        public fetchFilteredUsers() {
            this.usersFetching = true;
            UserApiService.getUserFilter(this.filter, this.rowsPerPage, this.page - 1, this.sort)
                .then(response => {
                    this.users = response.data.data.content;
                    this.totalItems = response.data.data.totalElements;
                })
                .catch(error => {
                    this.$logger.error(error);
                })
                .finally(() => this.usersFetching = false);

        }

        public saveRoles(roleIds: number[]) {
            if (!this.editUser) {
                return;
            }
            this.roleEditorFetching = true;
            UserApiService.changeRoles(this.editUser.id, roleIds)
                .then(response => {
                    this.roleEditorFormActive = false;
                    this.users = CollectionUtils.updateItem(this.users, response.data.data, user => user.id)
                })
                .catch(error => {
                    this.$logger.error(error);
                    this.roleEditorErrorMessages = ['user.component.roleEditorForm.error'];
                })
                .finally(() => this.roleEditorFetching = false);
        }

        public saveOwnedRoles(roleIds: number[]) {
            if (!this.editUser) {
                return;
            }
            this.ownedRoleEditorFetching = true;
            UserApiService.changeOwnedRoles(this.editUser.id, roleIds)
                .then(response => {
                    this.ownedRoleEditorFormActive = false;
                    this.users = CollectionUtils.updateItem(this.users, response.data.data, user => user.id)
                })
                .catch(error => {
                    this.$logger.error(error);
                    this.ownedRoleEditorErrorMessages = ['user.component.roleEditorForm.error'];
                })
                .finally(() => this.ownedRoleEditorFetching = false);
        }

        public openAddUserForm() {
            // @ts-ignore
            this.addUserForm.open();
        }

        public openEditUserRolesForm(user: UserDetailsModel) {
            this.editUser = user;
            this.roleEditorFormActive = true;
        }

        public openEditUserOwnedRolesForm(user: UserDetailsModel) {
            this.editUser = user;
            this.ownedRoleEditorFormActive = true;
        }

        public async fetchDictionaries() {
            this.dictionaryFetching = true;
            await OrganizationApiService.getOrganizationsFilter(Number.MAX_SAFE_INTEGER)
                .then(response => {
                    this.organizationsDictionary = response.data.data.content;
                })
                .catch(error => this.$logger.error(error));
            await RoleApiService.getAllRoles()
                .then(response => {
                    this.rolesDictionary = response.data.data;
                })
                .catch(error => this.$logger.debug(error));
            await UserApiService.getUserById(this.currentUserId)
                .then(response => {
                    this.currentUser = response.data.data;
                })
                .catch(error => this.$logger.debug(error));
        }

        public lockUser(userId: string, locked: boolean) {
            return new Promise(async (resolve, reject) => {
                await UserApiService.postLocked(userId, locked)
                    .then(response => {
                        let user = this.users.find(user => user && user.id == userId);
                        if (user) {
                            user.locked = response.data.data.locked;
                        }
                        resolve();
                    })
                    .catch(error => {
                        this.$logger.error(error);
                        reject();
                    });
            });
        }

        public updateUserList(users: UserDetailsModel[]) {
            this.users = CollectionUtils.appendItems(this.users, users);
        }

        public data() {
            return {
                currentUser: undefined,
                rolesDictionary: undefined,
                organizationsDictionary: undefined,
                filter: {
                    search: undefined,
                    enabled: undefined,
                    locked: undefined,
                    organizationId: undefined,
                    roleIds: undefined,
                    ownedRoleIds: undefined
                },
                sort: undefined
            };
        }

        public mounted() {
            this.fetchFilteredUsers();
            this.fetchDictionaries()
                .finally(() => this.dictionaryFetching = false);
        }
    }
</script>
