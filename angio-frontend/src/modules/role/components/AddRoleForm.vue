<template>
    <BaseDialogFormContainer
            v-bind:open="show"
            v-bind:title="$t('role.component.addRoleForm.title')"
            v-bind:max-width="600"
    >
        <template slot="form">
            <v-form
                    ref="form"
                    v-model="valid"
                    v-on:submit.prevent="addRole"
                    id="add-role-form"
            >
                <v-layout row wrap>
                    <v-flex xs12>
                        <v-text-field
                                v-model="newRole.description"
                                v-bind:label="$t('role.component.addRoleForm.field.description')"
                                v-bind:rules="[v => !!v || $t('role.component.addRoleForm.validation.description.NotEmpty')]"
                                v-bind:disabled="fetching"
                                persistent-hint
                                type="text"
                                name="description"
                                browser-autocomplete="off"
                                maxlength="30"
                                outline
                                required
                        ></v-text-field>
                    </v-flex>
                    <v-flex xs12>
                        <v-autocomplete
                                v-model="newRole.permissionIds"
                                v-bind:items="permissionsDictionary"
                                v-bind:item-disabled="isPermissionDisabled"
                                v-bind:label="$t('role.component.addRoleForm.field.permissions')"
                                v-bind:rules="[v => v.length > 0 || $t('role.component.addRoleForm.validation.permissions.NotEmpty')]"
                                v-bind:disabled="fetching"
                                item-text="description"
                                item-value="id"
                                deletable-chips
                                small-chips
                                single-line
                                clearable
                                multiple
                                outline
                                chips
                        ></v-autocomplete>
                    </v-flex>
                </v-layout>
                <BuiltInErrorMessage
                        v-bind:error-messages="errorMessages"
                ></BuiltInErrorMessage>
            </v-form>
        </template>
        <template slot="buttons">
            <v-btn
                    v-on:click="syncedShow = false"
                    v-bind:disabled="fetching"
                    flat
                    round
            >
                {{ $t('common.component.button.cancel') }}
            </v-btn>
            <v-btn
                    v-bind:disabled="!valid"
                    form="add-role-form"
                    type="submit"
                    color="success"
                    round
            >
                {{ $t('common.component.button.save') }}
            </v-btn>
        </template>
    </BaseDialogFormContainer>
</template>

<script lang="ts">
    import BaseDialogFormContainer from '@/modules/common/components/BaseDialogFormContainer.vue';
    import BuiltInErrorMessage from '@/modules/common/components/BuiltInErrorMessage.vue';
    import {Permission, Role, UpdateRoleModel} from '@/modules/role/models/role';
    import {Component, Emit, Prop, PropSync, Ref, Vue, Watch} from 'vue-property-decorator';

    @Component({
        components: {BuiltInErrorMessage, BaseDialogFormContainer}
    })
    export default class AddRoleForm extends Vue {

        @Ref('form')
        public form!: HTMLElement;

        @Prop()
        public rolesDictionary!: Role[];

        @Prop()
        public permissionsDictionary!: Permission[];

        @Prop()
        public ownedPermissions!: Permission[];

        @Prop()
        public fetching!: boolean;

        @Prop()
        public errorMessages!: string[];

        @Prop()
        public show!: boolean;

        @PropSync('show')
        public syncedShow!: boolean;

        @Emit()
        public addRole() {
            return this.newRole;
        }

        public valid: boolean = false;

        public newRole!: UpdateRoleModel;

        @Watch('show')
        onShowChanged(newVal: boolean, oldVal: boolean) {
            if (newVal) {
                this.reset();
            }
        }

        public reset() {
            this.newRole = {
                id: undefined,
                description: '',
                permissionIds: []
            };
            // @ts-ignore
            this.form.resetValidation();
        }


        public isPermissionDisabled(permission: Permission) {
            return this.ownedPermissions.find(p => p.id === permission.id) === undefined;
        }

        public data() {
            return {
                newRole: {
                    id: undefined,
                    description: '',
                    permissionIds: []
                } as UpdateRoleModel
            };
        }
    }
</script>
