<template>
    <v-layout row>
        <v-flex xs12>
            <v-card>
                <v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <DeleteAnalyseButton
                                v-if="hasPermissions(['ANALYSE_REMOVE'])"
                                v-bind:analyse-id="analyse.id"
                                v-bind:delete-analyse="deleteAnalyse"
                                class="ma-0 pa-0"
                        ></DeleteAnalyseButton>
                        <EditAnalyseButton
                                v-if="hasPermissions(['ANALYSE_EDIT'])"
                                v-bind:analyse-id="analyse.id"
                                v-bind:analyse-additional-info="analyse.additionalInfo"
                                v-bind:edit-analyse-additional-info="editAnalyseAdditionalInfo"
                                class="ma-0 pa-0"
                        ></EditAnalyseButton>
                        <v-checkbox
                                v-bind:input-value="starred"
                                v-on:click.stop="setStarredAnalyse(analyse.id, !starred)"
                                color="yellow accent-3"
                                on-icon="star"
                                off-icon="star_border"
                                primary
                                hide-details
                                class="ma-0 pa-0"
                                style="flex: 0 auto"
                                flat
                                ripple
                        ></v-checkbox>
                        <PrintAnalyseButton
                                v-bind:analyse-id="analyse.id"
                        ></PrintAnalyseButton>
                        <DownloadArchiveAnalyseButton
                                v-bind:analyse-id="analyse.id"
                        ></DownloadArchiveAnalyseButton>
                        <v-spacer></v-spacer>
                    </v-card-actions>
                </v-card-text>
            </v-card>
        </v-flex>
    </v-layout>
</template>

<script lang="ts">
    import {UserPermission} from '@/modules/user/store/userState';
    import EditAnalyseButton from '@/modules/analyse/components/EditAnalyseButton.vue';
    import {Component, Prop, Vue} from "vue-property-decorator";
    import DeleteAnalyseButton from '@/modules/analyse/components/DeleteAnalyseButton.vue';
    import PrintAnalyseButton from '@/modules/analyse/components/PrintAnalyseButton.vue';
    import DownloadArchiveAnalyseButton from '@/modules/analyse/components/DownloadArchiveAnalyseButton.vue';
    import {Analyse, AnalyseAdditionalInfo} from '@/modules/analyse/models/analyse';

    @Component({
        components: {
            EditAnalyseButton, DeleteAnalyseButton, PrintAnalyseButton, DownloadArchiveAnalyseButton
        }
    })
    export default class AnalyseDetailsAdditionalActions extends Vue {

        @Prop()
        public analyse!: Analyse;

        @Prop()
        public readonly starred!: boolean;

        @Prop()
        public readonly hasPermissions!: (permissions: UserPermission[]) => boolean;

        @Prop()
        public readonly deleteAnalyse!: (id: number) => Promise<void>;

        @Prop()
        public readonly setStarredAnalyse!: (id: number, starred: boolean) => void;

        @Prop()
        public readonly editAnalyseAdditionalInfo!: (id: number, analyseAdditionalInfo: AnalyseAdditionalInfo) => Promise<void>;
    }
</script>
