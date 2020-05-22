<template>
    <BaseDialogFormContainer
            v-bind:open="active"
            v-bind:title="$t('user.component.addUserFrom.title')"
            v-bind:max-width="1000"
    >
        <template slot="form">
            <v-form
                    ref="form"
                    v-model="valid"
                    v-on:submit.prevent="submit"
                    id="add-user-form"
            >
                <v-container
                        grid-list-md
                        justify-space-between
                        align-content-space-around
                        d-layout
                >
                    <v-layout row wrap>
                        <v-flex xs6>
                            <v-text-field
                                    v-model="newUser.email"
                                    v-bind:label="$t('user.component.addUserFrom.email.field')"
                                    v-bind:rules="[v => !!v || $t('user.component.addUserFrom.email.validation.NotEmpty'),
                                            v => v.match(this.emailPattern) || $t('common.validation.IncorrectEmailFormat'),
                                            checkEmailUnique]"
                                    v-bind:disabled="fetching"
                                    data-test-id="email__input"
                                    persistent-hint
                                    type="text"
                                    name="email"
                                    prepend-inner-icon="person"
                                    browser-autocomplete="off"
                                    maxlength="30"
                                    outline
                                    required
                            ></v-text-field>
                        </v-flex>
                        <v-flex xs6>
                            <v-autocomplete
                                    v-model="newUser.organizationId"
                                    v-bind:items="organizationsDictionary"
                                    v-bind:label="$t('user.component.addUserFrom.organization.field')"
                                    v-bind:rules="[v => !!v || $t('user.component.addUserFrom.organization.validation.NotEmpty')]"
                                    prepend-inner-icon="domain"
                                    item-value="id"
                                    item-text="name"
                                    single-line
                                    clearable
                                    outline
                            ></v-autocomplete>
                        </v-flex>
                        <v-flex xs6>
                            <v-autocomplete
                                    v-model="newUser.roleIds"
                                    v-bind:items="rolesDictionary"
                                    v-bind:item-disabled="item => !checkRoleManaged(item.id)"
                                    v-bind:label="$t('user.component.addUserFrom.roles.field')"
                                    v-bind:rules="[v => v.length > 0 || $t('user.component.addUserFrom.roles.NotEmpty')]"
                                    item-text="description"
                                    item-value="id"
                                    prepend-inner-icon="security"
                                    deletable-chips
                                    single-line
                                    clearable
                                    multiple
                                    outline
                                    chips
                            ></v-autocomplete>
                        </v-flex>
                        <v-flex xs6>
                            <v-autocomplete
                                    v-model="newUser.ownedRoleToManageIds"
                                    v-bind:items="rolesDictionary"
                                    v-bind:item-disabled="item => !checkRoleManaged(item.id)"
                                    v-bind:label="$t('user.component.addUserFrom.ownedRoles.field')"
                                    item-text="description"
                                    item-value="id"
                                    prepend-inner-icon="security"
                                    deletable-chips
                                    single-line
                                    clearable
                                    multiple
                                    outline
                                    chips
                            ></v-autocomplete>
                        </v-flex>
                        <v-flex xs12 v-show="formData.length">
                            <v-data-table
                                    v-bind:items="formData"
                                    v-bind:expand="expand"
                                    item-key="email"
                                    style="overflow: auto; max-height: 400px"
                                    hide-actions
                                    hide-headers
                            >
                                <template v-slot:items="props">
                                    <tr
                                            v-on:click="props.expanded = !props.expanded"
                                            v-bind:key="props.item.email"
                                            v-bind:active="props.selected"
                                    >
                                        <td>{{ getOrganizationName(props.item.organizationId) }}</td>
                                        <td>{{ props.item.email }}</td>
                                        <td class="text-xs-right">
                                            <v-btn
                                                    v-on:click="removeUserFromFormData(props.item.email)"
                                                    icon
                                                    small
                                            >
                                                <v-icon>delete</v-icon>
                                            </v-btn>
                                        </td>
                                    </tr>
                                </template>
                                <template v-slot:expand="props">
                                    <AddUserFormTableDetails
                                            v-bind:user="props.item"
                                            v-bind:roles-dictionary="rolesDictionary"
                                    ></AddUserFormTableDetails>
                                </template>
                            </v-data-table>
                        </v-flex>
                    </v-layout>
                </v-container>
                <BuiltInErrorMessage
                        v-bind:error-messages="mapErrorMessages"
                ></BuiltInErrorMessage>
            </v-form>
        </template>
        <template slot="buttons">
            <v-btn
                    v-on:click.native="cancel"
                    v-bind:disabled="fetching"
                    flat
                    round
            >
                {{ $t('user.component.addUserFrom.cancel') }}
            </v-btn>
            <v-btn
                    v-on:click="addUserToFormData"
                    v-bind:disabled="!valid"
                    color="accent"
                    flat
                    round
            >
                {{ $t('user.component.addUserFrom.add') }}
            </v-btn>
            <v-btn
                    v-bind:disabled="!formData.length"
                    v-bind:loading="fetching"
                    form="add-user-form"
                    type="submit"
                    color="success"
                    round
            >
                {{ $t('user.component.addUserFrom.submit') }}
            </v-btn>
        </template>
    </BaseDialogFormContainer>
