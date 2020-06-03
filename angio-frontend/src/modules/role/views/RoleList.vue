<template>
    <StackLayout>
        <v-flex xs12>
            <BaseSubheader
                    v-on:append-button-click="openAddUserForm"
                    v-bind:value="$t('role.view.roleList.title')"
                    v-bind:is-button-enabled="hasPermissions(['ROLE_CREATE'])"
            ></BaseSubheader>
        </v-flex>
        <v-flex xs12>
            <RoleListTable
                    v-on:edit-role="openEditRoleForm"
                    v-on:delete-role="deleteRole"
                    v-bind:fetching="fetching"
                    v-bind:roles-dictionary="rolesDictionary"
                    v-bind:show-edit-button="hasPermissions(['ROLE_EDIT'])"
                    v-bind:show-delete-button="hasPermissions(['ROLE_REMOVE'])"
            ></RoleListTable>
        </v-flex>
        <v-flex xs12>
            <AddRoleForm
                    v-on:add-role="addRole"
                    v-bind:show.sync="openedAddRoleForm"
                    v-bind:roles-dictionary="rolesDictionary"
                    v-bind:permissions-dictionary="permissionsDictionary"
                    v-bind:owned-permissions="ownedPermissions"
                    v-bind:fetching="fetchingAddRole"
                    v-bind:error-messages="addRoleErrorMessages"
            ></AddRoleForm>
            <EditRoleForm
                    v-on:edit-role="editRole"
                    v-bind:default-role="editRoleObject"
                    v-bind:show.sync="openedEditRoleForm"
                    v-bind:roles-dictionary="rolesDictionary"
                    v-bind:permissions-dictionary="permissionsDictionary"
                    v-bind:owned-permissions="ownedPermissions"
                    v-bind:fetching="fetchingEditRole"
                    v-bind:error-messages="editRoleErrorMessages"
            ></EditRoleForm>
        </v-flex>
    </StackLayout>
</template>

<script lang="ts">
    import {ConfirmType} from '@/modules/analyse/helpers/confirm';
    import BaseSubheader from '@/modules/common/components/BaseSubheader.vue';
    import StackLayout from '@/modules/common/components/StackLayout.vue';
    import AddRoleForm from '@/modules/role/components/AddRoleForm.vue';
    import EditRoleForm from '@/modules/role/components/EditRoleForm.vue';
    import RoleListTable from '@/modules/role/components/RoleListTable.vue';
    import {Permission, Role, UpdateRoleModel} from '@/modules/role/models/role';
    import {RoleApiService} from '@/modules/role/services/roleApiService';
    import {UserApiService} from '@/modules/user/services/userApiService';
    import {UserPermission} from '@/modules/user/store/userState';
    import {UserGetter} from '@/modules/user/store/userStore';
    import CollectionUtils from '@/utils/collectionUtils';
    import {Component, Vue} from 'vue-property-decorator';
    import {Getter, State} from 'vuex-class';

    @Component({
        components: {RoleListTable, EditRoleForm, AddRoleForm, BaseSubheader, StackLayout}
    })
    export default class RoleList extends Vue {

        @State((state) => state.user.info.id)
        public meId!: string;

        @Getter(UserGetter.HAS_PERMISSIONS)
        public readonly hasPermissions!: (permissions: UserPermission[]) => boolean;

        public fetching: boolean = false;
        public fetchingAddRole: boolean = false;
        public fetchingEditRole: boolean = false;

        public addRoleErrorMessages: string[] = [];
        public editRoleErrorMessages: string[] = [];

        public openedAddRoleForm = false;
        public openedEditRoleForm = false;

        public rolesDictionary: Role[] = [];
        public permissionsDictionary: Permission[] = [];
        public ownedPermissions: Permission[] = [];

        public editRoleId: number | null = null;

        get editRoleObject(): Role | undefined {
            return this.rolesDictionary.find(r => r.id === this.editRoleId);
        }

        public openAddUserForm() {
            this.openedAddRoleForm = true;
        }

        public openEditRoleForm(roleId: number) {
            this.openedEditRoleForm = true;
            this.editRoleId = roleId;
        }

        public async fetchDictionaries() {
            this.fetching = true;
            await RoleApiService.getAllRoles()
                .then(response => {
                    this.rolesDictionary = response.data.data;
                })
                .catch(error => this.$logger.debug(error));
            await RoleApiService.getAllPermissions()
                .then(response => {
                    this.permissionsDictionary = response.data.data;
                })
                .catch(error => this.$logger.debug(error));
            await UserApiService.getUserById(this.meId)
                .then(response => {
                    this.ownedPermissions = response.data.data.ownedRolesToManage.flatMap(role => role.permissions);
                })
                .catch(error => this.$logger.debug(error));
        }

        public addRole(role: UpdateRoleModel) {
            this.fetching = true;
            this.fetchingAddRole = true;
            RoleApiService.createRole(role)
                .then(response => {
                    this.openedAddRoleForm = false;
                    this.editRoleErrorMessages = [];
                    this.rolesDictionary = CollectionUtils.appendItem(this.rolesDictionary, response.data.data);
                })
                .catch(error => {
                    if (error.response.status == 400) {
                        this.addRoleErrorMessages = [error.response.data.error.message];
                    }
                    this.$logger.error(error);
                })
                .finally(() => {
                    this.fetching = false;
                    this.fetchingAddRole = false
                });
        }

        public editRole(role: UpdateRoleModel) {
            this.fetching = true;
            this.fetchingEditRole = false;
            RoleApiService.updateRole(role.id as number, role)
                .then(response => {
                    this.openedEditRoleForm = false;
                    this.editRoleErrorMessages = [];
                    this.rolesDictionary = CollectionUtils.updateItem(this.rolesDictionary, response.data.data, (r) => r.id);
                })
                .catch(error => {
                    if (error.response.status == 400) {
                        this.editRoleErrorMessages = [error.response.data.error.message];
                    }
                    return this.$logger.error(error);
                })
                .finally(() => {
                    this.fetching = false;
                    this.fetchingEditRole = false;
                });
        }

        public async deleteRole(roleId: number) {
            const role = this.rolesDictionary.find(r => r.id === roleId);
            const roleName = role ? role.description : roleId;
            const title = this.$t('role.view.roleList.delete.confirm.title').toString();
            const text = this.$t('role.view.roleList.delete.confirm.text', [roleName]).toString();
            if (await this.$confirm(title, ConfirmType.DELETE, text)) {
                RoleApiService.deleteRole(roleId)
                    .then(response => {
                        this.rolesDictionary = CollectionUtils.removeByCondition(this.rolesDictionary, (r) => r.id === roleId);
                    })
                    .catch(error => {
                        if (error.response.status == 400) {
                            const title = this.$t('role.view.roleList.delete.error.title').toString();
                            const text = this.$t('role.view.roleList.delete.error.text', [roleName]).toString();
                            this.$confirm(title, ConfirmType.ERROR, text);
                        }
                        return this.$logger.error(error);
                    });
            }
        }

        public mounted() {
            this.fetchDictionaries()
                .finally(() => this.fetching = false);
        }
    }
</script>
