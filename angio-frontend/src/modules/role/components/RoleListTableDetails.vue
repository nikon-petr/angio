<template>
    <v-card flat>
        <v-card-text class="px-4">
            <v-layout row wrap justify-end>
                <v-flex xs12>
                    <v-layout
                            pa-0
                            ma-0
                    >
                        <div class="subheading">
                            <div>
                                <span class="font-weight-medium text--secondary ml-2">
                                    {{ $t('role.component.roleListTableDetails.permissions') }}
                                </span>
                                <v-chip
                                        v-for="permission in role.permissions"
                                        v-bind:key="permission.id"
                                        small
                                >
                                    {{ permission.description }}
                                </v-chip>
                            </div>
                        </div>
                    </v-layout>
                </v-flex>
                <v-flex v-if="isButtonPanelShow" xs12>
                    <v-divider class="pb-1"></v-divider>
                    <v-btn
                            v-if="showDeleteButton"
                            v-on:click="deleteRole"
                            class="ma-0"
                            color="error"
                            ripple
                            icon
                            flat
                    >
                        <v-icon>delete</v-icon>
                    </v-btn>
                    <v-btn
                            v-if="showEditButton"
                            v-on:click="editRole"
                            class="ma-0"
                            color="accent"
                            ripple
                            round
                            flat
                    >
                        {{ $t('role.component.roleListTableDetails.button.editRole') }}
                    </v-btn>
                </v-flex>
            </v-layout>
        </v-card-text>
    </v-card>
</template>

<script lang="ts">
    import {Role} from '@/modules/role/models/role';
    import {Component, Emit, Prop, Vue} from 'vue-property-decorator';

    @Component({})
    export default class RoleListTableDetails extends Vue {

        @Prop()
        public role!: Role;

        @Prop()
        public showEditButton!: boolean;

        @Prop()
        public showDeleteButton!: boolean;

        @Emit()
        public editRole() {
            return this.role.id;
        }

        @Emit()
        public deleteRole() {
            return this.role.id;
        }

        get isButtonPanelShow() {
            return (this.showDeleteButton || this.showEditButton) && !this.role.systemRole;
        }
    }
</script>
