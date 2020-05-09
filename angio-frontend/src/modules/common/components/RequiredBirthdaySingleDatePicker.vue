<template>
    <v-dialog
            v-model="modal"
            v-bind:return-value.sync="dateString"
            ref="dialog"
            width="290px"
            persistent
            full-width
            lazy
    >
        <template v-slot:activator="{ on }">
            <v-text-field
                    v-model="formattedDate"
                    v-bind:label="label"
                    v-bind:placeholder="$t('common.component.singleDatePicker.placeholder')"
                    v-bind:rules="[v => !!v || $t(''), v => isDateLowerOrCurrent()  || $t('')]"
                    v-bind:disabled="disabled"
                    v-on="on"
                    prepend-inner-icon="event"
                    readonly
                    clearable
                    hide-details
                    outline
                    required
            ></v-text-field>
        </template>
        <v-date-picker
                v-model="dateString"
                v-bind:locale="locale"
        >
            <v-spacer></v-spacer>
            <v-btn
                    v-on:click="modal = false"
                    round
                    flat
            >
                {{ $t('common.component.singleDatePicker.button.cancel') }}
            </v-btn>
            <v-btn
                    v-bind:disabled="!dateString"
                    v-on:click="$refs.dialog.save(dateString)"
                    color="success"
                    round
                    flat
            >
                {{ $t('common.component.singleDatePicker.button.ok') }}
            </v-btn>
        </v-date-picker>
    </v-dialog>
</template>

<script lang="ts">
    import {Component, Model, Prop, Vue} from 'vue-property-decorator';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';

    @Component
    export default class RequiredBirthdaySingleDatePicker extends Vue {

        @Prop()
        public readonly locale!: string;

        @Prop()
        public readonly label!: string;

        @Prop()
        public readonly disabled!: boolean;

        @Model(CommonEvent.CHANGE)
        public readonly date?: Date;

        public modal: boolean = false;

        get dateString(): string | undefined {
            return this.date ? this.date.toISOString().substr(0, 10) : '';
        }

        set dateString(value: string | undefined) {
            this.$emit(CommonEvent.CHANGE, value ? new Date(value) : undefined);
        }

        get formattedDate(): string | undefined {
            // @ts-ignore
            return this.date ? this.$moment(this.date, 'YYYY-MM-DD').format('DD.MM.YYYY') : undefined;
        }

        set formattedDate(value: string | undefined) {
            // @ts-ignore
            this.dateValue = this.$emit(CommonEvent.CHANGE, value ? this.$moment(this.dateValue, 'DD.MM.YYYY').toDate() : undefined);
        }

        public isDateLowerOrCurrent(): boolean {
            if (this.date == undefined) {
                return false
            } else {
                return this.date <= new Date();
            }
        }
    }
</script>
