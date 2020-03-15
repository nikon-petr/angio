import Vue from 'vue';
// @ts-ignore
import vueFilePond from 'vue-filepond';

import 'filepond/dist/filepond.min.css';
import 'filepond-plugin-image-preview/dist/filepond-plugin-image-preview.min.css';

// @ts-ignore
import FilePondPluginFileValidateType from 'filepond-plugin-file-validate-type';
// @ts-ignore
import FilePondPluginImagePreview from 'filepond-plugin-image-preview';
// @ts-ignore
import FilePondPluginFileValidateSize from 'filepond-plugin-file-validate-size';
// @ts-ignore
import FilePondPluginImageValidateSize from 'filepond-plugin-image-validate-size';


const FilePond = vueFilePond(
    FilePondPluginFileValidateType,
    FilePondPluginImagePreview,
    FilePondPluginFileValidateSize,
    FilePondPluginImageValidateSize
);

Vue.component('file-pond', FilePond);
