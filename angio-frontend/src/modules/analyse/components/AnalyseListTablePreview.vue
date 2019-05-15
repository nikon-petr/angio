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
                        <div>
                            <v-spacer></v-spacer>
                            <v-btn
                                    v-if="hasPermissions(['ANALYSE_REMOVE'])"
                                    v-on:click="deleteAnalyse"
                                    class="ma-0"
                                    color="error"
                                    ripple
                                    icon
                                    flat
                            >
                                <v-icon>delete</v-icon>
                            </v-btn>
                            <v-btn
                                    class="ma-0"
                                    color="accent"
                                    v-bind:to="`/analyse/${id}`"
                                    ripple
                                    round
                                    flat
                            >
                                {{ $t('analyse.component.analyseListTablePreview.button.details') }}
                            </v-btn>
                        </div>
                    </v-layout>
                </v-flex>
                <v-flex xs12 sm6 md6 lg4 xl4>
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

        public async deleteAnalyse() {
            if(await this.$confirm('Удалить анализ', ConfirmType.DELETE, `Вы уверены, что хотите удалить анализ №${this.id}?`)) {
                // TODO: rest api call
                this.$logger.debug(`delete analyse ${this.id}`);
            }
        }

        public fullName(name: FullName): string {
            const patronymic: string = name.patronymic ? name.patronymic : '';
            return `${name.lastname} ${name.firstname} ${patronymic}`;
        }
    }
</script>
