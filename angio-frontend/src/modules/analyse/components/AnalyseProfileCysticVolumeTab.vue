<template>
    <v-card flat>
        <v-card-text>
            <v-layout fill-height row wrap>
                <v-flex xs12 class="text-xs-center">
                    <p class="headline pb-0 mb-0">
                        {{ $t('analyse.component.details.analyseTabs.profileCysticVolume.images.title') }}
                    </p>
                </v-flex>
                <v-flex xs6 class="text-xs-center">
                    <p class="subtitle-1">
                        {{ $t('analyse.component.details.analyseTabs.profileCysticVolume.images.angio') }}
                    </p>
                    <v-layout
                            align-center
                            justify-center
                            ma-0
                            pa-0
                    >
                        <AnalyseListTablePreviewImage
                                v-bind:src="cysticVolume.angiogramImage.url"
                                v-bind:max-size="500"
                        ></AnalyseListTablePreviewImage>
                    </v-layout>
                </v-flex>
                <v-flex xs6 class="text-xs-center">
                    <p class="subtitle-1">
                        {{ $t('analyse.component.details.analyseTabs.profileCysticVolume.images.profile') }}
                    </p>
                    <v-layout
                            align-center
                            justify-center
                            ma-0
                            pa-0
                    >
                        <AnalyseListTablePreviewImage
                                v-bind:src="cysticVolume.profileImage.url"
                                v-bind:max-size="500"
                        ></AnalyseListTablePreviewImage>
                    </v-layout>
                </v-flex>
                <v-flex xs12 class="text-xs-center">
                    <p class="headline pb-0 mb-0">
                        {{ $t('analyse.component.details.analyseTabs.profileCysticVolume.description.title') }}
                    </p>
                </v-flex>
                <v-flex xs12>
                    <p class="subtitle-1 pb-0 mb-0">
                        {{ $t('analyse.component.details.analyseTabs.profileCysticVolume.description.commonInfo') }}
                    </p>
                </v-flex>
                <v-flex xs12>
                    <p class="headline pb-0 mb-0">
                        {{ $t('analyse.component.details.analyseTabs.profileCysticVolume.description.result') }}
                    </p>

                    <p v-if="isCysticVolumeFound()" class="subtitle-1 pb-0 mb-0">
                        {{ $t('analyse.component.details.analyseTabs.profileCysticVolume.description.cysticFound', [cysticVolume.cysticVolume]) }}
                    </p>

                    <p v-if="!isCysticVolumeFound()" class="subtitle-1 pb-0 mb-0">
                        {{ $t('analyse.component.details.analyseTabs.profileCysticVolume.description.cysticNotFound') }}
                    </p>
                </v-flex>
            </v-layout>
        </v-card-text>
    </v-card>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import {CysticVolume} from '@/modules/analyse/models/analyse';
    import AnalyseListTablePreviewImage from '@/modules/analyse/components/AnalysePreviewImage.vue';

    @Component({
        components: {AnalyseListTablePreviewImage}
    })
    export default class AnalyseProfileCysticVolumeTab extends Vue {

        @Prop()
        public readonly cysticVolume!: CysticVolume;

        public isCysticVolumeFound(): boolean {
            return this.cysticVolume.cysticVolume > 0;
        }
    }
</script>
