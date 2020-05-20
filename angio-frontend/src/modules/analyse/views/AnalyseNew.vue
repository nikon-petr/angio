<template>
    <StackLayout>
        <v-flex xs12>
            <BaseSubheader
                    v-bind:value="$t('analyse.component.createAnalyseFormDialog.title')"
            ></BaseSubheader>
        </v-flex>

        <v-flex xs12>
            <CreateAnalyseFormStepper
                    v-bind:create-analyse="createAnalyse"
            ></CreateAnalyseFormStepper>
        </v-flex>
    </StackLayout>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import StackLayout from '@/modules/common/components/StackLayout.vue';
    import BaseSubheader from '@/modules/common/components/BaseSubheader.vue';
    import CreateAnalyseFormStepper from '@/modules/analyse/components/CreateAnalyseFormStepper.vue';
    import {Analyse} from '@/modules/analyse/models/analyse';
    import {PatientApiService} from '@/modules/patient/services/patientApiService';
    import {Patient} from '@/modules/patient/models/patient';
    import {AnalyseApiService} from '@/modules/analyse/services/analyseApiService';

    @Component({
        components: {StackLayout, BaseSubheader, CreateAnalyseFormStepper}
    })
    export default class AnalyseNew extends Vue {

        public createAnalyse(patient: Patient, analyse: Analyse): Promise<void> {
            return new Promise<void>(async (resolve, reject) => {
                if (patient.id == undefined) {
                    let patientRequest = {
                        bday: patient.bday,
                        address: patient.address,
                        fullName: patient.fullName
                    };

                    await PatientApiService
                        .addPatient(patientRequest)
                        .then(response => {
                            analyse.additionalInfo.patientId = response.data.data.id;
                        })
                        .catch((error) => {
                            reject();
                        });
                } else {
                    analyse.additionalInfo.patientId = patient.id;
                }

                await AnalyseApiService
                    .createAnalyse(analyse)
                    .catch((error) => {
                        reject();
                    });
                resolve();
            });
        }
    }
</script>
