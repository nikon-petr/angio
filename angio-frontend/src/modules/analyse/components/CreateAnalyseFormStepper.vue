import {FormStepAnalyseType} from "../models/analyse";
<template>
    <v-stepper v-model="stepCounter" vertical>
        <v-stepper-step v-bind:complete="stepCounter > 1" step="1">
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepChooseAnalyseType.title') }}
            <small>
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepChooseAnalyseType.subtitle') }}
            </small>
        </v-stepper-step>

        <v-stepper-content pa-0 step="1">
            <CreateAnalyseFormStepChooseAnalyseType
                    ref="createAnalyseFormStepChooseAnalyseType"
                    v-bind:on-form-step-analyse-type-changed="onFormStepAnalyseTypeChanged"
            ></CreateAnalyseFormStepChooseAnalyseType>
            <v-btn
                    color="primary"
                    v-on:click="onStepperForward(2)"
                    v-bind:disabled="!formStepAnalyseTypeNextButtonEnabled"
            >
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.continue') }}
            </v-btn>
        </v-stepper-content>

        <v-stepper-step v-bind:complete="stepCounter > 2" step="2">
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepChooseAnalyseParameter.title') }}
            <small>
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepChooseAnalyseParameter.subtitle') }}
            </small>
        </v-stepper-step>

        <v-stepper-content step="2">
            <CreateAnalyseFormStepChooseAnalyseParameter
                    ref="createAnalyseFormStepChooseAnalyseParameter"
                    v-bind:form-step-analyse-type="formStepAnalyseType"
                    v-bind:on-form-step-analyse-parameters-changed="onFormStepAnalyseParametersChanged"
            ></CreateAnalyseFormStepChooseAnalyseParameter>
            <v-btn
                    color="primary"
                    v-on:click="onStepperForward(3)"
                    v-bind:disabled="!formStepAnalyseParameterNextButtonEnabled"
            >
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.continue') }}
            </v-btn>
            <v-btn v-on:click="onStepperBack()" text>
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.back') }}
            </v-btn>
        </v-stepper-content>

        <v-stepper-step v-bind:complete="stepCounter > 3" step="3" v-bind::rules="[false]">
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepImage.title') }}
            <small>
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepImage.subtitle') }}
            </small>
        </v-stepper-step>

        <v-stepper-content step="3">
            <CreateAnalyseFormStepUploadImages
                    v-bind:form-step-analyse-type="formStepAnalyseType"
                    v-bind:on-form-step-analyse-image-uploaded="onFormStepAnalyseImageUploaded"
            ></CreateAnalyseFormStepUploadImages>
            <v-btn
                    color="primary"
                    v-on:click="onStepperForward(4)"
                    v-bind:disabled="!formStepAnalyseUploadImagesNextButtonEnabled"
            >
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.continue') }}
            </v-btn>
            <v-btn v-on:click="onStepperBack()" text>
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.back') }}
            </v-btn>
        </v-stepper-content>

        <v-stepper-step v-bind:complete="stepCounter > 4" step="4">
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.title') }}
            <small>
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.subtitle') }}
            </small>
        </v-stepper-step>

        <v-stepper-content step="4">
            <CreateAnalyseFormStepPatient
                    ref="createAnalyseFormStepPatient"
                    v-bind:selected-patient="formStepPatient"
            ></CreateAnalyseFormStepPatient>
            <v-btn
                    color="primary"
                    v-on:click="onStepperForward(5)"
                    v-bind:disabled="!formStepPatientButtonEnabled"
            >
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.continue') }}
            </v-btn>
            <v-btn v-on:click="onStepperBack()" text>
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.back') }}
            </v-btn>
        </v-stepper-content>

        <v-stepper-step v-bind:complete="stepCounter > 5" step="5">
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepDisease.title') }}
            <small>
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepDisease.subtitle') }}
            </small>
        </v-stepper-step>

        <v-stepper-content step="5">
            <CreateAnalyseFormStepDisease
                    v-bind:analyse-additional-info="formStepDisease"
            ></CreateAnalyseFormStepDisease>
            <v-btn
                    color="primary"
                    v-bind:disabled="!formStepFinishButtonEnabled"
                    v-on:click="onStepperFinish()"
            >
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.run') }}
            </v-btn>
            <v-btn v-on:click="onStepperBack()" text>
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.back') }}
            </v-btn>
        </v-stepper-content>

        <v-container
                fluid
                fill-height
                justify-center
                align-center
                v-if="fetching"
        >
            <v-progress-circular :indeterminate="true" :background-opacity="0" pa-0 ma-0></v-progress-circular>
        </v-container>

        <v-container
                fluid
                fill-height
                justify-center
                align-center
                v-if="hasErrorMessages"
        >
            <BuiltInErrorMessage
                    v-bind:error-messages="mapErrorMessages"
            ></BuiltInErrorMessage>
        </v-container>
    </v-stepper>
