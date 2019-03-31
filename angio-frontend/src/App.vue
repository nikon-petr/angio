<template>
    <v-app :dark="darkThemeEnabled" id="inspire">
        <TheDrawer></TheDrawer>
        <TheHeader></TheHeader>
        <TheContent></TheContent>
    </v-app>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import {State} from "vuex-class";
    import TheDrawer from '@/modules/common/components/TheDrawer.vue';
    import TheHeader from '@/modules/common/components/TheHeader.vue';
    import TheContent from '@/modules/common/components/TheContent.vue';

    @Component({
        components: {
            TheContent,
            TheHeader,
            TheDrawer,
        },
    })
    export default class App extends Vue {

        @State(state => state.user.settings.darkThemeEnabled)
        public darkThemeEnabled: boolean | undefined;
    }
</script>
