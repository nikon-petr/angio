<template>
    <v-app v-bind:dark="darkThemeEnabled">
        <TheDrawer></TheDrawer>
        <TheNotification></TheNotification>
        <TheConfirmDialog
                ref="confirm"
        ></TheConfirmDialog>
        <TheHeader
                v-bind:is-authenticated="isAuthenticated"
                v-on:logout="logout"
        ></TheHeader>
        <TheContent></TheContent>
        <TheFooter></TheFooter>
    </v-app>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import {Action, Getter, State} from 'vuex-class';
    import TheHeader from '@/modules/common/components/TheHeader.vue';
    import TheContent from '@/modules/common/components/TheContent.vue';
    import {UserAction, UserGetter} from '@/modules/user/store/userStore';
    import TheFooter from '@/modules/common/components/TheFooter.vue';
    import TheNotification from '@/modules/notification/components/TheNotification.vue';
    import TheDrawer from '@/modules/common/components/TheDrawer.vue';
    import TheConfirmDialog from "@/modules/common/components/TheConfirmDialog.vue";

    @Component({
        components: {
            TheConfirmDialog,
            TheDrawer,
            TheNotification,
            TheFooter,
            TheContent,
            TheHeader,
        },
    })
    export default class App extends Vue {

        @State((state) => state.user.settings.darkThemeEnabled)
        public readonly darkThemeEnabled!: boolean;

        @Getter(UserGetter.IS_AUTHENTICATED)
        public readonly isAuthenticated!: boolean;

        @Action(UserAction.LOGOUT)
        public readonly logout!: () => Promise<void>;

        @Action(UserAction.REFRESH_AUTH)
        public readonly refreshAuth!: () => Promise<void>;

        $refs!: {
            confirm: HTMLFormElement
        };

        public mounted() {
            this.refreshAuth();
            Vue.prototype.$confirm = this.$refs.confirm.open;
        }
    }
</script>
