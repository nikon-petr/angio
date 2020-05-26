<template>
    <div>
        <v-flex xs12 px-0 pb-0>
            <v-text-field
                    v-on:input="searchUpdate"
                    v-bind:value="search"
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
                            justify-space-between
                            align-content-space-around
                    >
                        <v-flex xl3 lg6 md6 sm12 xs6>
                            <v-select
                                    v-on:change="organizationIdUpdate"
                                    v-bind:value="organizationId"
                                    v-bind:items="organizationsDictionary"
                                    v-bind:item-text="getOrganizationName"
                                    v-bind:item-value="getOrganizationId"
                                    v-bind:label="$t('user.component.userListFilter.field.organization.label')"
                                    v-bind:return-object="false"
                                    v-bind:loading="organizationsFetch"
                                    clearable
                                    hide-details
                                    outline
                            ></v-select>
                        </v-flex>
                        <v-flex xl3 lg6 md6 sm6 xs6 align-self-center>
                            <v-checkbox
                                    v-on:change="enabledUpdate"
                                    v-bind:value="enabled"
                                    v-bind:label="$t('user.component.userListFilter.field.enabled.label')"
                                    v-bind:true-value="true"
                                    v-bind:false-value="false"
                                    v-bind:indeterminate="enabled === undefined"
                                    class="mt-0"
                                    hide-details
                            ></v-checkbox>
                        </v-flex>
                        <v-flex xl3 lg6 md6 sm6 xs6 align-self-center>
                            <v-checkbox
                                    v-on:change="lockedUpdate"
                                    v-bind:value="locked"
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
    import {Component, Vue, Prop, Emit} from 'vue-property-decorator';
    import {Organization} from '@/modules/organization/models/organization';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';

    @Component({})
    export default class UserListFilter extends Vue {

        @Prop()
        public search!: string;

        @Prop()
        public enabled!: boolean;

        @Prop()
        public locked!: boolean;

        @Prop()
        public organizationId!: number;

        @Prop()
        public readonly fetching!: boolean;

        @Prop()
        public readonly organizationsDictionary!: Organization[];

        @Prop()
        public readonly organizationsFetch!: boolean;

        @Emit(CommonEvent.SUBMIT)
        public submitSearch() {}

        @Emit('update:search')
        public searchUpdate(value: string): string {
            return value;
        }

        @Emit('update:enabled')
        public enabledUpdate(value: boolean): boolean {
            return value;
        }

        @Emit('update:locked')
        public lockedUpdate(value: boolean): boolean {
            return value;
        }

        @Emit('update:organizationId')
        public organizationIdUpdate(value: number): number {
            return value;
        }

        public getOrganizationName(organization: Organization): string {
            return organization.name
        }

        public getOrganizationId(organization: Organization): number | undefined {
            return organization.id
        }
    }
</script>
