<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-card>
        <v-card-text class="pa-0">
            <v-container fluid class="pb-0">
                <v-layout row wrap justify-space-between align-content-space-around>
                    <v-flex xs12>
                        <v-text-field
                                v-model="analyseFilterModel.search"
                                v-bind:label="$t('analyse.component.analyseListFilter.search')"
                                v-bind:append-outer-icon="extended ? 'expand_less' : 'expand_more'"
                                v-on:click:append-outer="extended = !extended"
                                prepend-inner-icon="search"
                                clearable
                                outline
                        ></v-text-field>
                    </v-flex>
                </v-layout>
                <v-expand-transition>
                    <v-layout
                            v-show="extended"
                            row
                            wrap
                            grid-list-md
                            justify-space-between
                            align-content-space-around
                    >
                        <v-flex xl8 lg8 md8 sm12 xs12>
                            <v-combobox
                                    v-model="analyseFilterModel.statuses"
                                    v-bind:items="statuses"
                                    v-bind:item-text="(item) => $t(item.text)"
                                    v-bind:label="$t('analyse.component.analyseListFilter.statusField')"
                                    v-bind:return-object="false"
                                    multiple
                                    clearable
                                    small-chips
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
                                        {{ $t(statuses.find(status => status.value === data.item).text) }}
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
                                                {{ $t('analyse.component.analyseListFilter.statusWithoutMatches') }}
                                            </v-list-tile-title>
                                        </v-list-tile-content>
                                    </v-list-tile>
                                </template>
                            </v-combobox>
                        </v-flex>
                        <v-flex xl3 lg3 md3 sm4 xs4>
                            <v-checkbox
                                    v-model="analyseFilterModel.isStarred"
                                    v-bind:label="$t('analyse.component.analyseListFilter.starred')"
                            ></v-checkbox>
                        </v-flex>

                        <v-flex xl3 lg3 md3 sm4 xs4>
                            <v-checkbox
                                    v-model="isPeriod"
                                    v-bind:label="$t('analyse.component.analyseListFilter.period')"
                            ></v-checkbox>
                        </v-flex>

                        <v-flex xl8 lg8 md8 sm12 xs12>
                            <PeriodDatePicker
                                    v-bind:locale="locale"
                                    v-bind:isPeriod="isPeriod"
                                    v-on:send-period="onPeriodDataChanged"
                            ></PeriodDatePicker>
                        </v-flex>
                    </v-layout>
                </v-expand-transition>
            </v-container>
        </v-card-text>
    </v-card>
</template>

<script lang="ts">
    import {Component, Emit, Prop, Vue} from 'vue-property-decorator';
    import {AnalyseFilterModel, AnalyseStatusType} from '@/modules/analyse/models/analyse';
    import {Locale} from '@/modules/user/store/userState';
    import PeriodDatePicker from '@/modules/common/components/PeriodDatePicker.vue';
    import {PeriodDateModel} from '@/modules/common/models/PeriodDateModel';
    import {AnalyseStatusRepresentation} from '@/modules/analyse/helpers/representation';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';

    @Component({
        components: {PeriodDatePicker},
    })
    export default class AnalyseListFilter extends Vue {

        @Prop()
        public readonly locale!: Locale;

        public statuses: AnalyseStatusRepresentation[] = [
            {value: AnalyseStatusType.CREATED, text: 'analyse.component.analyseListFilter.status.CREATED'},
            {value: AnalyseStatusType.SUCCESS, text: 'analyse.component.analyseListFilter.status.SUCCESS'},
            {value: AnalyseStatusType.IN_PROGRESS, text: 'analyse.component.analyseListFilter.status.IN_PROGRESS'},
            {value: AnalyseStatusType.FAILED, text: 'analyse.component.analyseListFilter.status.FAILED'}
        ];

        public isPeriod: boolean = true;

        public extended: boolean = false;

        public analyseFilterModel: AnalyseFilterModel = {
            search: undefined,
            statuses: [],
            startDate: undefined,
            endDate: undefined,
            date: undefined,
            isStarred: undefined
        };

        public onPeriodDataChanged(periodDateModel: PeriodDateModel) {
            this.analyseFilterModel.date = periodDateModel.date;
            this.analyseFilterModel.startDate = periodDateModel.startDate;
            this.analyseFilterModel.endDate = periodDateModel.endDate;
        }

        @Emit(CommonEvent.SEND_FORM)
        public submit() {
            return this.analyseFilterModel;
        }
    }
</script>
