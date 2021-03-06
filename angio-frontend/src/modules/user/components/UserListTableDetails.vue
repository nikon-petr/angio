<template>
    <v-card flat>
        <v-card-text class="px-4">
            <v-layout row wrap>
                <v-flex xs12>
                    <v-layout
                            column
                            justify-space-between
                            fill-height
                            pa-0
                            ma-0
                    >
                        <div class="subheading">
                            <div>
                                <span class="font-weight-medium text--secondary">
                                    {{ $t('user.component.userListTableDetails.field.fullName') }}
                                </span>{{ user.fullName | fullName() }}
                            </div>
                            <div>
                                <span class="font-weight-medium text--secondary">
                                    {{ $t('user.component.userListTableDetails.field.email') }}
                                </span>{{ user.email }}
                            </div>
                            <div>
                                <span class="font-weight-medium text--secondary">
                                    {{ $t('user.component.userListTableDetails.field.enabled') }}
                                </span>{{ user.enabled | formatBoolean() }}
                            </div>
                            <div>
                                <span class="font-weight-medium text--secondary">
                                    {{ $t('user.component.userListTableDetails.field.locked') }}
                                </span>{{ user.locked | formatBoolean() }}
                            </div>
                            <div>
                                <span class="font-weight-medium text--secondary">
                                    {{ $tc('user.component.userListTableDetails.field.roles', user.roles.length) }}
                                </span>
                                <v-chip
                                        v-for="role in user.roles"
                                        v-bind:key="role.id"
                                        small
                                >
                                    {{ role.description }}
                                </v-chip>
                            </div>
                            <div>
                                <span class="font-weight-medium text--secondary">
                                    {{ $tc('user.component.userListTableDetails.field.ownedRolesToManage', user.ownedRolesToManage.length) }}
                                </span>
                                <v-chip
                                        v-for="role in user.ownedRolesToManage"
                                        v-bind:key="role.id"
                                        small
                                >
                                    {{ role.description }}
                                </v-chip>
                            </div>
                        </div>
                    </v-layout>
                </v-flex>
                <v-flex xs12 v-if="showEditorButtons">
                    <v-divider class="pb-1"></v-divider>
                    <LockButton
                            v-bind:lock="lockUser"
                            v-bind:locked="user.locked"
                            v-bind:id="user.id"
                            object="user.component.userListTableDetails.object"
                    ></LockButton>
                    <v-btn
                            v-on:click="openRoleEditor"
                            class="ma-0"
                            color="accent"
                            ripple
                            round
                            flat
                    >
                        {{ $t('user.component.userListTableDetails.button.roles.name') }}
                    </v-btn>
                    <v-btn
                            v-on:click="openOwnedRoleEditor"
                            class="ma-0"
                            color="accent"
                            ripple
                            round
                            flat
                    >
                        {{ $t('user.component.userListTableDetails.button.ownedRoles.name') }}
                    </v-btn>
                </v-flex>
            </v-layout>
        </v-card-text>
    </v-card>
</template>

<script lang="ts">
    import {Component, Vue, Prop, Emit} from 'vue-property-decorator';
    import {UserDetailsModel} from '@/modules/user/models/user';
    import LockButton from '@/modules/common/components/LockButton.vue';
    import RoleEditorForm from '@/modules/user/components/RoleEditorForm.vue';

    @Component({
        components: {RoleEditorForm, LockButton}
    })
    export default class UserListTableDetails extends Vue {

        @Prop()
        public readonly showEditorButtons!: boolean;

        @Prop()
        public readonly lockUser!: (id: string, locked: boolean) => Promise<void>;

        @Prop()
        public readonly user!: UserDetailsModel;

        @Emit()
        public openRoleEditor() {
            return this.user;
        }

        @Emit()
        public openOwnedRoleEditor() {
            return this.user;
        }
    }
</script>
