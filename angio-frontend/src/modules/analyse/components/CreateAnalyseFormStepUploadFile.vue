<template>
    <div>
        <span
                v-if="formStepAnalyseType == 'GEOMETRIC'"
                class="subtitle-1"
        >
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepFile.angioData.content1') }}<br>
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepFile.angioData.content2') }}<br>
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepFile.angioData.content3') }}<br>
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepFile.angioData.content4') }}<br><br>
        </span>
        <span
                v-if="formStepAnalyseType == 'PROFILE'"
                class="subtitle-1"
        >
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepFile.profileData.content1') }}<br>
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepFile.profileData.content2') }}<br>
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepFile.profileData.content3') }}<br><br>
        </span>
        <FileUploader
                v-on:change="onFileIdChanged"
                v-bind:accepted-file-types="acceptedFileTypes"
                v-bind:allow-multiple="false"
        ></FileUploader>
    </div>
</template>

<script lang="ts">
    import {FormStepAnalyseType} from '@/modules/analyse/helpers/formStep';
    import {Component, Emit, Prop, Vue} from 'vue-property-decorator';
    import FileUploader from '@/modules/common/components/FileUploader.vue';
    import {FileTypes} from '@/modules/common/helpers/fileTypes';
    import EventWithValidation from '@/modules/common/models/eventWithValidation';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';

    @Component({
        components: {FileUploader}
    })
    export default class CreateAnalyseFormStepUploadFile extends Vue {

        @Prop()
        public readonly acceptedFileTypes!: FileTypes[];

        @Prop()
        public readonly formStepAnalyseType: FormStepAnalyseType | undefined;

        @Emit(CommonEvent.CHANGE)
        public onFileIdChanged(fileId: string | null): EventWithValidation<string> {
            return {
                isValid: fileId != null,
                payload: fileId == null ? undefined : fileId
            };
        }
    }
</script>
