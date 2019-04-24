<template>
    <v-navigation-drawer
            v-bind:value="isAuthenticated"
            mini-variant
            clipped
            floating
            stateless
            dark
            app
    >
        <v-list>

            <PreAuthorize v-bind:has-permissions="['ANALYSE_VIEW']">

                <v-tooltip right>
                    <template v-slot:activator="{ on }">
                        <v-list-tile to="/analyse/new" ripple v-on="on" active-class="accent--text">
                            <v-list-tile-action>
                                <v-icon>add</v-icon>
                            </v-list-tile-action>

                            <v-list-tile-content>
                                <v-list-tile-title>{{ $t('common.component.theDrawer.link.newAnalyse.name') }}
                                </v-list-tile-title>
                            </v-list-tile-content>
                        </v-list-tile>
                    </template>
                    <span>{{ $t('common.component.theDrawer.link.newAnalyse.tooltip') }}</span>
                </v-tooltip>

            </PreAuthorize>

            <PreAuthorize v-bind:has-permissions="['ANALYSE_CREATE']">

                <v-tooltip right>
                    <template v-slot:activator="{ on }">
                        <v-list-tile to="/analyse" ripple v-on="on" active-class="accent--text">
                            <v-list-tile-action>
                                <v-icon>search</v-icon>
                            </v-list-tile-action>

                            <v-list-tile-content>
                                <v-list-tile-title>{{ $t('common.component.theDrawer.link.search.name') }}
                                </v-list-tile-title>
                            </v-list-tile-content>
                        </v-list-tile>
                    </template>
                    <span>{{ $t('common.component.theDrawer.link.search.tooltip') }}</span>
                </v-tooltip>

            </PreAuthorize>

            <PreAuthorize v-bind:has-any-of-given-permissions="dashboardPermissions">

                <v-tooltip right>
                    <template v-slot:activator="{ on }">
                        <v-list-tile to="/dashboard" ripple v-on="on" active-class="accent--text">
                            <v-list-tile-action>
                                <v-icon>dashboard</v-icon>
                            </v-list-tile-action>

                            <v-list-tile-title>{{ $t('common.component.theDrawer.link.dashboard.name') }}
                            </v-list-tile-title>
                        </v-list-tile>
                    </template>
                    <span>{{ $t('common.component.theDrawer.link.dashboard.tooltip') }}</span>
                </v-tooltip>

            </PreAuthorize>

            <PreAuthorize v-bind:has-permissions="['USER_VIEW']">

                <v-tooltip right>
                    <template v-slot:activator="{ on }">
                        <v-list-tile to="/user" ripple v-on="on" active-class="accent--text">
                            <v-list-tile-action>
                                <v-icon>group</v-icon>
                            </v-list-tile-action>

                            <v-list-tile-title>{{ $t('common.component.theDrawer.link.users.name') }}
                            </v-list-tile-title>
                        </v-list-tile>
                    </template>
                    <span>{{ $t('common.component.theDrawer.link.users.tooltip') }}</span>
                </v-tooltip>

            </PreAuthorize>

            <PreAuthorize v-bind:has-permissions="['ORGANIZATION_VIEW']">

                <v-tooltip right>
                    <template v-slot:activator="{ on }">
                        <v-list-tile to="/organization" ripple v-on="on" active-class="accent--text">
                            <v-list-tile-action>
                                <v-icon>domain</v-icon>
                            </v-list-tile-action>

                            <v-list-tile-title>{{ $t('common.component.theDrawer.link.organizations.name') }}
                            </v-list-tile-title>
                        </v-list-tile>
                    </template>
                    <span>{{ $t('common.component.theDrawer.link.organizations.tooltip') }}</span>
                </v-tooltip>

            </PreAuthorize>

            <PreAuthorize v-bind:has-permissions="['PUSH_NOTIFICATION_SEND']">

                <v-tooltip right>
                    <template v-slot:activator="{ on }">
                        <v-list-tile to="/notification/new" ripple v-on="on" active-class="accent--text">
                            <v-list-tile-action>
                                <v-icon>add_alert</v-icon>
                            </v-list-tile-action>

                            <v-list-tile-title>{{ $t('common.component.theDrawer.link.newNotification.name') }}
                            </v-list-tile-title>
                        </v-list-tile>
                    </template>
                    <span>{{ $t('common.component.theDrawer.link.newNotification.tooltip') }}</span>
                </v-tooltip>

            </PreAuthorize>

            <PreAuthorize is-authenticated>

                <v-tooltip right>
                    <template v-slot:activator="{ on }">
                        <v-list-tile to="/help" ripple v-on="on" active-class="accent--text">
                            <v-list-tile-action>
                                <v-icon>help_outline</v-icon>
                            </v-list-tile-action>

                            <v-list-tile-title>{{ $t('common.component.theDrawer.link.help.name') }}</v-list-tile-title>
                        </v-list-tile>
                    </template>
                    <span>{{ $t('common.component.theDrawer.link.help.tooltip') }}</span>
                </v-tooltip>

                <v-tooltip right>
                    <template v-slot:activator="{ on }">
                        <v-list-tile to="/about" ripple v-on="on" active-class="accent--text">
                            <v-list-tile-action>
                                <v-icon>info_outline</v-icon>
                            </v-list-tile-action>

                            <v-list-tile-title>{{ $t('common.component.theDrawer.link.about.name') }}</v-list-tile-title>
                        </v-list-tile>
                    </template>
                    <span>{{ $t('common.component.theDrawer.link.about.tooltip') }}</span>
                </v-tooltip>

            </PreAuthorize>

        </v-list>
    </v-navigation-drawer>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import PreAuthorize from '@/modules/common/components/PreAuthorize.vue';
    import {userModule} from '@/modules/user/store/userStore';
    import {UserPermission} from "@/modules/user/store/userState";

    @Component({
        components: {PreAuthorize}
    })
    export default class TheDrawer extends Vue {

        public readonly dashboardPermissions: UserPermission[] = [
            UserPermission.USER_VIEW,
            UserPermission.ORGANIZATION_VIEW,
            UserPermission.TOKEN_VIEW,
            UserPermission.ANALYSE_PURGE_DELETED,
            UserPermission.IMAGE_UPLOAD_PURGE_UNUSED
        ];

        @userModule.Getter
        public readonly isAuthenticated!: boolean;
    }
</script>
