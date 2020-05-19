<template>
    <div class="pl-3">
        <v-checkbox
                v-on:change="onFormStepAnalyseTypeChanged"
                v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepChooseAnalyseParameter.data.geometric.tortuosityAndBranching')"
                v-model="formStepAnalyseParameters"
                v-if="isFormStepAnalyseTypeGeometric()"
                hide-details
                value="TORTUOSITY_AND_BRANCHING"
        ></v-checkbox>
        <v-checkbox
                v-on:change="onFormStepAnalyseTypeChanged"
                v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepChooseAnalyseParameter.data.geometric.ishemiaAndMacula')"
                v-model="formStepAnalyseParameters"
                v-if="isFormStepAnalyseTypeGeometric()"
                hide-details
                value="ISCHEMIA_AND_MACULA"
        ></v-checkbox>
        <v-checkbox
                v-on:change="onFormStepAnalyseTypeChanged"
                v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepChooseAnalyseParameter.data.geometric.density')"
                v-model="formStepAnalyseParameters"
                v-if="isFormStepAnalyseTypeGeometric()"
                hide-details
                value="DENSITY"
                class="mb-4"
        ></v-checkbox>
        <v-layout
                v-if="isFormStepAnalyseTypeProfile()"
                row
                wrap
                align-center
                class="mb-3"
        >
            <v-flex>
                <span class="subtitle-1">
                    {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepChooseAnalyseParameter.data.profile.inDevelopment') }}
                </span>
            </v-flex>
        </v-layout>
    </div>
</template>

<script lang="ts">
    import {Component, Emit, Prop, Vue} from 'vue-property-decorator';
    import {FormStepAnalyseParameter, FormStepAnalyseType} from '@/modules/analyse/helpers/formStep';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';
    import EventWithValidation from '@/modules/common/models/eventWithValidation';

    @Component({})
    export default class CreateAnalyseFormStepChooseAnalyseParameter extends Vue {

        @Prop()
        public readonly formStepAnalyseType!: FormStepAnalyseType | undefined;

        public formStepAnalyseParameters: FormStepAnalyseParameter[] = [];

        public isFormStepAnalyseTypeGeometric(): boolean {
            return this.formStepAnalyseType == FormStepAnalyseType.GEOMETRIC;
        }

        public isFormStepAnalyseTypeProfile(): boolean {
            return this.formStepAnalyseType == FormStepAnalyseType.PROFILE;
        }

        @Emit(CommonEvent.CHANGE)
        public onFormStepAnalyseTypeChanged(value: string): EventWithValidation<FormStepAnalyseParameter[]> {
            if (value.toString().length != 0) {
                this.formStepAnalyseParameters = value.toString().split(',').map(el => el as FormStepAnalyseParameter);
            } else {
                this.formStepAnalyseParameters = [];
            }

            if (this.formStepAnalyseType == FormStepAnalyseType.GEOMETRIC) {
                return {
                    isValid: this.formStepAnalyseParameters.length > 0,
                    payload: this.formStepAnalyseParameters
                };
            } else {
                return {
                    isValid: false,
                    payload: []
                };
            }
        }
    }
</script>
