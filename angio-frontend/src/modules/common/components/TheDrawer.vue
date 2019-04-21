<template>
    <v-navigation-drawer
            v-bind:value="isAuthenticated"
            mini-variant
            floating
            stateless
            style="z-index: 0"
    >
        <v-list>
            <PreAuthorize is-authenticated>

                <v-list-tile to="/analyse/list" ripple>
                    <v-list-tile-action>
                        <v-icon>search</v-icon>
                    </v-list-tile-action>

                    <v-list-tile-content>
                        <v-list-tile-title>Analyses</v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>

                <v-list-tile to="/dashboard" ripple>
                    <v-list-tile-action>
                        <v-icon>dashboard</v-icon>
                    </v-list-tile-action>

                    <v-list-tile-title>Dashboard</v-list-tile-title>
                </v-list-tile>

                <v-list-tile to="/user/list" ripple>
                    <v-list-tile-action>
                        <v-icon>group</v-icon>
                    </v-list-tile-action>

                    <v-list-tile-title>Users</v-list-tile-title>
                </v-list-tile>

                <v-list-tile to="/organization/list" ripple>
                    <v-list-tile-action>
                        <v-icon>domain</v-icon>
                    </v-list-tile-action>

                    <v-list-tile-title>Organizations</v-list-tile-title>
                </v-list-tile>

                <v-list-tile to="/help" ripple>
                    <v-list-tile-action>
                        <v-icon>help_outline</v-icon>
                    </v-list-tile-action>

                    <v-list-tile-title>Help</v-list-tile-title>
                </v-list-tile>

                <v-list-tile to="/about" ripple>
                    <v-list-tile-action>
                        <v-icon>info_outline</v-icon>
                    </v-list-tile-action>

                    <v-list-tile-title>About</v-list-tile-title>
                </v-list-tile>
            </PreAuthorize>
        </v-list>
    </v-navigation-drawer>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import PreAuthorize from '@/modules/common/components/PreAuthorize.vue';
    import {userModule} from '@/modules/user/store/userStore';

    @Component({
        components: {PreAuthorize}
    })
    export default class TheDrawer extends Vue {

        @userModule.Getter
        public readonly isAuthenticated!: boolean;
    }
</script>

<style scoped>

</style>