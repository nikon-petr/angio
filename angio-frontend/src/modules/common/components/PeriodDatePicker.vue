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

        public periodDateModel: PeriodDateModel = {
            startDate: undefined,
            endDate: undefined,
            date: undefined
        };

        public onStartDateSelected(date?: Date) {
            this.periodDateModel.startDate = date;
            this.sendPeriod();
        }

        public onEndDateSelected(date?: Date) {
            this.periodDateModel.endDate = date;
            this.sendPeriod();
        }

        public onDateSelected(date?: Date) {
            this.periodDateModel.date = date;
            this.sendPeriod();
        }

        @Watch('isPeriod')
        public onIsPeriodChanged(newVal: boolean, oldVal: boolean) {
            this.sendPeriod();
        }
        
        @Emit(CommonEvent.SEND_PERIOD)
        public sendPeriod() {
            if (this.isPeriod) {
                this.periodDateModel.date = undefined;

            } else {
                this.periodDateModel.startDate = undefined;
                this.periodDateModel.endDate = undefined;
            }
            return this.periodDateModel;
        }
    }
</script>
