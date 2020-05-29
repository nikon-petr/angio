<template>
    <v-layout row>
        <v-flex xs10>
            <span class="display-1">{{ name }}</span><br>
            <span class="font-weight-bold subtitle-1 text--secondary text--darken-4">{{ formatSubtitle() }}</span>
        </v-flex>
        <v-flex xs2 class="text-xs-right">
            <span class="font-weight-light display-2">#{{ analyseId }}</span>
        </v-flex>
    </v-layout>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import {Patient} from '@/modules/patient/models/patient';

    @Component({})
    export default class AnalyseDetailsTitle extends Vue {

        @Prop()
        public readonly name!: string;

        @Prop()
        public readonly analyseId!: number;

        @Prop()
        public readonly patient!: Patient;

        public formatSubtitle(): string {
            let resultArray: string[] = [];
            if (this.patient && this.patient.bday) {
                resultArray.push(this.$moment(this.patient.bday).format('DD.MM.YYYY'))
            }
            if (this.patient
                && this.patient.fullName
                && this.patient.fullName.lastname
                && this.patient.fullName.firstname) {
                let fullName = `${this.patient.fullName.lastname} ${this.patient.fullName.firstname}`;
                if (this.patient.fullName.patronymic) {
                    fullName += ' ' + this.patient.fullName.patronymic;
                }
                resultArray.push(fullName);
            }
            if (this.patient && this.patient.address) {
                resultArray.push(this.patient.address)
            }
            return resultArray.join(' - ')
        }
    }
</script>