</template>

<script lang="ts">
    import {Component, Prop, Ref, Vue, Watch} from 'vue-property-decorator';
    import CreateAnalyseFormStepChooseAnalyseType
        from '@/modules/analyse/components/CreateAnalyseFormStepChooseAnalyseType.vue';
    import {
        AnalyseAdditionalInfo,
        AnalyseFullData,
        AnalyseType,
        FormStepAnalyseParameter,
        FormStepAnalyseType,
    } from '@/modules/analyse/models/analyse';
    import CreateAnalyseFormStepChooseAnalyseParameter
        from '@/modules/analyse/components/CreateAnalyseFormStepChooseAnalyseParameter.vue';
    import CreateAnalyseFormStepUploadImages from '@/modules/analyse/components/CreateAnalyseFormStepUploadImages.vue';
    import CreateAnalyseFormStepPatient from '@/modules/analyse/components/CreateAnalyseFormStepPatient.vue';
    import CreateAnalyseFormStepDisease from '@/modules/analyse/components/CreateAnalyseFormStepDisease.vue';
    import {Patient} from '@/modules/patient/models/patient';
    import {UserState} from '@/modules/user/store/userState';
    import {State} from 'vuex-class';
    import BuiltInErrorMessage from '@/modules/common/components/BuiltInErrorMessage.vue';

    @Component({
        components: {CreateAnalyseFormStepChooseAnalyseType, CreateAnalyseFormStepChooseAnalyseParameter,
            CreateAnalyseFormStepUploadImages, CreateAnalyseFormStepPatient, CreateAnalyseFormStepDisease,
            BuiltInErrorMessage}
    })
    export default class CreateAnalyseFormStepper extends Vue {

        @State((state) => state.user)
        public readonly user!: UserState;

        public errorMessages: string[] = [];

        public fetching: boolean = false;

        public stepCounter: number = 1;

        // #1 Analyse type
        public formStepAnalyseTypeNextButtonEnabled = false;
        @Ref()
        readonly createAnalyseFormStepChooseAnalyseType!: CreateAnalyseFormStepChooseAnalyseType;
        public formStepAnalyseType: FormStepAnalyseType = FormStepAnalyseType.GEOMETRIC;

        // #2 Analyse parameter
        public formStepAnalyseParameterNextButtonEnabled = false;
        @Ref()
        readonly createAnalyseFormStepChooseAnalyseParameter!: CreateAnalyseFormStepChooseAnalyseParameter;
        public formStepAnalyseParameters: FormStepAnalyseParameter[] = [];

        // #3 Images
        public formStepAnalyseUploadImagesNextButtonEnabled = false;
        public fileId: string | null = null;

        // #4 Patient
        public formStepPatientButtonEnabled = false;
        @Ref()
        readonly createAnalyseFormStepPatient!: CreateAnalyseFormStepPatient;
        public formStepPatient: Patient = {
            id: -1,
            fullName: {
                firstname: '',
                lastname: '',
                patronymic: ''
            },
            bday: new Date(),
            address: ''
        };

        // #5 Disease
        public formStepFinishButtonEnabled = false;
        public formStepDisease: AnalyseAdditionalInfo = {
            name: '',
            patientId: -1,
            diagnostician: {
                username: '',
                fullName: {
                    firstname: '',
                    lastname: '',
                    patronymic: ''
                }
            },
            shortDescription: '',
            fullDescription: '',
            type: '',
            comment: '',
            conclusion: ''
        };

        @Prop()
        public readonly createAnalyse!: (analyseFullData: AnalyseFullData) => Promise<void>;

        @Prop()
        public readonly createPatientAndAnalyse!: (patient: Patient, analyseFullData: AnalyseFullData) => Promise<void>;

        // #1 Analyse type
        public onFormStepAnalyseTypeChanged(formStepAnalyseType: FormStepAnalyseType) {
            this.formStepAnalyseType = formStepAnalyseType;
            this.updateNextButtonEnabled(1);
        }

        // #2 Analyse parameter
        public onFormStepAnalyseParametersChanged(formStepAnalyseParameters: FormStepAnalyseParameter[]) {
            this.formStepAnalyseParameters = formStepAnalyseParameters;
            this.updateNextButtonEnabled(2);
        }

        // #3 Images
        public onFormStepAnalyseImageUploaded(fileId: string) {
            this.fileId = fileId;
            this.updateNextButtonEnabled(3);
        }

        // #4 Patient
        @Watch('formStepPatient', {deep: true, immediate: true})
        public onPatientChange(newVal: Patient | undefined, oldVal: Patient | undefined) {
            this.updateNextButtonEnabled(4);
        }

        // #5 Disease
        @Watch('formStepDisease', {deep: true, immediate: true})
        public onDiseaseChange(newVal: AnalyseAdditionalInfo | undefined, oldVal: AnalyseAdditionalInfo | undefined) {
            this.updateNextButtonEnabled(5);
        }

        public onStepperForward(stepTo: number) {
            this.stepCounter = stepTo;
        }

        public onStepperBack() {
            this.stepCounter = this.stepCounter - 1;
            this.errorMessages = [];
        }

        public onStepperFinish() {
            this.onStepperForward(6);

            this.fetching = true;
            this.errorMessages = [];

            this.formStepDisease.diagnostician.username = this.user.info.email ? this.user.info.email : '';
            this.formStepDisease.diagnostician.fullName = this.user.info.fullName;

            if (this.formStepDisease.type == this.getAnalyseTypeName(AnalyseType.PRIMARY.toString())) {
                this.formStepDisease.type = AnalyseType.PRIMARY.toString();
            } else if (this.formStepDisease.type == this.getAnalyseTypeName(AnalyseType.SUBSEQUENT.toString())) {
                this.formStepDisease.type = AnalyseType.SUBSEQUENT.toString();
            }

            let analyseFullData = {
                additionalInfo: this.formStepDisease,
                originalImage: {
                    id: this.fileId ? Number(this.fileId) : 0
                }
            };

            // Fix only for english version of site
            if (analyseFullData.additionalInfo.type == "Первичный") {
                analyseFullData.additionalInfo.type = AnalyseType.PRIMARY;
            } else if (analyseFullData.additionalInfo.type == "Последующий") {
                analyseFullData.additionalInfo.type = AnalyseType.SUBSEQUENT;
            }

            if (this.formStepPatient.id == -1) {
                console.log(`stepper 1`);
                this.createPatientAndAnalyse(this.formStepPatient, analyseFullData)
                    .then(() => {
                        console.log(`stepper then 1 success`);
                        this.fetching = false;
                        this.errorMessages = [];
                        this.clearAllData();
                        this.navigateToListAnalyse();
                    })
                    .catch((error) => {
                        console.log(`stepper then 1 error`);
                        this.errorMessages = ['analyse.component.createAnalyseFormDialog.stepper.error.unknown'];
                        this.$logger.error(error);
                        this.stepCounter = 5;
                        this.fetching = false
                    })
            } else {
                analyseFullData.additionalInfo.patientId = this.formStepPatient.id;
                console.log(`stepper 2`);
                this.createAnalyse(analyseFullData)
                    .then((response) => {
                        console.log(`stepper then 2 success`);
                        this.fetching = false;
                        this.errorMessages = [];
                        this.clearAllData();
                        this.navigateToListAnalyse();
                    })
                    .catch((error) => {
                        console.log(`stepper then 2 error`);
                        this.errorMessages = ['analyse.component.createAnalyseFormDialog.stepper.error.unknown'];
                        this.$logger.error(error);
                        this.stepCounter = 5;
                        this.fetching = false;
                    })
            }
        }

        public updateNextButtonEnabled(step: number) {
            switch (step) {
                case 1:
                    this.formStepAnalyseTypeNextButtonEnabled = this.formStepAnalyseType != undefined;
                    break;
                case 2:
                    this.formStepAnalyseParameterNextButtonEnabled = this.formStepAnalyseParameters.length != 0;
                    break;
                case 3:
                    this.formStepAnalyseUploadImagesNextButtonEnabled = this.fileId != null;
                    break;
                case 4:
                    if (this.formStepPatient.id != -1) {
                        this.formStepPatientButtonEnabled = true;
                    } else {
                        this.formStepPatientButtonEnabled = this.isPatientFieldsValid();
                    }
                    break;
                case 5:
                    this.formStepFinishButtonEnabled = this.isDiseaseFieldsFalid();
                    break;
            }
        }

        public isPatientFieldsValid(): boolean {
            if (this.formStepPatient.bday == undefined || this.formStepPatient.bday > new Date()) {
                return false;
            }

            if (this.formStepPatient.address == undefined || this.formStepPatient.address.length < 3 || this.formStepPatient.address.length > 100) {
                return false;
            }

            if (this.formStepPatient.fullName == undefined || this.formStepPatient.fullName.firstname == undefined || this.formStepPatient.fullName.firstname.length < 1 || this.formStepPatient.fullName.firstname.length > 30) {
                return false;
            }

            if (this.formStepPatient.fullName.lastname == undefined || this.formStepPatient.fullName.lastname.length < 1 || this.formStepPatient.fullName.lastname.length > 30) {
                return false;
            }

            return true;
        }

        public isDiseaseFieldsFalid(): boolean {
            if (this.formStepDisease == undefined) {
                return false;
            }

            if (this.formStepDisease.type == undefined || this.formStepDisease.type == '') {
                return false;
            }

            if (this.formStepDisease.name == undefined || this.formStepDisease.name.length < 5 || this.formStepDisease.name.length > 75) {
                return false;
            }

            if (this.formStepDisease.shortDescription == undefined || this.formStepDisease.shortDescription.length < 5 || this.formStepDisease.shortDescription.length > 100) {
                return false;
            }

            return true;
        }

        public getAnalyseTypeName(type: string): string {
            return this.$t('analyse.model.analyseType.' + type).toString()
        }

        public clearAllData() {
            this.stepCounter = 1;

            // #1 Analyse type
            this.formStepAnalyseTypeNextButtonEnabled = false;
            this.formStepAnalyseType = FormStepAnalyseType.GEOMETRIC;
            // @ts-ignore
            this.createAnalyseFormStepChooseAnalyseType.resetAll();

            // #2 Analyse parameter
            this.formStepAnalyseParameterNextButtonEnabled = false;
            this.formStepAnalyseParameters = [];
            // @ts-ignore
            this.createAnalyseFormStepChooseAnalyseParameter.resetAll();

            // #3 Images
            this.formStepAnalyseUploadImagesNextButtonEnabled = false;
            this.fileId = null;

            // #4 Patient
            this.formStepPatientButtonEnabled = false;
            // @ts-ignore
            this.createAnalyseFormStepPatient.resetAll();
            this.formStepPatient = {
                id: -1,
                fullName: {
                    firstname: '',
                    lastname: '',
                    patronymic: ''
                },
                bday: new Date(),
                address: ''
            };

            // #5 Disease
            this.formStepFinishButtonEnabled = false;
            this.formStepDisease = {
                name: '',
                patientId: -1,
                diagnostician: {
                    username: '',
                    fullName: {
                        firstname: '',
                        lastname: '',
                        patronymic: ''
                    }
                },
                shortDescription: '',
                fullDescription: '',
                type: '',
                comment: '',
                conclusion: ''
            };
        }

        public navigateToListAnalyse() {
            this.$router.push({path: '/'});
            this.$router.go(0);
        }

        get mapErrorMessages() {
            return this.errorMessages.map(message => this.$t(message))
        }

        get hasErrorMessages() {
            return this.errorMessages.length > 0;
        }
    }
</script>
