<template>
    <v-app :dark="darkThemeEnabled" id="inspire">
        <TheNotification></TheNotification>
        <TheDrawer></TheDrawer>
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
    import {State} from 'vuex-class';
    import TheDrawer from '@/modules/common/components/TheDrawer.vue';
    import TheHeader from '@/modules/common/components/TheHeader.vue';
    import TheContent from '@/modules/common/components/TheContent.vue';
    import {logout, refreshAuth, userModule} from '@/modules/user/store/userStore';
    import TheFooter from '@/modules/common/components/TheFooter.vue';
    import TheNotification from "@/modules/notification/components/TheNotification.vue";

    @Component({
        components: {
            TheNotification,
            TheFooter,
            TheContent,
            TheHeader,
            TheDrawer,
        },
    })
    export default class App extends Vue {

        @State((state) => state.user.settings.darkThemeEnabled)
        public readonly darkThemeEnabled!: boolean;

        @userModule.Getter
        public readonly isAuthenticated!: boolean;

        public logout() {
            logout(this.$store);
        }

        public mounted() {
            refreshAuth(this.$store);
        }
    }
</script>
