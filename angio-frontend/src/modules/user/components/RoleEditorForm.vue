<template>
    <BaseDialogFormContainer
            v-bind:open="active"
            v-bind:title="$t('user.component.roleEditorForm.title')"
    >
        <template slot="form">
            <v-form
                    ref="form"
                    v-model="valid"
                    v-on:submit.prevent="submit"
                    id="role-editor-form"
            >
                <v-combobox
                        v-model="editedRoleList"
                        v-bind:disabled="fetching"
                        v-bind:items="rolesDictionary"
                        v-bind:item-text="(item) => item.description"
                        v-bind:item-value="(item) => item.id"
                        v-bind:item-disabled="(item) => !checkRoleManaged(item.id)"
                        v-bind:rules="[v => v.length > 0 || $t('user.component.roleEditorForm.validation.Empty'), validateChanges]"
                        multiple
                        chips
                ></v-combobox>
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
                {{ $t('user.component.roleEditorForm.cancel') }}
            </v-btn>
            <v-btn
                    v-bind:disabled="!valid"
                    v-bind:loading="fetching"
                    form="role-editor-form"
                    type="submit"
                    color="success"
                    round
            >
                {{ $t('user.component.roleEditorForm.submit') }}
            </v-btn>
        </template>
    </BaseDialogFormContainer>
</template>

<script lang="ts">
    import {Component, Prop, Ref, Vue} from 'vue-property-decorator';
    import {cloneDeep} from 'lodash';
    import {Role, UserDetailsModel} from '@/modules/user/models/user';
    import {UserApiService} from '@/modules/user/services/userApiService';
    import {State} from 'vuex-class';
    import BaseDialogFormContainer from '@/modules/common/components/BaseDialogFormContainer.vue';
    import BuiltInErrorMessage from '@/modules/common/components/BuiltInErrorMessage.vue';

    @Component({
        components: {BuiltInErrorMessage, BaseDialogFormContainer}
    })
    export default class RoleEditorForm extends Vue {

        @Ref('form')
        public form!: HTMLElement;

        @State((state) => state.user.info.id)
        public meId!: string;

        @Prop()
        public rolesDictionary!: Role[];

        @Prop()
        public ownedRolesToManage!: Role[];

        public active: boolean = false;

        public fetching: boolean = false;

        public errorMessages: string[] = [];

        public userId!: string;

        public roles!: Role[];

        public valid: boolean = false;

        public editedRoleList!: Role[];

        public open(user: UserDetailsModel) {
            this.reset();
            this.roles = cloneDeep(user.roles);
            this.editedRoleList = cloneDeep(user.roles);
            this.active = true;
        }

        public submit() {
            this.fetching = true;
            UserApiService.changeRoles(this.userId, this.editedRoleList.map(r => r.id))
                .then(response => {
                    this.active = false;
                })
                .catch(error => {
                    this.$logger.error(error);
                    this.errorMessages = ['user.component.roleEditorForm.error'];
                })
                .finally(() => this.fetching = false);

        }

        public cancel() {
            this.active = false;
        }

        public checkRoleManaged(roleId: number): boolean {
            return this.ownedRolesToManage ? this.ownedRolesToManage.map(r => r.id).includes(roleId) : false;
        }

        public validateChanges(v: Role[]) {
            return (!v.every(r => this.roles.includes(r)) && v.length != this.roles.length)
                || this.$t('user.component.roleEditorForm.validation.NoChanges');
        }

        public reset() {
            this.editedRoleList = cloneDeep(this.roles);
            // @ts-ignore
            this.form.resetValidation();
        }

        get mapErrorMessages() {
            return this.errorMessages.map(message => this.$t(message))
        }

        public data() {
            return {
                userId: undefined,
                editedRoleList: undefined,
                roles: undefined
            }
        }
    }
</script>
