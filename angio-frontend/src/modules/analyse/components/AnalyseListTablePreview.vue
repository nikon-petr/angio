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
                        <v-img
                                v-bind:src="originalImageUrl"
                                class="elevation-2"
                                max-height="250px"
                                max-width="250px"
                                aspect-ratio="1"
                                ma-0
                                pa-0
                        >
                            <template v-slot:placeholder>
                                <v-layout
                                        fill-height
                                        align-center
                                        justify-center
                                        ma-0
                                        pa-0
                                >
                                    <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                                </v-layout>
                            </template>
                        </v-img>
                    </v-layout>
                </v-flex>
                <v-flex xs12>
                    <v-divider class="py-1"></v-divider>
                    <v-btn
                            v-if="hasPermissions(['ANALYSE_REMOVE'])"
                            v-on:click="deleteAnalyseSafely"
                            class="ma-0"
                            color="error"
                            ripple
                            icon
                            flat
                    >
                        <v-icon>delete</v-icon>
                    </v-btn>
                    <v-tooltip bottom>
                        <template v-slot:activator="{ on }">
                            <v-btn
                                    v-if="status.type === 'SUCCESS'"
                                    v-on:click="printAnalyseReport(id)"
                                    v-on="on"
                                    class="ma-0"
                                    ripple
                                    icon
                                    flat
                            >
                                <v-icon>print</v-icon>
                            </v-btn>
                        </template>
                        <span>{{ $t('analyse.component.analyseListTablePreview.button.print.tooltip') }}</span>
                    </v-tooltip>
                    <v-tooltip bottom>
                        <template v-slot:activator="{ on }">
                            <v-btn
                                    v-if="status.type === 'SUCCESS'"
                                    v-on:click="downloadZipAnalyse"
                                    v-on="on"
                                    class="ma-0"
                                    ripple
                                    icon
                                    flat
                            >
                                <v-icon>archive</v-icon>
                            </v-btn>
                        </template>
                        <span>{{ $t('analyse.component.analyseListTablePreview.button.downloadZip.tooltip') }}</span>
                    </v-tooltip>
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
    import {ConfirmType} from '@/modules/analyse/helpers/confirm';

    @Component
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
        public readonly deleteAnalyse!: (id: number) => void;

        @Prop()
        public readonly printAnalyseReport!: (id: number) => void;

        public async deleteAnalyseSafely() {
            const title = this.$t('analyse.component.analyseListTablePreview.button.delete.confirm.title').toString();
            const text = this.$t('analyse.component.analyseListTablePreview.button.delete.confirm.text', [this.id]).toString();
            if(await this.$confirm(title, ConfirmType.DELETE, text)) {
                this.$logger.debug(`delete analyse #${this.id}`);
                this.deleteAnalyse(this.id);
            }
        }

        public downloadZipAnalyse() {
            this.$logger.debug(`download zip for analyse #${this.id}`)
        }

        public fullName(name: FullName): string {
            const patronymic: string = name.patronymic ? name.patronymic : '';
            return `${name.lastname} ${name.firstname} ${patronymic}`;
        }
    }
</script>
