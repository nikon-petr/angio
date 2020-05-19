<template>
    <v-form
            v-model="valid"
            ref="form"
    >
        <v-container>
            <v-layout row wrap>
                <v-flex xs4>
                    <v-select
                            v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepDisease.data.analyseType.field')"
                            v-bind:items="analyseTypes"
                            v-model="analyseAdditionalInfo.type"
                            item-text="text"
                            item-value="value"
                            required
                            persistent-hint
                            outline
                    ></v-select>
                </v-flex>

                <v-flex xs8>
                    <v-text-field
                            v-bind:label="$t('analyse.component.details.editForm.name.field')"
                            v-bind:rules="[v => !!v || $t('analyse.component.createAnalyseFormDialog.stepper.stepDisease.data.name.validation.NotEmpty'),
                                            v => v.length >= 5 || $t('analyse.component.createAnalyseFormDialog.stepper.stepDisease.data.name.validation.MinLength')]"
                            v-model="analyseAdditionalInfo.name"
                            type="text"
                            name="name"
                            maxlength="75"
                            browser-autocomplete="off"
                            required
                            outline
                    ></v-text-field>
                </v-flex>

                <v-flex xs12>
                    <v-text-field
                            v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepDisease.data.shortDescription.field')"
                            v-bind:rules="[v => !!v || $t('analyse.component.createAnalyseFormDialog.stepper.stepDisease.data.shortDescription.validation.NotEmpty'),
                                            v => v.length >= 5 || $t('analyse.component.createAnalyseFormDialog.stepper.stepDisease.data.shortDescription.validation.MinLength')]"
                            v-model="analyseAdditionalInfo.shortDescription"
                            type="text"
                            name="shortDescription"
                            maxlength="100"
                            browser-autocomplete="off"
                            required
                            outline
                    ></v-text-field>
                </v-flex>

                <v-flex xs12>
                    <v-textarea
                            v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepDisease.data.fullDescription.field')"
                            v-model="analyseAdditionalInfo.fullDescription"
                            type="text"
                            name="fullDescription"
                            counter
                            maxlength="1000"
                            clearable
                            browser-autocomplete="off"
                            outline
                    ></v-textarea>
                </v-flex>

                <v-flex xs12>
                    <v-textarea
                            v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepDisease.data.conclusion.field')"
                            v-model="analyseAdditionalInfo.conclusion"
                            type="text"
                            name="conclusion"
                            counter
                            maxlength="1000"
                            clearable
                            browser-autocomplete="off"
                            outline
                    ></v-textarea>
                </v-flex>

                <v-flex xs12>
                    <v-text-field
                            v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepDisease.data.comment.field')"
                            v-model="analyseAdditionalInfo.comment"
                            type="text"
                            name="comment"
                            maxlength="125"
                            clearable
                            browser-autocomplete="off"
                            outline
                    ></v-text-field>
                </v-flex>
            </v-layout>
        </v-container>
    </v-form>
</template>

<script lang="ts">
    import {Component, Emit, Vue, Watch} from 'vue-property-decorator';
    import {AnalyseAdditionalInfo, AnalyseType} from '@/modules/analyse/models/analyse';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';
    import EventWithValidation from '@/modules/common/models/eventWithValidation';

    @Component({})
    export default class CreateAnalyseFormStepDisease extends Vue {

        public analyseAdditionalInfo: AnalyseAdditionalInfo = {
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
        };

        public analyseTypes = [
            {value: AnalyseType.PRIMARY, text: this.getAnalyseTypeName(AnalyseType.PRIMARY)},
            {value: AnalyseType.SUBSEQUENT, text: this.getAnalyseTypeName(AnalyseType.SUBSEQUENT)}
        ];

        public valid: boolean = false;

        @Watch('valid', {deep: true, immediate: true})
        public onValidChanged(newVal: boolean, oldVal: boolean) {
            this.onFormStepAnalyseDiseaseChanged(this.analyseAdditionalInfo);
        }

        @Watch('analyseAdditionalInfo', {deep: true, immediate: true})
        public onValidForm(newVal: AnalyseAdditionalInfo, oldVal: AnalyseAdditionalInfo) {
            this.onFormStepAnalyseDiseaseChanged(this.analyseAdditionalInfo);
        }

        @Emit(CommonEvent.CHANGE)
        public onFormStepAnalyseDiseaseChanged(analyseAdditionalInfo: AnalyseAdditionalInfo): EventWithValidation<AnalyseAdditionalInfo> {
            return {
                isValid: this.valid,
                payload: analyseAdditionalInfo
            }
        }

        public getAnalyseTypeName(type: string): string {
            return this.$t('analyse.model.analyseType.' + type).toString()
        }
    }
</script>
