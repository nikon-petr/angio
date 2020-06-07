<template>
    <div>
        <v-flex xs12 pa-0 ref="menuActivator">
            <v-text-field
                    v-model.trim="search"
                    v-bind:label="$t('analyse.component.analyseListFilter.search')"
                    v-bind:loading="fetching"
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
                            origin="center center"
                            transition="scale-transition"
                            nudge-right="104"
                            nudge-bottom="119"
                            offset-y
                            offset-x
                            bottom
                            left
                    >
                        <template v-slot:activator="{ on: menu }">
                            <v-tooltip
                                    left
                                    open-delay="600"
                            >
                                <template v-slot:activator="{ on: tooltip }">
                                    <v-btn icon
                                           v-on="{ ...tooltip, ...menu }"
                                    >
                                        <v-icon>filter_list</v-icon>
                                    </v-btn>
                                </template>
                                <span>{{ $t('analyse.component.analyseListFilter.field.search.button.advancedSearch.tooltip') }}</span>
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

                                    <v-flex xl3 lg6 md6 sm12 xs12 align-self-center>
                                        <v-switch
                                                v-model="isPeriod"
                                                v-bind:label="$t('analyse.component.analyseListFilter.period')"
                                                class="mt-0"
                                                hide-details
                                        ></v-switch>
                                    </v-flex>

                                    <v-flex xl3 lg4 md5 sm12 xs12>
                                        <v-autocomplete
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
                                                    {{ $t(statuses.find(status => status.value === data.item.value).text) }}
                                                    <v-icon
                                                            v-on:click="data.parent.selectItem(data.item)"
                                                            small
                                                    >
                                                        close
                                                    </v-icon>
                                                </v-chip>
                                            </template>
                                            <template v-slot:no-data>
                                                <v-list-tile>
                                                    <v-list-tile-content>
                                                        <v-list-tile-title>
                                                            {{ $t('analyse.component.analyseListFilter.statusWithoutMatches') }}
                                                        </v-list-tile-title>
                                                    </v-list-tile-content>
                                                </v-list-tile>
                                            </template>
                                        </v-autocomplete>
                                    </v-flex>

                                    <v-flex xl3 lg8 md7 sm12 xs12 align-self-center>
                                        <v-checkbox
                                                v-model="analyseFilterModel.isStarred"
                                                v-bind:label="$t('analyse.component.analyseListFilter.starred')"
                                                class="mt-0"
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
            <AnalyseListFilterChipsList
                    v-if="showFilterLabels()"
                    v-bind:search.sync="analyseFilterModel.search"
                    v-bind:start-date.sync="analyseFilterModel.startDate"
                    v-bind:end-date.sync="analyseFilterModel.endDate"
                    v-bind:single-date.sync="analyseFilterModel.singleDate"
                    v-bind:statuses.sync="analyseFilterModel.statuses"
                    v-bind:isStarred.sync="analyseFilterModel.isStarred"
            ></AnalyseListFilterChipsList>
        </v-expand-transition>
    </div>
</template>

<script lang="ts">
    import {Component, Emit, Prop, Vue, Watch} from 'vue-property-decorator';
    import {AnalyseFilterModel, AnalyseStatusType} from '@/modules/analyse/models/analyse';
    import PeriodDatePicker from '@/modules/common/components/PeriodDatePicker.vue';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';
    import AnalyseListFilterChipsList from '@/modules/analyse/components/AnalyseListFilterChipsList.vue';
    import {Dictionary} from 'vue-router/types/router';

    @Component({
        components: {AnalyseListFilterChipsList, PeriodDatePicker},
    })
    export default class AnalyseListFilter extends Vue {

        $refs!: {
            menuActivator: HTMLFormElement
        };

        @Prop()
        public readonly locale!: string;

        @Prop()
        public readonly fetching!: boolean;

        public statuses = [
            {value: AnalyseStatusType.CREATED, text: 'analyse.component.analyseListFilter.status.CREATED'},
            {value: AnalyseStatusType.SUCCESS, text: 'analyse.component.analyseListFilter.status.SUCCESS'},
            {value: AnalyseStatusType.IN_PROGRESS, text: 'analyse.component.analyseListFilter.status.IN_PROGRESS'},
            {value: AnalyseStatusType.FAILED, text: 'analyse.component.analyseListFilter.status.FAILED'}
        ];

        public isPeriod: boolean = true;

        public menuOpen: boolean = false;

        public analyseFilterModel!: AnalyseFilterModel;

        // initiate analyseFilterModel in date hook because $route inject after vue instance created
        public data() {
            return {
                analyseFilterModel: {
                    search: this.$route.query && this.$route.query.search ? this.$route.query.search as string : undefined,
                    statuses: this.$route.query && this.$route.query.statuses ?
                        (Array.isArray(this.$route.query.statuses) ? this.$route.query.statuses as AnalyseStatusType[] : [this.$route.query.statuses]) : [],
                    startDate: this.$route.query && this.$route.query.startDate ? this.$moment(this.$route.query.startDate, 'YYYY-MM-DD').toDate() : undefined,
                    endDate: this.$route.query && this.$route.query.endDate ? this.$moment(this.$route.query.endDate, 'YYYY-MM-DD').toDate() : undefined,
                    singleDate: this.$route.query && this.$route.query.singleDate ? this.$moment(this.$route.query.singleDate, 'YYYY-MM-DD').toDate() : undefined,
                    isStarred: this.$route.query && this.$route.query.isStarred ? this.$route.query.isStarred === 'true' : undefined
                }
            }
        }

        set search(value: string | undefined) {
            this.analyseFilterModel.search = value ? value : undefined;
        }

        get search(): string | undefined {
            return this.analyseFilterModel.search;
        }

        public menuWidth(): number | 'auto' {
            // @ ts-ignore
            return this.$refs.menuActivator && this.$refs.menuActivator.clientWidth ? this.$refs.menuActivator.clientWidth : 'auto';
        }

        public showFilterLabels() {
            return this.analyseFilterModel.search
                    || (this.analyseFilterModel.statuses && this.analyseFilterModel.statuses.length > 0)
                    || (this.analyseFilterModel.startDate && this.analyseFilterModel.endDate)
                    || this.analyseFilterModel.singleDate
                    || this.analyseFilterModel.isStarred;
        }

        @Watch('analyseFilterModel', {deep: true, immediate: true})
        public onAnalyseFilterChange(newVal: AnalyseFilterModel, oldVal: AnalyseFilterModel) {
            const filterQuery: Dictionary<string> = {...newVal} as Dictionary<string>;

            if (newVal.startDate) {
                filterQuery.startDate = this.$moment(newVal.startDate).format('YYYY-MM-DD');
            }

            if (newVal.endDate) {
                filterQuery.endDate = this.$moment(newVal.endDate).format('YYYY-MM-DD');
            }

            if (newVal.singleDate) {
                filterQuery.singleDate = this.$moment(newVal.singleDate).format('YYYY-MM-DD');
            }

            this.$router.replace({
                path: this.$route.path,
                query: {...this.$route.query, ...filterQuery}
            });

            this.submit(newVal);
        }

        @Emit(CommonEvent.SEND_FORM)
        public submit(filter: AnalyseFilterModel) {
            return filter;
        }
    }
</script>
