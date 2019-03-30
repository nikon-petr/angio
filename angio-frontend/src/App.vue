<template>
    <v-app :dark="darkThemeEnabled">
        <v-toolbar app>
            <v-toolbar-title class="headline text-uppercase">
                <span>Vuetify</span>
                <span class="font-weight-light">MATERIAL DESIGN</span>
            </v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn
                    flat
                    href="https://github.com/vuetifyjs/vuetify/releases/latest"
                    target="_blank"
            >
                <span class="mr-2">Latest Release</span>
            </v-btn>
        </v-toolbar>

        <v-content>
            <router-view></router-view>
        </v-content>
    </v-app>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import HelloI18n from '@/modules/common/components/HelloI18n.vue';
    import {State} from "vuex-class";

    @Component({
        components: {
            HelloI18n,
        },
    })
    export default class App extends Vue {

        @State(state => state.user.settings.darkThemeEnabled)
        public darkThemeEnabled: boolean | undefined;
    }
</script>
