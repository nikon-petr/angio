<template>
    <BaseDialogFormContainer
            v-bind:open="active"
            v-bind:title="$t('organization.component.createOrganizationForm.title')"
    >
        <template slot="form">
            <v-form
                    ref="form"
                    v-model="valid"
                    v-on:submit.prevent="submit"
                    id="create-organization-form"
            >
                <v-text-field
                        v-model="name"
                        v-bind:label="$t('organization.component.createOrganizationForm.name.field')"
                        v-bind:rules="[v => !!v || $t('organization.component.createOrganizationForm.name.validation.NotEmpty')]"
                        v-bind:disabled="fetching"
                        data-test-id="name__input"
                        persistent-hint
                        type="text"
                        name="name"
                        prepend-inner-icon="domain"
                        browser-autocomplete="off"
                        maxlength="175"
                        outline
                        required
                ></v-text-field>
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
                {{ $t('organization.component.createOrganizationForm.cancel') }}
            </v-btn>
            <v-btn
                    v-on:click.native="submit"
                    v-bind:disabled="!valid"
                    v-bind:loading="fetching"
                    form="role-editor-form"
                    type="submit"
                    color="success"
                    round
            >
                {{ $t('organization.component.createOrganizationForm.submit') }}
            </v-btn>
        </template>
    </BaseDialogFormContainer>
</template>

<script lang="ts">
    import {Component, Emit, Ref, Vue} from 'vue-property-decorator';
    import BaseDialogFormContainer from '@/modules/common/components/BaseDialogFormContainer.vue';
    import BuiltInErrorMessage from '@/modules/common/components/BuiltInErrorMessage.vue';
    import {OrganizationApiService} from '@/modules/organization/services/organizationApiService';

    @Component({
        components: {BuiltInErrorMessage, BaseDialogFormContainer}
    })
    export default class CreateOrganizationForm extends Vue {

        @Ref('form')
        public form!: HTMLElement;

        public name: string = "";

        public active: boolean = false;

        public valid: boolean = false;

        public fetching: boolean = false;

        public errorMessages: string[] = [];

        public open() {
            this.reset();
            this.active = true;
        }

        public submit() {
            this.fetching = true;

            OrganizationApiService
                .createOrganization(this.name)
                .then(response => {
                    this.active = false;
                    this.onNewOrganizationCreated();
                })
                .catch(error => {
                    this.$logger.error(error);
                    this.errorMessages = ['organization.component.createOrganizationForm.error'];
                })
                .finally(() => this.fetching = false);
        }

        public cancel() {
            this.active = false;
        }

        public reset() {
            // @ts-ignore
            this.form.resetValidation();
            this.errorMessages = [];
            this.name = "";
        }

        get mapErrorMessages() {
            return this.errorMessages.map(message => this.$t(message))
        }

        public data() {
            return {
                name: "",
                errorMessages: []
            }
        }

        @Emit('new-organization-created')
        public onNewOrganizationCreated() {

        }
    }
</script>
