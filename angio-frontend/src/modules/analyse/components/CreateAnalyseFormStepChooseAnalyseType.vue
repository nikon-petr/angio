<template>
    <v-radio-group
            v-on:change="onFormStepAnalyseTypeChanged"
            v-bind:value="formStepAnalyseType"
            column
            class="pl-3"
    >
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
    import {Component, Emit, Vue} from 'vue-property-decorator';
    import {FormStepAnalyseType} from '@/modules/analyse/helpers/formStep';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';
    import EventWithValidation from '@/modules/common/models/eventWithValidation';

    @Component({})
    export default class CreateAnalyseFormStepChooseAnalyseType extends Vue {

        public formStepAnalyseType: FormStepAnalyseType | undefined;

        @Emit(CommonEvent.CHANGE)
        public onFormStepAnalyseTypeChanged(value: string): EventWithValidation<FormStepAnalyseType> {
            this.formStepAnalyseType = value as FormStepAnalyseType;
            return {
                isValid: this.formStepAnalyseType != undefined,
                payload: this.formStepAnalyseType
            }
        }

        public data() {
            return {
                formStepAnalyseType: undefined
            }
        }
    }
</script>
