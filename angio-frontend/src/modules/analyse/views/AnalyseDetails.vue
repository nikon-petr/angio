<template>
    <StackLayout>
        <v-flex xs12>
            <v-container
                    fluid
                    fill-height
                    justify-center
                    align-center
                    v-if="fetching"
            >
                <v-progress-circular :indeterminate="true" :background-opacity="0" pa-0 ma-0></v-progress-circular>
            </v-container>
            <AnalyseDetailsTitle
                    v-if="!fetching"
                    v-bind:name="analyse && analyse.additionalInfo && analyse.additionalInfo.name"
                    v-bind:analyse-id="analyseId"
                    v-bind:patient="patient"
            ></AnalyseDetailsTitle>
            <AnalyseDetailsAdditionalInfo
                    v-if="!fetching"
                    v-bind:analyse="analyse"
            ></AnalyseDetailsAdditionalInfo>
            <AnalyseDetailsAdditionalActions
                    v-if="!fetching"
                    v-bind:analyse="analyse"
                    v-bind:starred="starred"
                    v-bind:has-permissions="hasPermissions"
                    v-bind:delete-analyse="deleteAnalyse"
                    v-bind:set-starred-analyse="setStarredAnalyse"
                    v-bind:edit-analyse-additional-info="editAnalyseAdditionalInfo"
            ></AnalyseDetailsAdditionalActions>
            <AnalyseTabsContainer
                    v-if="!fetching"
                    v-bind:analyse="analyse"
                    v-bind:has-permissions="hasPermissions"
                    v-bind:delete-vessel="deleteVessel"
            ></AnalyseTabsContainer>
        </v-flex>
    </StackLayout>
</template>

<script lang="ts">
    import {Component, Vue, Prop} from 'vue-property-decorator';
    import StackLayout from '@/modules/common/components/StackLayout.vue';
    import AnalyseDetailsTitle from '@/modules/analyse/components/AnalyseDetailsTitle.vue';
    import {Analyse, AnalyseAdditionalInfo} from '@/modules/analyse/models/analyse';
    import {AnalyseApiService} from '@/modules/analyse/services/analyseApiService';
    import {PatientApiService} from '@/modules/patient/services/patientApiService';
    import {Patient} from '@/modules/patient/models/patient';
    import AnalyseDetailsAdditionalInfo from '@/modules/analyse/components/AnalyseDetailsAdditionalInfo.vue';
    import {UserPermission} from '@/modules/user/store/userState';
    import {UserGetter} from '@/modules/user/store/userStore';
    import {Getter} from 'vuex-class';
    import {debounce} from 'helpful-decorators';
    import AnalyseDetailsAdditionalActions from '@/modules/analyse/components/AnalyseDetailsAdditionalActions.vue';
    import AnalyseTabsContainer from '@/modules/analyse/components/AnalyseTabsContainer.vue';

    @Component({
        components: {
            AnalyseDetailsAdditionalInfo, AnalyseDetailsTitle, StackLayout, AnalyseDetailsAdditionalActions,
            AnalyseTabsContainer
        }
    })
    export default class AnalyseDetails extends Vue {

        @Prop()
        public analyseId!: number;

        @Getter(UserGetter.HAS_PERMISSIONS)
        public readonly hasPermissions!: (permissions: UserPermission[]) => boolean;

        public fetching: boolean = false;

        public analyse?: Analyse;

        public starred: boolean = false;

        public patient?: Patient;

        public data() {
            return {
                analyse: undefined,
                patient: undefined
            }
        }

        public created() {
            this.fetching = true;
            AnalyseApiService.getAnalyseById(this.analyseId)
                .then((analyseRes) => {
                    this.analyse = analyseRes.data.data;

                    PatientApiService.getPatientById(this.analyse.additionalInfo.patientId)
                        .then((res) => {
                            this.patient = res.data.data;

                            AnalyseApiService.getAnalyseStarred(this.analyseId)
                                .then((starredRes) => {
                                    if (this.analyse != undefined) {
                                        this.analyse.starred = starredRes.data.data.starred;
                                        this.starred = starredRes.data.data.starred;
                                    }
                                })
                                .catch((error) => this.$logger.error(error))
                                .finally(() => {
                                    this.fetching = false;
                                    this.setDocumentTitle();
                                })
                        })
                        .catch((error) => this.$logger.error(error))
                })
                .catch((error) => this.$logger.error(error))
        }

        public setDocumentTitle() {
            if (this.patient && this.patient.fullName && this.analyse) {
                const firstname: string = this.patient.fullName.firstname ? this.patient.fullName.firstname.substr(0, 1) : '';
                const patronymic: string = this.patient.fullName.patronymic ? this.patient.fullName.patronymic.substr(0, 1) : '';
                document.title = `${this.analyse.additionalInfo.name} - ${this.patient.fullName.lastname}\u00A0${firstname}.${patronymic}. | AngioVision`;
            }
        }

        public async deleteAnalyse(id: number): Promise<void> {
            return new Promise<void>(async (resolve) => {
                await AnalyseApiService
                    .deleteAnalyse(id)
                    .then(() => {
                        this.$router.back()
                    })
                    .catch((error) => this.$logger.error(error));
                resolve();
            });
        }

        @debounce(500)
        public setStarredAnalyse(id: number, starred: boolean) {
            if (this.analyse) {
                this.starred = starred;
                this.analyse.starred = starred;
            }
            AnalyseApiService
                .setAnalyseStarred(id, starred)
                .then((response) => {
                    console.log("Result of set analyse starred: " + response.data.data.starred);
                })
                .catch((error) => this.$logger.error(error));
        }

        public editAnalyseAdditionalInfo(id: number, analyseAdditionalInfo: AnalyseAdditionalInfo): Promise<void> {
            return new Promise<void>(async (resolve) => {
                await AnalyseApiService
                    .editAnalyseAdditionalInfo(id, analyseAdditionalInfo)
                    .then((response) => {
                        this.analyse = response.data.data;
                        this.analyse.starred = response.data.data.starred;
                        console.log("Result of editing analyse additional info: " + response.data.data.additionalInfo);
                    });
                    resolve();
            });
        }

        public deleteVessel(analyseId: number, vesselId: number) {
            return new Promise<void>(async (resolve) => {
                await AnalyseApiService
                    .deleteVessel(analyseId, vesselId)
                    .then(() => {
                        if (this.analyse) {
                            this.analyse.geometricAnalyse.vessels
                                = this.analyse.geometricAnalyse.vessels.filter(vessel => vessel.id != vesselId);
                        }
                    })
                    .catch((error) => this.$logger.error(error));
                resolve();
            });
        }
    }
</script>
