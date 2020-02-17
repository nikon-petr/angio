<template>
    <v-layout row align-center>
        <v-flex xs6 v-show="isPeriod">
            <SingleDatePicker
                    v-bind:date="startDate"
                    v-bind:locale="locale"
                    v-bind:label="$t('common.component.periodDatePicker.startDate')"
                    v-on:change="(e) => $emit('update:start-date', e)"
            ></SingleDatePicker>
        </v-flex>
        <v-flex xs6 v-show="isPeriod" pl-2>
            <SingleDatePicker
                    v-bind:date="endDate"
                    v-bind:locale="locale"
                    v-bind:label="$t('common.component.periodDatePicker.endDate')"
                    v-on:change="(e) => $emit('update:end-date', e)"
            ></SingleDatePicker>
        </v-flex>
        <v-flex xs6 v-show="!isPeriod">
            <SingleDatePicker
                    v-bind:date="singleDate"
                    v-bind:locale="locale"
                    v-bind:label="$t('common.component.periodDatePicker.singleDate')"
                    v-on:change="(e) => $emit('update:single-date', e)"
            ></SingleDatePicker>
        </v-flex>
    </v-layout>
</template>

<script lang="ts">
    import {Component, Prop, Vue, Watch} from 'vue-property-decorator';
    import SingleDatePicker from '@/modules/common/components/SingleDatePicker.vue';

    @Component({
        components: {SingleDatePicker},
    })
    export default class PeriodDatePicker extends Vue {

        @Prop()
        public readonly locale!: string;

        @Prop()
        public readonly isPeriod!: boolean;

        @Prop()
        public readonly startDate!: Date;

        @Prop()
        public readonly endDate!: Date;

        @Prop()
        public readonly singleDate!: Date;

        @Watch('isPeriod')
        public onIsPeriodChanged(newVal: boolean, oldVal: boolean) {
            if (this.isPeriod) {
                this.$emit('update:single-date', undefined);
            } else {
                this.$emit('update:start-date' , undefined);
                this.$emit('update:end-date', undefined);
            }
        }
    }
</script>
