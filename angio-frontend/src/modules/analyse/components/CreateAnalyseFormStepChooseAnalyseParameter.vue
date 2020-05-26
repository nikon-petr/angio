<template>
    <div class="pl-3">
        <v-checkbox
                v-if="isFormStepAnalyseTypeGeometric()"
                v-model="executionConfigurationSynced.geometric"
                v-bind:disabled="executionConfigurationSynced.opticDiskBloodFlow"
                v-on:change="onFormStepAnalyseTypeChanged"
                v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepChooseAnalyseParameter.data.geometric.tortuosityAndBranching')"
                hide-details
        ></v-checkbox>
        <v-checkbox
                v-if="isFormStepAnalyseTypeGeometric()"
                v-model="executionConfigurationSynced.maculaBloodFlow"
                v-bind:disabled="executionConfigurationSynced.opticDiskBloodFlow"
                v-on:change="onFormStepAnalyseTypeChanged"
                v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepChooseAnalyseParameter.data.geometric.maculaBloodFlow')"
                hide-details
        ></v-checkbox>
        <v-checkbox
                v-if="isFormStepAnalyseTypeGeometric()"
                v-model="executionConfigurationSynced.opticDiskBloodFlow"
                v-bind:disabled="executionConfigurationSynced.geometric || executionConfigurationSynced.maculaBloodFlow"
                v-on:change="onFormStepAnalyseTypeChanged"
                v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepChooseAnalyseParameter.data.geometric.opticDiskBloodFlow')"
                hide-details
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
    import {ExecutionConfiguration} from '@/modules/analyse/models/analyse';
    import {Component, Emit, Prop, PropSync, Vue} from 'vue-property-decorator';
    import {FormStepAnalyseParameter, FormStepAnalyseType} from '@/modules/analyse/helpers/formStep';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';
    import EventWithValidation from '@/modules/common/models/eventWithValidation';

    @Component({})
    export default class CreateAnalyseFormStepChooseAnalyseParameter extends Vue {

        @Prop()
        public readonly formStepAnalyseType?: FormStepAnalyseType;

        @Prop()
        public readonly executionConfiguration!: ExecutionConfiguration;

        @PropSync('executionConfiguration')
        public executionConfigurationSynced!: ExecutionConfiguration;

        public formStepAnalyseParameters: FormStepAnalyseParameter[] = [];

        public isFormStepAnalyseTypeGeometric(): boolean {
            return this.formStepAnalyseType == FormStepAnalyseType.GEOMETRIC;
        }

        public isFormStepAnalyseTypeProfile(): boolean {
            return this.formStepAnalyseType == FormStepAnalyseType.PROFILE;
        }

        @Emit(CommonEvent.CHANGE)
        public onFormStepAnalyseTypeChanged(value: string): EventWithValidation<void> {
            if (this.formStepAnalyseType == FormStepAnalyseType.GEOMETRIC) {
                return {
                    isValid: this.executionConfigurationSynced.geometric
                        || this.executionConfigurationSynced.maculaBloodFlow
                        || this.executionConfigurationSynced.opticDiskBloodFlow
                };
            } else {
                return {
                    isValid: false
                };
            }
        }
    }
</script>
