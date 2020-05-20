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
    import {Component, Vue, Prop, Ref} from 'vue-property-decorator';
    import BaseDialogFormContainer from '@/modules/common/components/BaseDialogFormContainer.vue';
    import BuiltInErrorMessage from '@/modules/common/components/BuiltInErrorMessage.vue';
    import {NewUserModel, Role} from '@/modules/user/models/user';
    import {UserApiService} from '@/modules/user/services/userApiService';
    import {Organization} from '@/modules/organization/models/organization';

    @Component({
        components: {BuiltInErrorMessage, BaseDialogFormContainer}
    })
    export default class AddUserFrom extends Vue {

        @Ref('form')
        public form!: HTMLElement;

        @Prop()
        public ownedRolesToManage!: Role[];

        @Prop()
        public rolesDictionary!: Role[];

        @Prop()
        public organizationsDictionary!: Organization[];

        public active: boolean = false;

        public fetching: boolean = false;

        public errorMessages: string[] = [];

        public valid: boolean = false;

        public formData: NewUserModel[] = [];

        public open() {
            this.reset();
            this.active = true;
        }

        public submit() {
            this.fetching = true;
            UserApiService.createUsers(this.formData)
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

        public reset() {
            // @ts-ignore
            this.form.resetValidation();
        }

        get mapErrorMessages() {
            return this.errorMessages.map(message => this.$t(message))
        }
    }
</script>