</template>

<script lang="ts">
    import {Component, Prop, Ref, Vue} from 'vue-property-decorator';
    import BaseDialogFormContainer from '@/modules/common/components/BaseDialogFormContainer.vue';
    import BuiltInErrorMessage from '@/modules/common/components/BuiltInErrorMessage.vue';
    import {NewUserModel, Role} from '@/modules/user/models/user';
    import {UserApiService} from '@/modules/user/services/userApiService';
    import {Organization} from '@/modules/organization/models/organization';
    import RegexUtils from '@/utils/regexUtils';
    import AddUserFormTableDetails from '@/modules/user/components/AddUserFormTableDetails.vue';
    import {UserPermission} from '@/modules/user/store/userState';

    @Component({
        components: {AddUserFormTableDetails, BuiltInErrorMessage, BaseDialogFormContainer}
    })
    export default class AddUserFrom extends Vue {

        public readonly emailPattern = RegexUtils.EMAIL_PATTERN;

        @Ref('form')
        public form!: HTMLElement;

        @Prop()
        public readonly hasPermissions!: (permissions: UserPermission[]) => boolean;

        @Prop()
        public ownedRolesToManage!: Role[];

        @Prop()
        public rolesDictionary!: Role[];

        @Prop()
        public organizationsDictionary!: Organization[];

        public active: boolean = false;

        public fetching: boolean = false;

        public expand: boolean = false;

        public errorMessages: string[] = [];

        public valid: boolean = false;

        public newUser!: NewUserModel;

        public formData: NewUserModel[] = [];

        public open() {
            this.reset();
            this.active = true;
        }

        public addUserToFormData() {
            this.formData.push(this.newUser);
            this.resetNewUser();
        }

        public removeUserFromFormData(email: string) {
            this.formData = this.formData.filter(user => user.email !== email);
        }

        public submit() {
            this.fetching = true;
            UserApiService.createUsers(this.formData)
                .then(response => {
                    this.active = false;
                })
                .catch(error => {
                    this.$logger.error(error);
                    this.errorMessages = ['user.component.addUserFrom.error'];
                })
                .finally(() => this.fetching = false);
        }

        public cancel() {
            this.active = false;
        }

        public checkRoleManaged(roleId: number): boolean {
            return this.ownedRolesToManage ? this.ownedRolesToManage.map(r => r.id).includes(roleId) : false;
        }

        public checkEmailUnique(email: string): boolean | string {
            return !this.formData.map(d => d.email).includes(email)
                || this.$t('user.component.addUserFrom.email.validation.Unique').toString();
        }

        public reset() {
            this.formData = [];
            this.resetNewUser();
        }

        public resetNewUser() {
            // @ts-ignore
            this.form.resetValidation();
            this.newUser = {
                email: '',
                organizationId: undefined,
                roleIds: [],
                ownedRoleToManageIds: []
            };
        }

        get mapErrorMessages() {
            return this.errorMessages ? this.errorMessages.map(message => this.$t(message)) : ''
        }

        public getOrganizationName(id: number): string {
            let result = this.organizationsDictionary.find(org => org.id === id);
            return result ? result.name : '';
        }

        public data() {
            return {
                newUser: {
                    email: '',
                    organizationId: undefined,
                    roleIds: [],
                    ownedRoleToManageIds: []
                }
            }
        }
    }
</script>
