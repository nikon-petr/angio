<template>
    <v-dialog
            v-model="isDialogOpened"
            fullscreen
            hide-overlay
            transition="dialog-bottom-transition"
            scrollable
    >
        <v-card tile>
            <v-toolbar card dark color="primary">
                <v-btn icon @click.native="close()" dark>
                    <v-icon>close</v-icon>
                </v-btn>
                <v-toolbar-title>
                    {{ $t('analyse.component.createAnalyseFormDialog.title') }}
                </v-toolbar-title>
            </v-toolbar>
            <v-card-text>
                <v-layout row justify-space-between>
                    <v-flex xs1></v-flex>
                    <v-flex xs10>
                        <CreateAnalyseFormStepper
                                v-bind:AnalyseListPaginationcreate-patient-and-analyse="createPatientAndAnalyse"
                                v-bind:create-analyse="createAnalyse"
                        ></CreateAnalyseFormStepper>
                    </v-flex>
                    <v-flex xs1></v-flex>
                </v-layout>
            </v-card-text>
            <div style="flex: 1 1 auto;"></div>
        </v-card>
    </v-dialog>
</template>

<script lang="ts">
    import {Component, Prop, Vue, Watch} from 'vue-property-decorator';
    import StackLayout from '@/modules/common/components/StackLayout.vue';
    import BaseSubheader from '@/modules/common/components/BaseSubheader.vue';
    import CreateAnalyseFormStepper from '@/modules/analyse/components/CreateAnalyseFormStepper.vue';
    import {Analyse, AnalyseFullData} from "@/modules/analyse/models/analyse";
    import {PatientApiService} from '@/modules/patient/services/patientApiService';
    import {Patient} from '@/modules/patient/models/patient';
    import {AnalyseApiService} from '@/modules/analyse/services/analyseApiService';

    @Component({
        components: {StackLayout, BaseSubheader, CreateAnalyseFormStepper}
    })
    export default class CreateAnalyseFormDialog extends Vue {

        @Prop()
        public readonly dialogUpdateWatcher!: boolean;

        public isDialogOpened: boolean = false;

        @Watch('dialogUpdateWatcher')
        public onUpdate() {
            this.isDialogOpened = true;
        }

        public close() {
            this.isDialogOpened = false;
        }

        public createPatientAndAnalyse(patient: Patient, analyseFullData: AnalyseFullData): Promise<void> {
            let patientRequest = {
                bday: patient.bday,
                address: patient.address,
                fullName: patient.fullName
            };

            return new Promise<void>(async (resolve) => {
                await PatientApiService
                    .addPatient(patientRequest)
                    .then((response) => {
                        analyseFullData.additionalInfo.patientId = response.data.data.id;
                        this.isDialogOpened = false;
                    });

                if (analyseFullData.additionalInfo.patientId != -1) {
                    await AnalyseApiService
                        .createAnalyse(analyseFullData)
                        .then((response) => {});
                }
                resolve();
            });
        }

        public createAnalyse(analyseFullData: AnalyseFullData): Promise<Analyse> {
            return new Promise<Analyse>(async (resolve) => {
                await AnalyseApiService
                    .createAnalyse(analyseFullData)
                    .then((response) => {});
                resolve();
            });
        }
    }
</script>
