<template>
    <v-stepper
            v-model="stepCounter"
            style="webkit-box-shadow: 0px 0px 0px 0px; box-shadow: 0px 0px 0px 0px;"
            vertical
    >
        <v-stepper-step
                v-bind:complete="stepCounter > 1"
                step="1"
        >
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepChooseAnalyseType.title') }}
            <small>
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepChooseAnalyseType.subtitle') }}
            </small>
        </v-stepper-step>

        <v-stepper-content pa-0 step="1">
            <CreateAnalyseFormStepChooseAnalyseType
                    v-on:change="handleChangeAnalyseType"
            ></CreateAnalyseFormStepChooseAnalyseType>
            <v-btn
                    v-on:click="onStepperForward(2)"
                    v-bind:disabled="!formValid[0]"
                    color="primary"
                    round
            >
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.continue') }}
            </v-btn>
        </v-stepper-content>

        <v-stepper-step
                v-bind:complete="stepCounter > 2"
                step="2"
        >
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepChooseAnalyseParameter.title') }}
            <small>
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepChooseAnalyseParameter.subtitle') }}
            </small>
        </v-stepper-step>

        <v-stepper-content step="2">
            <CreateAnalyseFormStepChooseAnalyseParameter
                    v-bind:form-step-analyse-type="formStepAnalyseType"
                    v-on:change="handleChangeAnalyseParameter"
            ></CreateAnalyseFormStepChooseAnalyseParameter>
            <v-btn
                    v-on:click="onStepperBack()"
                    round
                    flat
            >
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.back') }}
            </v-btn>
            <v-btn
                    v-on:click="onStepperForward(3)"
                    v-bind:disabled="!formValid[1]"
                    color="primary"
                    round
            >
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.continue') }}
            </v-btn>
        </v-stepper-content>

        <v-stepper-step
                v-bind:complete="stepCounter > 3"
                step="3"
        >
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepImage.title') }}
            <small>
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepImage.subtitle') }}
            </small>
        </v-stepper-step>

        <v-stepper-content step="3">
            <CreateAnalyseFormStepUploadImages
                    v-on:change="handleChangeFileId"
            ></CreateAnalyseFormStepUploadImages>
            <v-btn
                    v-on:click="onStepperBack()"
                    round
                    flat
            >
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.back') }}
            </v-btn>
            <v-btn
                    v-on:click="onStepperForward(4)"
                    v-bind:disabled="!formValid[2]"
                    color="primary"
                    round
            >
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.continue') }}
            </v-btn>
        </v-stepper-content>

        <v-stepper-step
                v-bind:complete="stepCounter > 4"
                step="4"
        >
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.title') }}
            <small>
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.subtitle') }}
            </small>
        </v-stepper-step>

        <v-stepper-content step="4">
            <CreateAnalyseFormStepPatient
                    v-on:change="handleChangePatient"
            ></CreateAnalyseFormStepPatient>
            <v-btn
                    v-on:click="onStepperBack()"
                    round
                    flat
            >
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.back') }}
            </v-btn>
            <v-btn
                    v-on:click="onStepperForward(5)"
                    v-bind:disabled="!formValid[3]"
                    color="primary"
                    round
            >
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.continue') }}
            </v-btn>
        </v-stepper-content>

        <v-stepper-step
                v-bind:complete="stepCounter > 5"
                step="5"
        >
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepDisease.title') }}
            <small>
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepDisease.subtitle') }}
            </small>
        </v-stepper-step>

        <v-stepper-content step="5">
            <CreateAnalyseFormStepDisease
                    v-on:change="handleChangeDisease"
            ></CreateAnalyseFormStepDisease>
            <v-btn
                    v-on:click="onStepperBack()"
                    round
                    flat
            >
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.back') }}
            </v-btn>
            <v-btn
                    v-on:click="onStepperFinish()"
                    v-bind:disabled="!formValid[4]"
                    color="primary"
                    round
            >
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.run') }}
            </v-btn>
        </v-stepper-content>

        <v-container
                v-if="fetching"
                fluid
                fill-height
                justify-center
                align-center
        >
            <v-progress-circular
                    v-bind:indeterminate="true"
                    v-bind:background-opacity="0"
                    pa-0
                    ma-0
            ></v-progress-circular>
        </v-container>

        <v-container
                v-if="hasErrorMessages"
                fluid
                fill-height
                justify-center
                align-center
        >
            <BuiltInErrorMessage
                    v-bind:error-messages="mapErrorMessages"
            ></BuiltInErrorMessage>
        </v-container>
    </v-stepper>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import CreateAnalyseFormStepChooseAnalyseType
        from '@/modules/analyse/components/CreateAnalyseFormStepChooseAnalyseType.vue';
    import {
        FormStepAnalyseParameter,
        FormStepAnalyseType,
    } from '@/modules/analyse/helpers/formStep';
    import CreateAnalyseFormStepChooseAnalyseParameter
        from '@/modules/analyse/components/CreateAnalyseFormStepChooseAnalyseParameter.vue';
    import CreateAnalyseFormStepUploadImages from '@/modules/analyse/components/CreateAnalyseFormStepUploadImages.vue';
    import CreateAnalyseFormStepPatient from '@/modules/analyse/components/CreateAnalyseFormStepPatient.vue';
    import CreateAnalyseFormStepDisease from '@/modules/analyse/components/CreateAnalyseFormStepDisease.vue';
    import {Patient} from '@/modules/patient/models/patient';
    import {UserState} from '@/modules/user/store/userState';
    import {State} from 'vuex-class';
    import BuiltInErrorMessage from '@/modules/common/components/BuiltInErrorMessage.vue';
    import {Analyse, AnalyseAdditionalInfo} from '@/modules/analyse/models/analyse';
    import EventWithValidation from '@/modules/common/models/eventWithValidation';

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

        public formValid: boolean[] = Array<boolean>(5).fill(false);

        // #1 Analyse type
        public formStepAnalyseType: FormStepAnalyseType | undefined = undefined;

        // #2 Analyse parameter
        public formStepAnalyseParameters: FormStepAnalyseParameter[] | undefined = undefined;

        // #4 Patient
        public formStepPatient: Patient = {
            id: undefined,
            fullName: {
                firstname: '',
                lastname: '',
                patronymic: ''
            },
            bday: new Date(),
            address: ''
        };

        // #3 & #5 Image and Disease
        public analyse: Analyse = {
            additionalInfo: {
                name: '',
                patientId: undefined,
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
            },
            id: undefined,
            starred: undefined,
            analyseDate: undefined,
            geometricAnalyse: undefined,
            bloodFlowAnalyse: undefined,
            originalImage: {
                filename: undefined,
                url: undefined,
                id: undefined
            }
        };

        @Prop()
        public readonly createAnalyse!: (patient: Patient, analyse: Analyse) => Promise<void>;

        // #1 Analyse type
        public handleChangeAnalyseType(eventPayload: EventWithValidation<FormStepAnalyseType>) {
            this.formStepAnalyseType = eventPayload.payload;
            Vue.set(this.formValid, 0, eventPayload.isValid);
        }

        // #2 Analyse parameter
        public handleChangeAnalyseParameter(eventPayload: EventWithValidation<FormStepAnalyseParameter[]>) {
            this.formStepAnalyseParameters = eventPayload.payload;
            Vue.set(this.formValid, 1, eventPayload.isValid);
        }

        // #3 Images
        public handleChangeFileId(eventPayload: EventWithValidation<string>) {
            if (eventPayload.payload != undefined) {
                this.analyse.originalImage.id = eventPayload.payload;
            }
            Vue.set(this.formValid, 2, eventPayload.isValid);
        }

        // #4 Patient
        public handleChangePatient(eventPayload: EventWithValidation<Patient>) {
            if (eventPayload.payload != undefined) {
                this.formStepPatient = eventPayload.payload;
            }
            Vue.set(this.formValid, 3, eventPayload.isValid);
        }

        // #5 Disease
        public handleChangeDisease(eventPayload: EventWithValidation<AnalyseAdditionalInfo>) {
            if (eventPayload.payload != undefined) {
                this.analyse.additionalInfo = eventPayload.payload;
            }
            Vue.set(this.formValid, 4, eventPayload.isValid);
        }

        public data() {
            return {
                formStepAnalyseType: undefined,
                formStepAnalyseParameters: undefined
            }
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

            this.createAnalyse(this.formStepPatient, this.analyse)
                .then(() => {
                    this.fetching = false;
                    this.errorMessages = [];
                    this.clearAllData();
                    this.navigateToListAnalyse();
                })
                .catch((error) => {
                    this.errorMessages = ['analyse.component.createAnalyseFormDialog.stepper.error.unknown'];
                    this.$logger.error(error);
                    this.stepCounter = 5;
                    this.fetching = false
                });
        }

        public clearAllData() {
            this.stepCounter = 1;

            this.formValid = Array<boolean>(5).fill(false);

            // #1 Analyse type
            this.formStepAnalyseType = undefined;

            // #2 Analyse parameter
            this.formStepAnalyseParameters = undefined;

            // #4 Patient
            this.formStepPatient = {
                id: undefined,
                fullName: {
                    firstname: '',
                    lastname: '',
                    patronymic: ''
                },
                bday: new Date(),
                address: ''
            };

            // #3 & #5 Image and Disease
            this.analyse = {
                additionalInfo: {
                    name: '',
                    patientId: undefined,
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
                },
                id: undefined,
                starred: undefined,
                analyseDate: undefined,
                geometricAnalyse: undefined,
                bloodFlowAnalyse: undefined,
                originalImage: {
                    filename: undefined,
                    url: undefined,
                    id: undefined
                }
            };
        }

        public navigateToListAnalyse() {
            this.$router.push({path: '/analyse'});
        }

        get mapErrorMessages() {
            return this.errorMessages.map(message => this.$t(message))
        }

        get hasErrorMessages() {
            return this.errorMessages.length > 0;
        }
    }
</script>
