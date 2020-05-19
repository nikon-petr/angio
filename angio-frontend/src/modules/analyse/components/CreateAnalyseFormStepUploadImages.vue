<template>
    <div>
        <span class="subtitle-1">
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepImage.data.content1') }}<br>
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepImage.data.content2') }}<br>
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepImage.data.content3') }}<br>
            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepImage.data.content4') }}<br><br>
        </span>
        <FileUploader
                v-on:change="onFileIdChanged"
                v-bind:accepted-file-types="acceptedImageTypes"
                v-bind:allow-multiple="false"
        ></FileUploader>
    </div>
</template>

<script lang="ts">
    import {Component, Emit, Vue} from 'vue-property-decorator';
    import FileUploader from '@/modules/common/components/FileUploader.vue';
    import {FileTypes} from '@/modules/common/helpers/fileTypes';
    import EventWithValidation from '@/modules/common/models/eventWithValidation';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';

    @Component({
        components: {FileUploader}
    })
    export default class CreateAnalyseFormStepUploadImages extends Vue {

        @Emit(CommonEvent.CHANGE)
        public onFileIdChanged(fileId: string | null): EventWithValidation<string> {
            return {
                isValid: fileId != null,
                payload: fileId == null ? undefined : fileId
            };
        }

        public readonly acceptedImageTypes = [
            FileTypes.BMP,
            FileTypes.PNG
        ];
    }
</script>
