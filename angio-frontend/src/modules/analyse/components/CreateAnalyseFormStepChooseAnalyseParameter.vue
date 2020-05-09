<template>
    <div>
        <v-checkbox
                hide-details
                v-model="formStepAnalyseParameters"
                v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepChooseAnalyseParameter.data.geometric.tortuosityAndBranching')"
                value="TORTUOSITY_AND_BRANCHING"
                v-if="isFormStepAnalyseTypeGeometric()"
        ></v-checkbox>
        <v-checkbox
                hide-details
                v-model="formStepAnalyseParameters"
                v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepChooseAnalyseParameter.data.geometric.ishemiaAndMacula')"
                value="ISCHEMIA_AND_MACULA"
                v-if="isFormStepAnalyseTypeGeometric()"
        ></v-checkbox>
        <v-checkbox
                hide-details
                v-model="formStepAnalyseParameters"
                v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepChooseAnalyseParameter.data.geometric.density')"
                value="DENSITY"
                v-if="isFormStepAnalyseTypeGeometric()"
                class="mb-4"
        ></v-checkbox>
        <v-layout
                row
                wrap
                align-center
                v-if="isFormStepAnalyseTypeProfile()"
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
    import {Component, Prop, Vue, Watch} from 'vue-property-decorator';
    import {FormStepAnalyseParameter, FormStepAnalyseType} from '@/modules/analyse/models/analyse';

    @Component({})
    export default class CreateAnalyseFormStepChooseAnalyseParameter extends Vue {

        @Prop()
        public readonly formStepAnalyseType!: number;

        @Prop()
        public readonly onFormStepAnalyseParametersChanged!: (formStepAnalyseParameters: FormStepAnalyseParameter[]) => void;

        public formStepAnalyseParameters: string[] = [];

        public isFormStepAnalyseTypeGeometric(): boolean {
            return this.formStepAnalyseType == FormStepAnalyseType.GEOMETRIC;
        }

        public isFormStepAnalyseTypeProfile(): boolean {
            return this.formStepAnalyseType == FormStepAnalyseType.PROFILE;
        }

        @Watch('formStepAnalyseParameters')
        public onFormStepAnalyseTypeChangedWatcher(newVal: string[], oldVal: string[]) {
            this.onFormStepAnalyseParametersChanged(
                newVal.map((p) => {
                    switch (p) {
                        case "TORTUOSITY_AND_BRANCHING":
                            return FormStepAnalyseParameter.TORTUOSITY_AND_BRANCHING;
                        case "ISCHEMIA_AND_MACULA":
                            return FormStepAnalyseParameter.ISCHEMIA_AND_MACULA;
                        default:
                            return FormStepAnalyseParameter.DENSITY;
                    }
                })
            );
        }

        public resetAll() {
            this.formStepAnalyseParameters = [];
        }
    }
</script>
