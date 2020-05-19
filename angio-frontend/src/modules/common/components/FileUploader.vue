<template>
    <file-pond
            ref="pond"
            name="file"
            v-bind:allow-multiple="allowMultiple"
            v-bind:allow-image-preview="enablePreview"
            v-bind:accepted-file-types="acceptedFileTypes.join(', ')"
            v-bind:server="{process, revert,  restore, load, fetch}"

            v-bind:label-idle="$t('common.component.fileUploader.labelIdle')"
            v-bind:label-file-type-not-allowed="$t('common.component.fileUploader.labelFileTypeNotAllowed')"
            v-bind:file-validate-type-label-expected-types="$t('common.component.fileUploader.fileValidateTypeLabelExpectedTypes')"
            v-bind:label-max-file-size-exceeded="$t('common.component.fileUploader.labelMaxFileSizeExceeded')"
            v-bind:label-max-file-size="$t('common.component.fileUploader.labelMaxFileSize')"
            v-bind:label-max-total-file-size-exceeded="$t('common.component.fileUploader.labelMaxTotalFileSizeExceeded')"
            v-bind:label-max-total-file-size="$t('common.component.fileUploader.labelMaxTotalFileSize')"
            v-bind:label-invalid-field="$t('common.component.fileUploader.labelInvalidField')"
            v-bind:label-file-waiting-for-size="$t('common.component.fileUploader.labelFileWaitingForSize')"
            v-bind:label-file-size-not-available="$t('common.component.fileUploader.labelFileSizeNotAvailable')"
            v-bind:label-file-loading="$t('common.component.fileUploader.labelFileLoading')"
            v-bind:label-file-load-error="$t('common.component.fileUploader.labelFileLoadError')"
            v-bind:label-file-processing="$t('common.component.fileUploader.labelFileProcessing')"
            v-bind:label-file-processing-complete="$t('common.component.fileUploader.labelFileProcessingComplete')"
            v-bind:label-file-processing-aborted="$t('common.component.fileUploader.labelFileProcessingAborted')"
            v-bind:label-file-processing-error="$t('common.component.fileUploader.labelFileProcessingError')"
            v-bind:label-file-processing-revert-error="$t('common.component.fileUploader.labelFileProcessingRevertError')"
            v-bind:label-file-remove-error="$t('common.component.fileUploader.labelFileRemoveError')"
            v-bind:label-tap-to-cancel="$t('common.component.fileUploader.labelTapToCancel')"
            v-bind:label-tap-to-retry="$t('common.component.fileUploader.labelTapToRetry')"
            v-bind:label-tap-to-undo="$t('common.component.fileUploader.labelTapToUndo')"
            v-bind:label-button-remove-item="$t('common.component.fileUploader.labelButtonRemoveItem')"
            v-bind:label-button-abort-item-load="$t('common.component.fileUploader.labelButtonAbortItemLoad')"
            v-bind:label-button-retry-item-load="$t('common.component.fileUploader.labelButtonRetryItemLoad')"
            v-bind:label-button-abort-item-processing="$t('common.component.fileUploader.labelButtonAbortItemProcessing')"
            v-bind:label-button-undo-item-processing="$t('common.component.fileUploader.labelButtonUndoItemProcessing')"
            v-bind:label-button-retry-item-processing="$t('common.component.fileUploader.labelButtonRetryItemProcessing')"
            v-bind:label-button-process-item="$t('common.component.fileUploader.labelButtonProcessItem')"

            v-bind:allowFileSizeValidation="validateFileSize"
            v-bind:max-file-size="maxFileSize"
            v-bind:image-validate-size-min-width="imageMinWidth"
            v-bind:image-validate-size-max-width="imageMaxWidth"
            v-bind:image-validate-size-min-height="imageMinHeight"
            v-bind:image-validate-size-max-height="imageMaxHeight"
            v-bind:image-validate-size-min-resolution="imageMinResolution"
            v-bind:image-validate-size-max-resolution="imageMaxResolution"

            v-bind:allow-image-validate-size="validateImageSize"
            v-bind:image-validate-size-label-format-error="$t('common.component.fileUploader.imageValidateSizeLabelFormatError')"
            v-bind:image-validate-size-label-image-size-too-small="$t('common.component.fileUploader.imageValidateSizeLabelImageSizeTooSmall')"
            v-bind:image-validate-size-label-image-size-too-big="$t('common.component.fileUploader.imageValidateSizeLabelImageSizeTooBig')"
            v-bind:image-validate-size-label-expected-min-size="$t('common.component.fileUploader.imageValidateSizeLabelExpectedMinSize')"
            v-bind:image-validate-size-label-expected-max-size="$t('common.component.fileUploader.imageValidateSizeLabelExpectedMaxSize')"
            v-bind:image-validate-size-label-image-resolution-too-low="$t('common.component.fileUploader.imageValidateSizeLabelImageResolutionTooLow')"
            v-bind:image-validate-size-label-image-resolution-too-high="$t('common.component.fileUploader.imageValidateSizeLabelImageResolutionTooHigh')"
            v-bind:image-validate-size-label-expected-min-resolution="$t('common.component.fileUploader.imageValidateSizeLabelExpectedMinResolution')"
            v-bind:image-validate-size-label-expected-max-resolution="$t('common.component.fileUploader.imageValidateSizeLabelExpectedMaxResolution')"
    ></file-pond>
