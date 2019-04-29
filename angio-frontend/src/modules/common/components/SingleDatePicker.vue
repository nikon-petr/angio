<template>
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
                    v-on:focusin="modal = true"
                    v-on:click:clear.stop="clearDateValue"
                    placeholder="дд.мм.гггг"
                    ref="dateTextField"
                    prepend-inner-icon="event"
                    readonly
                    clearable
                    hide-details
                    outline
            ></v-text-field>
        </template>
        <v-date-picker
                v-model="dateString"
                v-bind:locale="locale"
        >
            <v-spacer></v-spacer>
            <v-btn flat round color="primary" v-on:click="modal = false">Cancel</v-btn>
            <v-btn flat round color="primary" v-on:click="onDateSelected">OK</v-btn>
        </v-date-picker>
    </v-dialog>
</template>

<script lang="ts">
    import {Component, Emit, Model, Prop, Vue, Watch} from 'vue-property-decorator';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';

    @Component
    export default class SingleDatePicker extends Vue {

        @Prop()
        public readonly locale!: string;

        @Prop()
        public readonly label!: string;

        public modal: boolean = false;

        @Model('send-selected-date', {type: Date})
        public readonly date!: Date;

        public dateValue?: Date | null = null;

        @Watch('date', {immediate: true})
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

        set formattedDate(value: string) {
            // @ts-ignore
            this.dateValue = value ? this.$moment(this.dateValue, 'YYYY-MM-DD').toDate() : undefined;
        }

        public clearDateValue() {
            this.dateValue = undefined;
            this.onDateSelected();
            this.$nextTick(() => {
                // @ts-ignore
                this.$refs.dateTextField.blur();
            })
        }

        @Emit(CommonEvent.CHANGE)
        public onDateSelected() {
            this.modal = false;
            return this.dateValue;
        }
    }
</script>
