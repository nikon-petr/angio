<template>
    <v-layout row align-center>
        <v-flex xs6 v-show="isPeriod">
            <SingleDatePicker
                    v-bind:locale="locale"
                    v-bind:label="$t('common.component.periodDatePicker.startDate')"
                    v-on:send-selected-date="onStartDateSelected"
            ></SingleDatePicker>
        </v-flex>
        <v-flex xs6 v-show="isPeriod" pl-2>
            <SingleDatePicker
                    v-bind:locale="locale"
                    v-bind:label="$t('common.component.periodDatePicker.endDate')"
                    v-on:send-selected-date="onEndDateSelected"
            ></SingleDatePicker>
        </v-flex>
        <v-flex xs6 v-show="!isPeriod">
            <SingleDatePicker
                    v-bind:locale="locale"
                    v-bind:label="$t('common.component.periodDatePicker.singleDate')"
                    v-on:send-selected-date="onDateSelected"
            ></SingleDatePicker>
        </v-flex>
    </v-layout>
</template>

<script lang="ts">
    import {Component, Emit, Prop, Vue, Watch} from 'vue-property-decorator';
    import {Locale} from '@/modules/user/store/userState';
    import SingleDatePicker from '@/modules/common/components/SingleDatePicker.vue';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';
    import {PeriodDateModel} from '@/modules/common/models/PeriodDateModel';

    @Component({
        components: {SingleDatePicker},
    })
    export default class PeriodDatePicker extends Vue {

        @Prop()
        public readonly locale!: Locale;

        @Prop()
        public isPeriod!: boolean;

        public periodDate: PeriodDateModel = {
            startDate: undefined,
            endDate: undefined,
            date: undefined
        };

        public onStartDateSelected(date?: Date) {
            this.periodDate.startDate = date;
            this.sendPeriod();
        }

        public onEndDateSelected(date?: Date) {
            this.periodDate.endDate = date;
            this.sendPeriod();
        }

        public onDateSelected(date?: Date) {
            this.periodDate.date = date;
            this.sendPeriod();
        }

        @Watch('isPeriod')
        public onIsPeriodChanged(newVal: boolean, oldVal: boolean) {
            if (this.isPeriod) {
                this.periodDate.date = undefined;

            } else {
                this.periodDate.startDate = undefined;
                this.periodDate.endDate = undefined;
            }
            this.sendPeriod();
        }
        
        @Emit(CommonEvent.SEND_PERIOD)
        public sendPeriod() {
            return this.periodDate;
        }
    }
</script>
