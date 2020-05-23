<template>
    <BaseDialogFormContainer
            v-bind:open="show"
            v-bind:title="$t('role.component.editRoleForm.title')"
            v-bind:max-width="600"
    >
        <template slot="form">
            <v-form
                    ref="form"
                    v-model="valid"
                    v-on:submit.prevent="editRole"
                    id="edit-role-form"
            >
                <v-layout row wrap>
                    <v-flex xs12>
                        <v-text-field
                                v-model="updateRole.description"
                                v-bind:label="$t('role.component.editRoleForm.field.description')"
                                v-bind:rules="[v => !!v || $t('role.component.editRoleForm.validation.description.NotEmpty')]"
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
                                v-model="updateRole.permissionIds"
                                v-bind:items="permissionsDictionary"
                                v-bind:label="$t('role.component.editRoleForm.field.permissions')"
                                v-bind:rules="[v => v.length > 0 || $t('role.component.editRoleForm.validation.permissions.NotEmpty')]"
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
                    v-bind:disabled="!valid || !isRoleChanged()"
                    form="edit-role-form"
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
    import {isEqual} from 'lodash';
    import {Component, Emit, Prop, PropSync, Ref, Vue, Watch} from 'vue-property-decorator';

    @Component({
        components: {BuiltInErrorMessage, BaseDialogFormContainer}
    })
    export default class EditRoleForm extends Vue {

        @Ref('form')
        public form!: HTMLElement;

        @Prop()
        public rolesDictionary!: Role[];

        @Prop()
        public permissionsDictionary!: Permission[];

        @Prop()
        public fetching!: boolean;

        @Prop()
        public errorMessages!: string[];

        @Prop()
        public show!: boolean;

        @Prop()
        public defaultRole?: Role;

        @PropSync('show')
        public syncedShow!: boolean;

        @Emit()
        public editRole() {
            return this.updateRole;
        }

        @Watch('show')
        onShowChanged(newVal: boolean, oldVal: boolean) {
            if (newVal) {
                this.reset();
            }
        }

        public valid: boolean = false;

        public updateRole!: UpdateRoleModel;

        public getRoleDefaults(): UpdateRoleModel {
            if (this.defaultRole) {
                return {
                    id: this.defaultRole.id,
                    description: this.defaultRole.description,
                    permissionIds: this.defaultRole.permissions.map(p => p.id)
                };
            } else {
                return {
                    id: undefined,
                    description: '',
                    permissionIds: []
                };
            }
        }

        public isRoleChanged() {
            return !isEqual(this.getRoleDefaults(), this.updateRole);
        }

        public reset() {
            this.updateRole = this.getRoleDefaults();
            // @ts-ignore
            this.form.resetValidation();
        }

        public data() {
            return {
                updateRole: this.getRoleDefaults()
            };
        }
    }
</script>