</template>

<script lang="ts">
    import {Component, Vue, Prop, Watch, Emit} from 'vue-property-decorator';
    import {FileUploadService} from '@/modules/common/service/fileUploadService';
    import {FileTypes} from '@/modules/common/helpers/fileTypes';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';

    @Component({})
    export default class FileUploader extends Vue {

        @Prop({default: () => []})
        public readonly acceptedFileTypes!: FileTypes[];

        @Prop({default: true})
        public readonly enablePreview!: boolean;

        @Prop({default: true})
        public readonly allowMultiple!: boolean;

        @Prop({default: true})
        public readonly validateFileSize!: boolean;

        @Prop({default: null})
        public readonly maxFileSize!: string;

        @Prop({default: false})
        public readonly validateImageSize!: boolean;

        @Prop({default: 1})
        public readonly imageMinWidth!: number;

        @Prop({default: 65535})
        public readonly imageMaxWidth!: number;

        @Prop({default: 1})
        public readonly imageMinHeight!: number;

        @Prop({default: 65535})
        public readonly imageMaxHeight!: number;

        @Prop({default: null})
        public readonly imageMinResolution!: number | null;

        @Prop({default: null})
        public readonly imageMaxResolution!: number | null;

        public fileId: string | null = null;

        @Watch('fileId')
        public onFileIdChange(newVal: string, oldVal: string) {
            this.fileUploaded(newVal);
        }

        @Emit(CommonEvent.CHANGE)
        public fileUploaded(fileId: string | null) {
            return fileId;
        }

        public process(fieldName: any, file: any, metadata: any, load: any, error: any, progress: any, abort: any) {
            this.$logger.debug('filepond process');

            const formData = new FormData();
            formData.append('multipartFile', file, file.name);

            const CancelToken = this.$axios.CancelToken;
            const source = CancelToken.source();

            FileUploadService.uploadImage(formData, progress)
                .then(response => {
                    load(response.data.data.id);
                    this.fileId = response.data.data.id;
                }).catch((error) => {
                    this.$logger.debug(error)
                });

            return {
                abort: () => {
                    source.cancel('Operation canceled');
                    abort()
                }
            }
        }

        public revert(uniqueFileId: any, load: any, error: any) {
            this.$logger.debug('filepond revert');
            this.fileUploaded(null);
            load();
        }

        public load(uniqueFileId: any, load: any, error: any){
            this.$logger.debug('filepond load');
            error()
        }

        public fetch(url: any, load: any, error: any, progress: any, abort: any, headers: any) {
            this.$logger.debug('filepond fetch');
            error()
        }

        restore(uniqueFileId: any, load: any, error: any, progress: any, abort: any, headers: any) {
            this.$logger.debug('filepond restore');
            error()
        }
    }
</script>

<style scoped>

</style>
