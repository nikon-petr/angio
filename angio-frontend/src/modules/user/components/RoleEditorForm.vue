<template>
    <BaseDialogFormContainer
            v-bind:open="active"
            v-bind:title="title"
    >
        <template slot="form">
            <v-form
                    ref="form"
                    v-model="valid"
            >
                <v-combobox
                        v-model="editedRoleList"
                        v-bind:label="$t('user.component.roleEditorForm.combobox.label')"
                        v-bind:disabled="fetching"
                        v-bind:items="rolesDictionary"
                        v-bind:item-text="(item) => item.description"
                        v-bind:item-value="(item) => item.id"
                        v-bind:item-disabled="(item) => !checkRoleManaged(item.id)"
                        v-bind:rules="[validateLength, validateChanges]"
                        deletable-chips
                        small-chips
                        outline
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
                    v-on:click.native="activeSynced = false"
                    v-bind:disabled="fetching"
                    flat
                    round
            >
                {{ $t('common.component.button.cancel') }}
            </v-btn>
            <v-btn
                    v-on:click.prevent="save"
                    v-bind:disabled="!valid"
                    v-bind:loading="fetching"
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
    import {Role} from '@/modules/role/models/role';
    import {isEqual, sortBy} from 'lodash';
    import {Component, Emit, Prop, PropSync, Ref, Vue, Watch} from 'vue-property-decorator';

    @Component({
        components: {BuiltInErrorMessage, BaseDialogFormContainer}
    })
    export default class RoleEditorForm extends Vue {

        @Ref('form')
        public form!: HTMLElement;

        @Prop()
        public readonly errorMessages!: string[];

        @Prop()
        public readonly title!: string;

        @Prop()
        public readonly fetching!: boolean;

        @Prop()
        public readonly rolesDictionary!: Role[];

        @Prop()
        public readonly ownedRolesToManage!: Role[];

        @Prop({default: false})
        public readonly allowEmpty!: boolean;

        @Prop()
        public readonly roles!: Role[];

        @Prop()
        public readonly active!: boolean;

        @PropSync('active')
        public activeSynced!: boolean;

        @Watch('active')
        onShowChanged(newVal: boolean, oldVal: boolean) {
            if (newVal) {
                this.reset();
                this.editedRoleList = [...this.roles];
                this.activeSynced = true;
            }
        }

        @Emit()
        public save() {
            return this.editedRoleList.map(r => r.id);
        }

        public valid: boolean = false;

        public editedRoleList: Role[] = [];

        public checkRoleManaged(roleId: number): boolean {
            return this.ownedRolesToManage ? this.ownedRolesToManage.map(r => r.id).includes(roleId) : false;
        }

        public validateChanges(v: Role[]) {
            return !isEqual(sortBy(v), sortBy(this.roles))
                || this.$t('user.component.roleEditorForm.validation.NoChanges');
        }

        public validateLength(v: Role[]) {
            return v.length > 0 || this.allowEmpty || this.$t('user.component.roleEditorForm.validation.Empty');
        }

        public reset() {
            this.editedRoleList = [...this.roles];
            // @ts-ignore
            this.form.resetValidation();
        }

        get mapErrorMessages() {
            return this.errorMessages.map(message => this.$t(message));
        }
    }
</script>
