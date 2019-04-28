<template>
    <StackLayout>
        <v-flex xs12>
            <BaseSubheader
                    v-bind:value="$t('analyse.view.analyseList.subheader')"
            ></BaseSubheader>
        </v-flex>

        <v-flex xs12>
            <AnalyseListFilter
                    v-bind:locale="locale"
                    v-on:send-analyse-filter="sendAnalyseFilter"
            ></AnalyseListFilter>
        </v-flex>

        <v-flex xs12>
            <AnalyseListTable></AnalyseListTable>
        </v-flex>
    </StackLayout>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import BaseSubheader from "@/modules/common/components/BaseSubheader.vue";
    import AnalyseListFilter from "@/modules/analyse/components/AnalyseListFilter.vue";
    import {State} from 'vuex-class';
    import {Locale} from '@/modules/user/store/userState';
    import {AnalyseFilterModel} from '@/modules/analyse/models/analyse';
    import AnalyseListTable from "@/modules/analyse/components/AnalyseListTable.vue";
    import StackLayout from "@/modules/common/components/StackLayout.vue";

    @Component({
        components: {StackLayout, AnalyseListTable, BaseSubheader, AnalyseListFilter},
    })
    export default class AnalyseList extends Vue {

        public analyseListPage = [];

        @State((state) => state.user.settings.locale)
        public readonly locale!: Locale;

        public sendAnalyseFilter(model: AnalyseFilterModel) {
        }
    }
</script>

<style scoped>

</style>
