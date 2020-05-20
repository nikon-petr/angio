<template>
    <v-layout row wrap>
        <v-flex xs12 class="ml-3">
            <v-switch
                    v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.switch')"
                    v-model="isAutoFill"
            ></v-switch>
        </v-flex>

        <v-flex xs-12>
            <CreateAnalyseFormStepPatientExisting
                    ref="createAnalyseFormStepPatientExisting"
                    v-on:change="onFormStepAnalysePatientChanged"
                    v-if="isAutoFill"
            ></CreateAnalyseFormStepPatientExisting>
            <CreateAnalyseFormStepPatientNew
                    ref="createAnalyseFormStepPatientNew"
                    v-on:change="onFormStepAnalysePatientChanged"
                    v-if="!isAutoFill"
            ></CreateAnalyseFormStepPatientNew>
        </v-flex>
    </v-layout>
</template>

<script lang="ts">
    import {Component, Emit, Vue, Watch} from 'vue-property-decorator';
    import {Locale} from '@/modules/user/store/userState';
    import {State} from 'vuex-class';
    import SingleDatePicker from '@/modules/common/components/SingleDatePicker.vue';
    import CreateAnalyseFormStepPatientExisting
        from '@/modules/analyse/components/CreateAnalyseFormStepPatientExisting.vue';
    import CreateAnalyseFormStepPatientNew from '@/modules/analyse/components/CreateAnalyseFormStepPatientNew.vue';
    import EventWithValidation from '@/modules/common/models/eventWithValidation';
    import {Patient} from '@/modules/patient/models/patient';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';

    @Component({
        components: {
            CreateAnalyseFormStepPatientNew, CreateAnalyseFormStepPatientExisting, SingleDatePicker}
    })
    export default class CreateAnalyseFormStepPatient extends Vue {

        @State((state) => state.user.settings.locale)
        public readonly locale!: Locale;

        public isAutoFill: boolean = false;

        @Watch('isAutoFill')
        public onValidForm(newVal: boolean, oldVal: boolean) {
            if (this.isAutoFill) {
                this.onFormStepAnalysePatientChanged({
                    isValid: false,
                    payload: {
                        id: undefined,
                        fullName: {
                            firstname: '',
                            lastname: '',
                            patronymic: ''
                        },
                        bday: new Date(),
                        address: ''
                    }
                });
            }
        }

        @Emit(CommonEvent.CHANGE)
        public onFormStepAnalysePatientChanged(eventPayload: EventWithValidation<Patient>): EventWithValidation<Patient> {
            return eventPayload;
        }
    }
</script>
