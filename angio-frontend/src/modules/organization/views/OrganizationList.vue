<template>
    <StackLayout>
        <v-flex xs12>
            <BaseSubheader
                    v-on:append-button-click="onButtonAddClicked"
                    v-bind:value="$t('organization.view.organizationList.title')"
                    v-bind:is-button-enabled="hasPermissions(['ORGANIZATION_CREATE'])"
            ></BaseSubheader>
        </v-flex>

        <v-flex xs12>
            <OrganizationListTable
                    ref="organizationListTable"
            ></OrganizationListTable>
        </v-flex>

        <v-flex xs12>
            <CreateOrganizationForm
                    ref="createOrganizationForm"
                    v-on:new-organization-created="onNewOrganizationCreated"
            ></CreateOrganizationForm>
        </v-flex>
    </StackLayout>
</template>

<script lang="ts">
    import {Component, Ref, Vue} from 'vue-property-decorator';
    import StackLayout from '@/modules/common/components/StackLayout.vue';
    import BaseSubheader from '@/modules/common/components/BaseSubheader.vue';
    import {UserPermission} from '@/modules/user/store/userState';
    import {UserGetter} from '@/modules/user/store/userStore';
    import {Getter} from 'vuex-class';
    import OrganizationListTable from '@/modules/organization/components/OrganizationListTable.vue';
    import CreateOrganizationForm from '@/modules/organization/components/CreateOrganizationForm.vue';

    @Component({
        components: {CreateOrganizationForm, OrganizationListTable, StackLayout, BaseSubheader}
    })
    export default class OrganizationList extends Vue {

        @Ref('createOrganizationForm')
        public createOrganizationForm!: CreateOrganizationForm;

        @Ref('organizationListTable')
        public organizationListTable!: OrganizationListTable;

        @Getter(UserGetter.HAS_PERMISSIONS)
        public readonly hasPermissions!: (permissions: UserPermission[]) => boolean;

        public onButtonAddClicked() {
            // @ts-ignore
            this.createOrganizationForm.open();
        }

        public onNewOrganizationCreated() {
            // @ts-ignore
            this.organizationListTable.update();
        }
    }
</script>
