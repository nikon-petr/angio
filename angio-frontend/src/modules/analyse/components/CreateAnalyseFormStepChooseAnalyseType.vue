<template>
    <v-radio-group v-model="formStepAnalyseType" column>
        <v-radio
                v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepChooseAnalyseType.data.geometric')"
                value="GEOMETRIC"
        ></v-radio>
        <v-radio
                v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepChooseAnalyseType.data.profile')"
                value="PROFILE"
        ></v-radio>
    </v-radio-group>
</template>

<script lang="ts">
    import {Component, Prop, Vue, Watch} from 'vue-property-decorator';
    import {FormStepAnalyseType} from '@/modules/analyse/models/analyse';

    @Component({})
    export default class CreateAnalyseFormStepChooseAnalyseType extends Vue {

        public formStepAnalyseType: string = "";

        @Prop()
        public readonly onFormStepAnalyseTypeChanged!: (formStepAnalyseType: FormStepAnalyseType) => void;

        @Watch('formStepAnalyseType')
        public onFormStepAnalyseTypeChangedWatcher(newVal: string, oldVal: string) {
            if (newVal == "GEOMETRIC") {
                this.onFormStepAnalyseTypeChanged(FormStepAnalyseType.GEOMETRIC);
            } else if (newVal == "PROFILE") {
                this.onFormStepAnalyseTypeChanged(FormStepAnalyseType.PROFILE);
            }
        }

        public resetAll() {
            this.formStepAnalyseType = "";
        }
    }
</script>
