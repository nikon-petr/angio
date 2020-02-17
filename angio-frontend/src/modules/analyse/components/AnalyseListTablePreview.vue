<template lang="html">
    <v-card flat>
        <v-card-text class="px-4">
            <v-layout row wrap>
                <v-flex xs12 sm6 md6 lg8 xl8>
                    <v-layout
                            column
                            justify-space-between
                            fill-height
                            pa-0
                            ma-0
                    >
                        <div class="subheading">
                            <div>
                                <span class="font-weight-medium text--secondary">
                                    {{ $t('analyse.component.analyseListTablePreview.field.diagnostician') }}
                                </span>{{ fullName(diagnostician) }}
                            </div>
                            <div>
                                <span class="font-weight-medium text--secondary">
                                    {{ $t('analyse.component.analyseListTablePreview.field.patient') }}
                                </span>{{ fullName(patient) }}
                            </div>
                            <div>
                                <span class="font-weight-medium text--secondary">
                                    {{ $t('analyse.component.analyseListTablePreview.field.name') }}
                                </span>{{ name }}
                            </div>
                            <div>
                                <span class="font-weight-medium text--secondary">
                                    {{ $t('analyse.component.analyseListTablePreview.field.shortDescription') }}
                                </span>{{ shortDescription }}
                            </div>
                            <div>
                                <span class="font-weight-medium text--secondary">
                                    {{ $t('analyse.component.analyseListTablePreview.field.analyseType') }}
                                </span>{{ $t('analyse.model.analyseType.' + analyseType) }}
                            </div>
                            <div><br/></div>
                            <div>
                                <span class="font-weight-medium text--secondary">
                                    {{ $t('analyse.component.analyseListTablePreview.field.statusType') }}
                                </span>{{ $t('analyse.model.status.' + status.type) }}
                            </div>
                            <div v-if="status.extension">
                                <span class="font-weight-medium text--secondary">
                                    {{ $t('analyse.component.analyseListTablePreview.field.statusExtension') }}
                                </span>{{ status.extension }}
                            </div>
                        </div>
                    </v-layout>
                </v-flex>
                <v-flex xs12 sm6 md6 lg4 xl4 v-if="$vuetify.breakpoint.mdAndUp">
                    <v-layout
                            justify-end
                            pa-0
                            ma-0
                    >
                        <AnalyseListTablePreviewImage
                                v-bind:src="originalImageUrl"
                        ></AnalyseListTablePreviewImage>
                    </v-layout>
                </v-flex>
                <v-flex xs12>
                    <v-divider class="pb-1"></v-divider>
                    <DeleteAnalyseButton
                            v-if="hasPermissions(['ANALYSE_REMOVE'])"
                            v-bind:analyse-id="id"
                            v-bind:delete-analyse="deleteAnalyse"
                    ></DeleteAnalyseButton>
                    <PrintAnalyseButton
                            v-if="status.type === 'SUCCESS'"
                            v-bind:analyse-id="id"
                    ></PrintAnalyseButton>
                    <DownloadArchiveAnalyseButton
                            v-if="status.type === 'SUCCESS'"
                            v-bind:analyse-id="id"
                    ></DownloadArchiveAnalyseButton>
                    <v-btn
                            v-if="status.type === 'SUCCESS'"
                            v-bind:to="`/analyse/${id}`"
                            class="ma-0"
                            color="accent"
                            ripple
                            round
                            flat
                    >
                        {{ $t('analyse.component.analyseListTablePreview.button.details.name') }}
                    </v-btn>
                </v-flex>
            </v-layout>
        </v-card-text>
    </v-card>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import FullName from '@/modules/common/models/fullName';
    import {AnalyseStatus} from '@/modules/analyse/models/analyse';
    import {UserPermission} from '@/modules/user/store/userState';
    import PrintAnalyseButton from '@/modules/analyse/components/PrintAnalyseButton.vue';
    import DownloadArchiveAnalyseButton from '@/modules/analyse/components/DownloadArchiveAnalyseButton.vue';
    import DeleteAnalyseButton from '@/modules/analyse/components/DeleteAnalyseButton.vue';
    import AnalyseListTablePreviewImage from '@/modules/analyse/components/AnalyseListTablePreviewImage.vue';

    @Component({
        components: {AnalyseListTablePreviewImage, DeleteAnalyseButton, DownloadArchiveAnalyseButton, PrintAnalyseButton}
    })
    export default class AnalyseListTablePreview extends Vue {

        @Prop()
        public readonly id!: number;

        @Prop()
        public readonly diagnostician!: FullName;

        @Prop()
        public readonly patient!: FullName;

        @Prop()
        public readonly name!: string;

        @Prop()
        public readonly shortDescription!: string;

        @Prop()
        public readonly analyseType!: string;

        @Prop()
        public readonly status!: AnalyseStatus;

        @Prop()
        public readonly originalImageUrl!: string;

        @Prop()
        public readonly hasPermissions!: (permissions: UserPermission[]) => boolean;

        @Prop()
        public readonly deleteAnalyse!: (id: number) => Promise<void>;

        public fullName(name: FullName): string {
            const patronymic: string = name.patronymic ? name.patronymic : '';
            return `${name.lastname} ${name.firstname} ${patronymic}`;
        }
    }
</script>
