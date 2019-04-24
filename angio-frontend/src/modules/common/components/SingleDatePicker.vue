<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-menu
            v-model="menu"
            v-bind:close-on-content-click="false"
            v-bind:nudge-right="40"
            lazy
            transition="scale-transition"
            offset-y
            full-width
            min-width="290px"
    >
        <template v-slot:activator="{ on }">
            <v-text-field
                    v-model="date"
                    v-bind:label="label"
                    v-on:input="onDateSelected"
                    v-on="on"
                    prepend-icon="event"
                    readonly
                    clearable
            ></v-text-field>
        </template>
        <v-date-picker
                v-model="date"
                v-bind:locale="locale.toString()"
                v-on:input="onDateSelected"
        >
        </v-date-picker>
    </v-menu>
</template>

<script lang="ts">
    import {Component, Emit, Model, Prop, Vue} from 'vue-property-decorator';
    import {Locale} from '@/modules/user/store/userState';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';

    @Component
    export default class SingleDatePicker extends Vue {

        @Prop()
        public readonly locale!: Locale;

        @Prop()
        public readonly label!: string;

        public menu: boolean = false;

        @Model('send-selected-date', { type: String })
        public date: string = '';

        @Emit(CommonEvent.SEND_SELECTED_DATE)
        public onDateSelected() {
            this.menu = false;

            return this.date ? new Date(this.date) : undefined;
        }
    }
</script>
