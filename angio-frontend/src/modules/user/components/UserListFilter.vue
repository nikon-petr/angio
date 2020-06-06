<template>
    <div>
        <v-flex xs12 px-0 pb-0>
            <v-text-field
                    v-model="searchSynced"
                    v-bind:label="$t('user.component.userListFilter.field.search.label')"
                    v-bind:loading="fetching"
                    type="search"
                    browser-autocomplete="off"
                    clearable
                    hide-details
                    solo
            >
                <template v-slot:append>
                    <v-tooltip
                            left
                            open-delay="600"
                    >
                        <template v-slot:activator="{ on: tooltip }">
                            <v-btn
                                    v-on="{ ...tooltip, click: submitSearch }"
                                    icon
                            >
                                <v-icon>search</v-icon>
                            </v-btn>
                        </template>
                        <span>{{ $t('user.component.userListFilter.field.search.tooltip') }}</span>
                    </v-tooltip>
                </template>
            </v-text-field>
        </v-flex>
        <v-flex xs12 px-0>
            <v-card elevation="2">
                <v-container fluid>
                    <v-layout
                            row
                            wrap
                            grid-list-md
                            align-content-space-around
                    >
                        <v-flex xl4 lg6 md6 sm12 xs12>
                            <v-select
                                    v-model="organizationIdSynced"
                                    v-bind:items="organizationsDictionary"
                                    v-bind:label="$t('user.component.userListFilter.field.organization.label')"
                                    v-bind:return-object="false"
                                    v-bind:loading="organizationsFetch"
                                    item-value="id"
                                    item-text="name"
                                    hide-details
                                    clearable
                                    outline
                            ></v-select>
                        </v-flex>
                        <v-flex xl4 lg6 md6 sm12 xs12>
                            <v-autocomplete
                                    v-model="roleIdsSynced"
                                    v-bind:items="rolesDictionary"
                                    v-bind:label="$t('user.component.userListFilter.field.role.label')"
                                    v-bind:return-object="false"
                                    item-value="id"
                                    item-text="description"
                                    hide-details
                                    clearable
                                    multiple
                                    outline
                            ></v-autocomplete>
                        </v-flex>
                        <v-flex xl4 lg6 md6 sm12 xs12>
                            <v-autocomplete
                                    v-model="ownedRoleIdsSynced"
                                    v-bind:items="rolesDictionary"
                                    v-bind:label="$t('user.component.userListFilter.field.ownedRole.label')"
                                    v-bind:return-object="false"
                                    item-value="id"
                                    item-text="description"
                                    hide-details
                                    clearable
                                    multiple
                                    outline
                            ></v-autocomplete>
                        </v-flex>
                        <v-flex xs3>
                            <v-checkbox
                                    v-model="enabledSynced"
                                    v-bind:label="$t('user.component.userListFilter.field.enabled.label')"
                                    v-bind:true-value="true"
                                    v-bind:false-value="false"
                                    v-bind:indeterminate="enabled === undefined"
                                    class="mt-0"
                                    hide-details
                            ></v-checkbox>
                        </v-flex>
                        <v-flex xs3>
                            <v-checkbox
                                    v-model="lockedSynced"
                                    v-bind:label="$t('user.component.userListFilter.field.locked.label')"
                                    v-bind:true-value="true"
                                    v-bind:false-value="false"
                                    v-bind:indeterminate="locked === undefined"
                                    class="mt-0"
                                    hide-details
                            ></v-checkbox>
                        </v-flex>
                    </v-layout>
                </v-container>
            </v-card>
        </v-flex>
    </div>
</template>

<script lang="ts">
    import {Role} from '@/modules/role/models/role';
    import {Component, Vue, Prop, Emit, PropSync} from 'vue-property-decorator';
    import {Organization} from '@/modules/organization/models/organization';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';

    @Component({})
    export default class UserListFilter extends Vue {

        @Prop()
        public readonly search!: string;

        @PropSync('search')
        public searchSynced!: string;

        @Prop()
        public readonly enabled!: boolean;

        @PropSync('enabled')
        public enabledSynced!: boolean;

        @Prop()
        public readonly locked!: boolean;

        @PropSync('locked')
        public readonly lockedSynced!: boolean;

        @Prop()
        public readonly organizationId!: number;

        @PropSync('organizationId')
        public organizationIdSynced!: number;

        @Prop()
        public readonly roleIds!: number[];

        @PropSync('roleIds')
        public roleIdsSynced!: number[];

        @Prop()
        public readonly ownedRoleIds!: number[];

        @PropSync('ownedRoleIds')
        public ownedRoleIdsSynced!: number[];

        @Prop()
        public readonly fetching!: boolean;

        @Prop()
        public readonly organizationsDictionary!: Organization[];

        @Prop()
        public readonly rolesDictionary!: Role[];

        @Prop()
        public readonly organizationsFetch!: boolean;

        @Emit(CommonEvent.SUBMIT)
        public submitSearch() {}
    }
</script>
