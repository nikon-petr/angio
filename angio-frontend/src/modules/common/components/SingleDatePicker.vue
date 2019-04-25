<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-dialog
            v-model="modal"
            ref="dialog"
            persistent
            lazy
            full-width
            width="290px"
    >
        <template v-slot:activator="{ on }">
            <v-text-field
                    v-model="formattedDate"
                    v-bind:label="label"
                    v-on:focus="modal = true"
                    prepend-inner-icon="event"
                    readonly
                    clearable
                    outline
            ></v-text-field>
        </template>
        <v-date-picker
                v-model="dateString"
                v-bind:locale="locale.toString()"
        >
            <v-spacer></v-spacer>
            <v-btn flat round color="primary" v-on:click="modal = false">Cancel</v-btn>
            <v-btn flat round color="primary" v-on:click="onDateSelected">OK</v-btn>
        </v-date-picker>
    </v-dialog>
</template>

<script lang="ts">
    import {Component, Emit, Model, Prop, Vue, Watch} from 'vue-property-decorator';
    import {Locale} from '@/modules/user/store/userState';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';

    @Component
    export default class SingleDatePicker extends Vue {

        @Prop()
        public readonly locale!: Locale;

        @Prop()
        public readonly label!: string;

        public modal: boolean = false;

        @Model('send-selected-date', { type: Date })
        public readonly date!: Date;

        public dateValue?: Date | null = null;

        @Watch('date', { immediate: true })
        public onDateChange(newVal: Date, oldVal: Date) {
            this.dateValue = newVal;
        }

        get dateString(): string {
            return this.dateValue ? this.dateValue.toISOString().substr(0, 10) : '';
        }

        set dateString(value: string) {
            this.dateValue = new Date(value);
        }

        get formattedDate(): string {
            // @ts-ignore
            return this.dateValue ? this.$moment(this.dateValue, 'YYYY-MM-DD').format('DD.MM.YYYY') : this.dateValue;
        }

        @Emit(CommonEvent.SEND_SELECTED_DATE)
        public onDateSelected() {
            this.modal = false;
            return this.dateValue;
        }
    }
</script>
