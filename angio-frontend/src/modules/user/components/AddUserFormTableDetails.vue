<template>
    <v-card flat>
        <v-card-text class="px-4">
            <v-layout row wrap>
                <v-flex xs12>
                    <v-layout
                            child-flex
                            column
                            justify-space-between
                            fill-height
                            pa-0
                            ma-0
                    >
                        <div class="subheading">
                            <div>
                                <span class="font-weight-medium text--secondary">
                                    {{ $tc('user.component.addUserFormTableDetails.field.roles', user.roleIds.length) }}
                                </span>
                                <v-chip
                                        v-for="roleId in user.roleIds"
                                        v-bind:key="roleId"
                                        small
                                >
                                    {{ getRoleName(roleId) }}
                                </v-chip>
                            </div>
                            <div>
                                <span class="font-weight-medium text--secondary">
                                    {{ $tc('user.component.addUserFormTableDetails.field.ownedRolesToManage', user.ownedRoleToManageIds.length) }}
                                </span>
                                <v-chip
                                        v-for="roleId in user.ownedRoleToManageIds"
                                        v-bind:key="roleId"
                                        small
                                >
                                    {{ getRoleName(roleId) }}
                                </v-chip>
                            </div>
                        </div>
                    </v-layout>
                </v-flex>
            </v-layout>
        </v-card-text>
    </v-card>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import {NewUserModel, Role} from '@/modules/user/models/user';

    @Component({})
    export default class AddUserFormTableDetails extends Vue {

        @Prop()
        public readonly user!: NewUserModel;

        @Prop()
        public rolesDictionary!: Role[];

        public getRoleName(id: number): string {
            let result = this.rolesDictionary.find(role => role.id === id);
            return result ? result.description : '';
        }
    }
</script>
