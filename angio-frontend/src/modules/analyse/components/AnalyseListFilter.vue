<template>
    <div>
        <v-flex xs12 pa-0 ref="menuActivator">
            <v-text-field
                    v-model="analyseFilterModel.search"
                    v-bind:label="$t('analyse.component.analyseListFilter.search')"
                    type="search"
                    browser-autocomplete="off"
                    clearable
                    hide-details
                    solo
            >
                <template slot="append">
                    <v-menu
                            v-model="menuOpen"
                            v-bind:close-on-content-click="false"
                            v-bind:min-width="menuWidth()"
                            v-bind:max-width="menuWidth()"
                            v-bind:attach="$refs.menuActivator"
                            nudge-right="104"
                            nudge-bottom="104"
                            offset-y
                            offset-x
                            bottom
                            left
                    >
                        <template v-slot:activator="{ on: menu }">
                            <v-tooltip
                                    left
                                    open-delay="600"
                                    color="rgba(0, 0, 0, 0)"
                                    content-class="elevation-0"
                            >
                                <template v-slot:activator="{ on: tooltip }">
                                    <v-btn icon
                                           v-on="{ ...tooltip, ...menu }"
                                    >
                                        <v-icon>filter_list</v-icon>
                                    </v-btn>
                                </template>
                                <span>Расширеный поиск</span>
                            </v-tooltip>
                        </template>
                        <v-card>
                            <v-container fluid>
                                <v-layout
                                        row
                                        wrap
                                        grid-list-md
                                        justify-space-between
                                        align-content-space-around
                                >
                                    <v-flex xl3 lg6 md6 sm12 xs12>
                                        <PeriodDatePicker
                                                v-bind:start-date.sync="analyseFilterModel.startDate"
                                                v-bind:end-date.sync="analyseFilterModel.endDate"
                                                v-bind:single-date.sync="analyseFilterModel.singleDate"
                                                v-bind:locale="locale"
                                                v-bind:isPeriod="isPeriod"
                                        ></PeriodDatePicker>
                                    </v-flex>

                                    <v-flex xl3 lg6 md6 sm12 xs12>
                                        <v-switch
                                                v-model="isPeriod"
                                                v-bind:label="$t('analyse.component.analyseListFilter.period')"
                                                hide-details
                                        ></v-switch>
                                    </v-flex>

                                    <v-flex xl3 lg4 md5 sm12 xs12>
                                        <v-combobox
                                                v-model="analyseFilterModel.statuses"
                                                v-bind:items="statuses"
                                                v-bind:item-text="(item) => $t(item.text)"
                                                v-bind:label="$t('analyse.component.analyseListFilter.statusField')"
                                                v-bind:return-object="false"
                                                multiple
                                                clearable
                                                small-chips
                                                hide-details
                                                outline
                                        >
                                            <template v-slot:selection="data">
                                                <v-chip
                                                        v-bind:key="JSON.stringify(data.item)"
                                                        v-bind:selected="data.selected"
                                                        v-bind:disabled="data.disabled"
                                                        v-on:input="data.parent.selectItem(data.item)"
                                                        class="v-chip--select-multi caption"
                                                        small
                                                >
                                                    {{ $t(statuses.find(status => status.value === data.item).text)
                                                    }}
                                                    <v-icon
                                                            v-on:click="data.parent.selectItem(data.item)"
                                                            small
                                                    >close
                                                    </v-icon>
                                                </v-chip>
                                            </template>
                                            <template v-slot:no-data>
                                                <v-list-tile>
                                                    <v-list-tile-content>
                                                        <v-list-tile-title>
                                                            {{
                                                            $t('analyse.component.analyseListFilter.statusWithoutMatches')
                                                            }}
                                                        </v-list-tile-title>
                                                    </v-list-tile-content>
                                                </v-list-tile>
                                            </template>
                                        </v-combobox>
                                    </v-flex>

                                    <v-flex xl3 lg8 md7 sm12 xs12>
                                        <v-checkbox
                                                v-model="analyseFilterModel.isStarred"
                                                v-bind:label="$t('analyse.component.analyseListFilter.starred')"
                                                hide-details
                                        ></v-checkbox>
                                    </v-flex>
                                </v-layout>
                            </v-container>
                        </v-card>
                    </v-menu>
                </template>
            </v-text-field>
        </v-flex>

        <v-expand-transition appear>
            <v-flex xs12 pt-4 pb-0 px-0
                    v-if="showFilterLabels()">
                <AnalyseListFilterChipsList
                        v-bind:search.sync="analyseFilterModel.search"
                        v-bind:start-date.sync="analyseFilterModel.startDate"
                        v-bind:end-date.sync="analyseFilterModel.endDate"
                        v-bind:single-date.sync="analyseFilterModel.singleDate"
                        v-bind:statuses.sync="analyseFilterModel.statuses"
                        v-bind:isStarred.sync="analyseFilterModel.isStarred"
                ></AnalyseListFilterChipsList>
            </v-flex>
        </v-expand-transition>
    </div>
</template>

<script lang="ts">
    import {Component, Emit, Prop, Vue} from 'vue-property-decorator';
    import {AnalyseFilterModel, AnalyseStatusType} from '@/modules/analyse/models/analyse';
    import PeriodDatePicker from '@/modules/common/components/PeriodDatePicker.vue';
    import {AnalyseStatusRepresentation} from '@/modules/analyse/helpers/representation';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';
    import AnalyseListFilterChipsList from "@/modules/analyse/components/AnalyseListFilterChipsList.vue";

    @Component({
        components: {AnalyseListFilterChipsList, PeriodDatePicker},
    })
    export default class AnalyseListFilter extends Vue {

        @Prop()
        public readonly locale!: string;

        public statuses: AnalyseStatusRepresentation[] = [
            {value: AnalyseStatusType.CREATED, text: 'analyse.component.analyseListFilter.status.CREATED'},
            {value: AnalyseStatusType.SUCCESS, text: 'analyse.component.analyseListFilter.status.SUCCESS'},
            {value: AnalyseStatusType.IN_PROGRESS, text: 'analyse.component.analyseListFilter.status.IN_PROGRESS'},
            {value: AnalyseStatusType.FAILED, text: 'analyse.component.analyseListFilter.status.FAILED'}
        ];

        public isPeriod: boolean = true;

        public menuOpen: boolean = false;

        public analyseFilterModel: AnalyseFilterModel = {
            search: undefined,
            statuses: [],
            startDate: undefined,
            endDate: undefined,
            singleDate: undefined,
            isStarred: undefined
        };

        public menuWidth(): number {
            // @ ts-ignore
            return this.$refs.menuActivator && this.$refs.menuActivator.clientWidth ? this.$refs.menuActivator.clientWidth : 'auto';
        }

        public showFilterLabels() {
            return this.analyseFilterModel.search
                || (this.analyseFilterModel.startDate && this.analyseFilterModel.endDate)
                || this.analyseFilterModel.singleDate
                || this.analyseFilterModel.isStarred;
        }

        @Emit(CommonEvent.SEND_FORM)
        public submit() {
            return this.analyseFilterModel;
        }
    }
</script>
