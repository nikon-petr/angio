<template>
    <StackLayout>
        <v-flex xs12>
            <AnalyseDetailsTitle
                    v-bind:name="analyse && analyse.additionalInfo && analyse.additionalInfo.name"
                    v-bind:analyse-id="analyseId"
                    v-bind:patient="patient"
            ></AnalyseDetailsTitle>
        </v-flex>
    </StackLayout>
</template>

<script lang="ts">
    import {Component, Vue, Prop} from 'vue-property-decorator';
    import StackLayout from '@/modules/common/components/StackLayout.vue';
    import AnalyseDetailsTitle from '@/modules/analyse/components/AnalyseDetailsTitle.vue';
    import {Analyse} from '@/modules/analyse/models/analyse';
    import {AnalyseApiService} from '@/modules/analyse/services/analyseApiService';
    import {PatientApiService} from '@/modules/patient/services/patientApiService';
    import {Patient} from '@/modules/patient/models/patient';

    @Component({
        components: {AnalyseDetailsTitle, StackLayout}
    })
    export default class AnalyseDetails extends Vue {

        @Prop()
        public analyseId!: number;

        public fetching: boolean = false;

        public analyse?: Analyse;

        public patient?: Patient;

        public data() {
            return {
                analyse: undefined,
                patient: undefined
            }
        }

        public created() {
            this.fetching = true;
            AnalyseApiService.getAnalyseById(this.analyseId)
                .then((res) => {
                    this.analyse = res.data.data;

                    PatientApiService.getPatientById(this.analyse.additionalInfo.patientId)
                        .then((res) => {
                            this.patient = res.data.data;
                            this.setDocumentTitle();
                        })
                        .catch((error) => this.$logger.error(error));
                })
                .catch((error) => this.$logger.error(error))
                .finally(() => this.fetching = false)
        }

        public setDocumentTitle() {
            if (this.patient && this.patient.fullName && this.analyse) {
                const firstname: string = this.patient.fullName.firstname ? this.patient.fullName.firstname.substr(0, 1) : '';
                const patronymic: string = this.patient.fullName.patronymic ? this.patient.fullName.patronymic.substr(0, 1) : '';
                document.title = `${this.analyse.additionalInfo.name} - ${this.patient.fullName.lastname}\u00A0${firstname}.${patronymic}. | AngioVision`;
            }
        }
    }
</script>