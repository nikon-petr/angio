<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-card flat>
        <v-card-title>
            <v-layout row wrap>
                <v-flex xs12>
                    <v-layout align-end>
                        <v-flex xs7>
                            <v-text-field
                                    v-model="analyseFilterModel.search"
                                    v-bind:label="$t('analyse.component.analyseListFilter.search')"
                            ></v-text-field>
                        </v-flex>

                        <v-flex xs5 pl-2>
                            <v-combobox
                                    v-model="analyseFilterModel.statuses"
                                    v-bind:items="statuses"
                                    v-bind:item-text="(item) => $t(item.text)"
                                    v-bind:label="$t('analyse.component.analyseListFilter.statusField')"
                                    v-bind:return-object="false"
                                    multiple
                                    clearable
                                    small-chips
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
                                        >close</v-icon>
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
                    </v-layout>
                </v-flex>

                <v-flex xs12>
                    <v-layout align-center>
                        <v-flex xs5>
                            <PeriodDatePicker
                                    v-bind:locale="locale"
                                    v-bind:isPeriod="isPeriod"
                                    v-on:send-period="onPeriodDataChanged"
                            ></PeriodDatePicker>
                        </v-flex>

                        <v-flex xs5 pl-2>
                            <v-checkbox
                                    v-model="isPeriod"
                                    v-bind:label="$t('analyse.component.analyseListFilter.period')"
                            ></v-checkbox>
                        </v-flex>

                        <v-flex xs2 pl-2>
                            <v-checkbox
                                    v-model="analyseFilterModel.isStarred"
                                    v-bind:label="$t('analyse.component.analyseListFilter.starred')"
                                    class="justify-end"
                            ></v-checkbox>
                        </v-flex>
                    </v-layout>
                </v-flex>
            </v-layout>
        </v-card-title>
    </v-card>
</template>

<script lang="ts">
    import {Component, Emit, Prop, Vue} from 'vue-property-decorator';
    import {AnalyseFilterModel, AnalyseStatusType} from '@/modules/analyse/models/analyse';
    import {Locale} from '@/modules/user/store/userState';
    import {debounce} from 'helpful-decorators';
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
            { value: AnalyseStatusType.CREATED, text: 'analyse.component.analyseListFilter.status.CREATED' },
            { value: AnalyseStatusType.SUCCESS, text: 'analyse.component.analyseListFilter.status.SUCCESS' },
            { value: AnalyseStatusType.IN_PROGRESS, text: 'analyse.component.analyseListFilter.status.IN_PROGRESS' },
            { value: AnalyseStatusType.FAILED, text: 'analyse.component.analyseListFilter.status.FAILED' }
        ];

        public isPeriod: boolean = true;

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

        @debounce(1500)
        @Emit(CommonEvent.SEND_FORM)
        public submit() {
            return this.analyseFilterModel;
        }
    }
</script>
