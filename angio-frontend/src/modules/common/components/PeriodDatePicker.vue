<template>
    <v-layout row align-center>
        <v-flex xs6 v-show="isPeriod">
            <SingleDatePicker
                    v-bind:value="startDate"
                    v-bind:locale="locale"
                    v-bind:label="$t('common.component.periodDatePicker.startDate')"
                    v-on:change="(e) => $emit('update:startDate', e)"
            ></SingleDatePicker>
        </v-flex>
        <v-flex xs6 v-show="isPeriod" pl-2>
            <SingleDatePicker
                    v-bind:value="endDate"
                    v-bind:locale="locale"
                    v-bind:label="$t('common.component.periodDatePicker.endDate')"
                    v-on:change="(e) => $emit('update:endDate', e)"
            ></SingleDatePicker>
        </v-flex>
        <v-flex xs6 v-show="!isPeriod">
            <SingleDatePicker
                    v-bind:date="singleDate"
                    v-bind:locale="locale"
                    v-bind:label="$t('common.component.periodDatePicker.singleDate')"
                    v-on:change="(e) => $emit('update:date', e)"
            ></SingleDatePicker>
        </v-flex>
    </v-layout>
</template>

<script lang="ts">
    import {Component, Prop, Vue, Watch} from 'vue-property-decorator';
    import {Locale} from '@/modules/user/store/userState';
    import SingleDatePicker from '@/modules/common/components/SingleDatePicker.vue';

    @Component({
        components: {SingleDatePicker},
    })
    export default class PeriodDatePicker extends Vue {

        @Prop()
        public readonly locale!: Locale;

        @Prop()
        public isPeriod!: boolean;

        @Prop()
        public startDate!: Date;

        @Prop()
        public endDate!: Date;

        @Prop()
        public singleDate!: Date;

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
