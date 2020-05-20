<template>
    <v-form
            v-model="valid"
            ref="form"
    >
        <v-container>
            <v-layout row wrap>
                <v-flex xs4>
                    <v-text-field
                            v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.form.lastname.field')"
                            v-bind:rules="[v => !!v || $t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.form.lastname.validation.NotEmpty')]"
                            v-model="newPatient.fullName.lastname"
                            type="text"
                            name="name"
                            maxlength="30"
                            browser-autocomplete="off"
                            required
                            outline
                    ></v-text-field>
                </v-flex>
                <v-flex xs4>
                    <v-text-field
                            v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.form.firstname.field')"
                            v-bind:rules="[v => !!v || $t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.form.firstname.validation.NotEmpty')]"
                            v-model="newPatient.fullName.firstname"
                            type="text"
                            name="name"
                            maxlength="30"
                            browser-autocomplete="off"
                            required
                            outline
                    ></v-text-field>
                </v-flex>
                <v-flex xs4>
                    <v-text-field
                            v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.form.patronymic.field')"
                            v-model="newPatient.fullName.patronymic"
                            type="text"
                            name="name"
                            maxlength="30"
                            browser-autocomplete="off"
                            outline
                    ></v-text-field>
                </v-flex>
                <v-flex xs8>
                    <v-text-field
                            v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.form.address.field')"
                            v-bind:rules="[v => !!v || $t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.form.address.validation.NotEmpty')]"
                            v-model="newPatient.address"
                            type="text"
                            name="name"
                            maxlength="100"
                            browser-autocomplete="off"
                            required
                            outline
                    ></v-text-field>
                </v-flex>
                <v-flex xs4>
                    <SingleDatePicker
                            v-on:change="(e) => newPatient.bday = e"
                            v-bind:date="newPatient.bday"
                            v-bind:locale="locale"
                            v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.form.bday.field')"
                            v-bind:required="true"
                            v-bind:hide-details="false"
                            v-bind:clearable="false"
                            v-bind:rules="[v => !!v, v => $moment(newPatient.bday).isSameOrBefore()
                                || $t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.form.bday.validation.BDayShouldNotBeFuture')]"
                    ></SingleDatePicker>
                </v-flex>
            </v-layout>
        </v-container>
    </v-form>
</template>

<script lang="ts">
    import {Component, Emit, Vue, Watch} from 'vue-property-decorator';
    import {Patient} from '@/modules/patient/models/patient';
    import {Locale} from '@/modules/user/store/userState';
    import {State} from 'vuex-class';
    import SingleDatePicker from '@/modules/common/components/SingleDatePicker.vue';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';
    import EventWithValidation from '@/modules/common/models/eventWithValidation';

    @Component({
        components: {SingleDatePicker}
    })
    export default class CreateAnalyseFormStepPatient extends Vue {

        @State((state) => state.user.settings.locale)
        public readonly locale!: Locale;

        public newPatient: Patient = {
            id: undefined,
            fullName: {
                firstname: '',
                lastname: '',
                patronymic: ''
            },
            bday: new Date(),
            address: ''
        };

        public valid: boolean = false;

        @Watch('valid', {deep: true, immediate: true})
        public onValidChanged(newVal: boolean, oldVal: boolean) {
            this.onFormStepAnalysePatientNewChanged(this.newPatient);
        }

        @Watch('newPatient', {deep: true, immediate: true})
        public onPatientForm(newVal: Patient, oldVal: Patient) {
            this.onFormStepAnalysePatientNewChanged(this.newPatient);
        }

        @Emit(CommonEvent.CHANGE)
        public onFormStepAnalysePatientNewChanged(patient: Patient): EventWithValidation<Patient> {
            patient.id = undefined;

            return {
                isValid: this.valid,
                payload: patient
            }
        }
    }
</script>
