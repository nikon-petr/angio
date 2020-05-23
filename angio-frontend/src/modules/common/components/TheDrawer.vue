<template>
    <v-navigation-drawer
            v-bind:value="isAuthenticated"
            mini-variant
            clipped
            floating
            stateless
            app
    >
        <v-list>

            <PreAuthorize v-bind:has-permissions="['ANALYSE_VIEW']">

                <v-tooltip right>
                    <template v-slot:activator="{ on }">
                        <v-list-tile v-on="on" v-bind:active-class="ACTIVE_CLASS" to="/analyse/new" ripple>
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
                        <v-list-tile
                                v-on="on"
                                v-bind:class="$route.name === 'analyse-list' && 'accent--text'"
                                to="/analyse"
                                ripple
                                active-class=""
                        >
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
                        <v-list-tile v-on="on" to="/dashboard" active-class="accent--text" ripple>
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
                        <v-list-tile v-on="on" v-bind:active-class="ACTIVE_CLASS" to="/user" ripple>
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
                        <v-list-tile v-on="on" v-bind:active-class="ACTIVE_CLASS" to="/organization" ripple>
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

            <PreAuthorize v-bind:has-permissions="['ROLE_EDIT']">

                <v-tooltip right>
                    <template v-slot:activator="{ on }">
                        <v-list-tile v-on="on" v-bind:active-class="ACTIVE_CLASS" to="/role" ripple>
                            <v-list-tile-action>
                                <v-icon>security</v-icon>
                            </v-list-tile-action>

                            <v-list-tile-title>{{ $t('common.component.theDrawer.link.roles.name') }}
                            </v-list-tile-title>
                        </v-list-tile>
                    </template>
                    <span>{{ $t('common.component.theDrawer.link.roles.tooltip') }}</span>
                </v-tooltip>

            </PreAuthorize>

            <PreAuthorize v-bind:has-permissions="['PUSH_NOTIFICATION_SEND']">

                <v-tooltip right>
                    <template v-slot:activator="{ on }">
                        <v-list-tile v-on="on" v-bind:active-class="ACTIVE_CLASS" to="/notification/new" ripple>
                            <v-list-tile-action>
                                <v-icon>add_alert</v-icon>
                            </v-list-tile-action>

                            <v-list-tile-title>{{ $t('common.component.theDrawer.link.newNotification.name') }}
                            </v-list-tile-title>
                        </v-list-tile>
                    </template>
                    <span>{{ $t('common.component.theDrawer.link.newNotification.tooltip') }}</span>
                </v-tooltip>

                <v-tooltip right>
                    <template v-slot:activator="{ on }">
                        <v-list-tile v-on="on" v-bind:active-class="ACTIVE_CLASS" to="/about" ripple>
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
    import {UserGetter} from '@/modules/user/store/userStore';
    import {UserPermission} from '@/modules/user/store/userState';
    import {Getter} from 'vuex-class';

    @Component({
        components: {PreAuthorize}
    })
    export default class TheDrawer extends Vue {

        public readonly ACTIVE_CLASS = 'accent--text';

        public readonly dashboardPermissions: UserPermission[] = [
            UserPermission.USER_VIEW,
            UserPermission.ORGANIZATION_VIEW,
            UserPermission.TOKEN_VIEW,
            UserPermission.ANALYSE_PURGE_DELETED,
            UserPermission.IMAGE_UPLOAD_PURGE_UNUSED
        ];

        @Getter(UserGetter.IS_AUTHENTICATED)
        public readonly isAuthenticated!: boolean;
    }
</script>
