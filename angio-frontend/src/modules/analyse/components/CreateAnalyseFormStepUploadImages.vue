<template>
    <div v-if="isFormStepAnalyseTypeGeometric()">
        <span class="subtitle-1">
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepImage.data.content1') }}<br>
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepImage.data.content2') }}<br>
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepImage.data.content3') }}<br>
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepImage.data.content4') }}<br><br>
        </span>
        <FileUploader
                v-bind:accepted-file-types="acceptedImageTypes"
                v-bind:allow-multiple="false"
                v-on:change="onFileIdChanged"
        ></FileUploader>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import FileUploader from '@/modules/common/components/FileUploader.vue';
    import {FormStepAnalyseType} from '@/modules/analyse/models/analyse';
    import {FileTypes} from '@/modules/common/helpers/fileTypes';
    import {throttle} from 'helpful-decorators';

    @Component({
        components: {FileUploader}
    })
    export default class CreateAnalyseFormStepUploadImages extends Vue {

        @Prop()
        public readonly formStepAnalyseType!: number;

        @Prop()
        public readonly onFormStepAnalyseImageUploaded!: (fileId: string) => void;

        @throttle(1000)
        public onFileIdChanged(fileId: string) {
            this.onFormStepAnalyseImageUploaded(fileId)
        }

        public readonly acceptedImageTypes = [
            FileTypes.JPG.toString(),
            FileTypes.JPEG.toString(),
            FileTypes.BMP.toString(),
            FileTypes.PNG.toString()
        ];

        public isFormStepAnalyseTypeGeometric(): boolean {
            return this.formStepAnalyseType == FormStepAnalyseType.GEOMETRIC;
        }

        public isFormStepAnalyseTypeProfile(): boolean {
            return this.formStepAnalyseType == FormStepAnalyseType.PROFILE;
        }
    }
</script>
