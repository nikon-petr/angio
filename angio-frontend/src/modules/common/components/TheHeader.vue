<template>
    <v-toolbar app clipped-left>

        <v-img
                v-on:click="$router.push('/')"
                v-bind:src="require('@/assets/logo.png')"
                style="cursor: pointer"
                max-width="30"
                class="mr-2"
                alt="Angio Logo"
        ></v-img>

        <v-toolbar-title
                v-on:click="$router.push('/')"
                style="cursor: pointer"
        >
            {{ $t('common.appName') }}
        </v-toolbar-title>

        <v-spacer></v-spacer>

        <PreAuthorize is-authenticated>

            <UserMenuButton></UserMenuButton>

            <template slot="not-authorized">

                <v-btn to="/register" active-class flat icon>
                    <v-icon>person_add</v-icon>
                </v-btn>

                <v-btn to="/login" active-class flat icon>
                    <v-icon>exit_to_app</v-icon>
                </v-btn>

            </template>

        </PreAuthorize>

    </v-toolbar>
</template>

<script lang="ts">
    import {Component, Emit, Prop, Vue} from 'vue-property-decorator';
    import UserMenuButton from '@/modules/user/components/UserMenuButton.vue';
    import PreAuthorize from "@/modules/common/components/PreAuthorize.vue";

    @Component({
        components: {PreAuthorize, UserMenuButton},
    })
    export default class TheHeader extends Vue {

        @Prop()
        public readonly isAuthenticated!: boolean;

        @Emit()
        public logout() {
        }
    }
</script>
