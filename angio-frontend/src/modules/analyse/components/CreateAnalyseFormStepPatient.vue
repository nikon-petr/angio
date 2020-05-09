<template>
    <v-layout row wrap>
        <v-flex xs12 class="ml-3">
            <v-switch
                    v-model="isAutoFill"
                    v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.switch')"
            ></v-switch>
        </v-flex>

        <v-flex xs-12 v-if="isAutoFill">
            <v-card>
                <v-card-title>
                    {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.searchTitle') }}
                    <v-spacer></v-spacer>
                    <v-text-field
                            v-model="searchField"
                            append-icon="mdi-magnify"
                            v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.searchField')"
                            single-line
                            hide-details
                            clearable
                            v-bind:disabled="!isAutoFill"
                    ></v-text-field>
                </v-card-title>
                <v-data-table
                        v-bind:headers="headers"
                        v-bind:items="patients"
                        v-bind:loading="fetching"
                        v-bind:pagination.sync="pagination"
                        v-bind:total-items="totalItems"
                        item-key="id"
                        v-bind:no-data-text="$t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.table.noDataText')"
                        sort-icon="arrow_drop_down"
                        disable-initial-sort
                        hide-actions
                >
                    <template slot="headerCell" slot-scope="props">
                        {{ $t(props.header.text) }}
                    </template>
                    <template v-slot:items="props">
                        <td>{{ props.item.fullName.lastname }}</td>
                        <td>{{ props.item.fullName.firstname }}</td>
                        <td>{{ props.item.fullName.patronymic }}</td>
                        <td>{{ props.item.bday | moment('DD.MM.YYYY') }}</td>
                        <td>{{ props.item.address }}</td>
                        <td class="center">
                            <v-btn
                                    class="ma-2"
                                    tile dark
                                    v-bind:disabled="!isAutoFill"
                                    v-on:click="onPatientSelected(props.item)"
                            >
                                {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.table.add') }}
                            </v-btn>
                        </td>
                    </template>
                </v-data-table>
            </v-card>
        </v-flex>

        <v-flex xs12 v-if="isAutoFill" class="pt-2">
            <AnalyseListPagination
                    v-bind:page.sync="page"
                    v-bind:rows-per-page="rowsPerPage"
                    v-bind:total-items="totalItems"
                    v-bind:disabled="!isAutoFill"
            ></AnalyseListPagination>
        </v-flex>

        <v-flex xs12 v-if="isAutoFill" class="pt-2 mb-2">
            <span class="font-weight-medium text--secondary">
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.form.selectedPatientInfo') }}
            </span>
            <span class="font-weight-medium subtitle-1">
                {{ printSelectedPatient() }}
            </span>
        </v-flex>

        <v-flex xs12 v-if="!isAutoFill">
            <v-form
                    ref="form"
            >
                <v-container>
                    <v-layout row wrap>
                        <v-flex xs4>
                            <v-text-field
                                    v-model="selectedPatient.fullName.lastname"
                                    v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.form.lastname.field')"
                                    v-bind:rules="[v => !!v || $t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.form.lastname.validation.NotEmpty')]"
                                    v-bind:disabled="isAutoFill"
                                    type="text"
                                    name="name"
                                    counter
                                    maxlength="30"
                                    browser-autocomplete="off"
                                    required
                            ></v-text-field>
                        </v-flex>
                        <v-flex xs4>
                            <v-text-field
                                    v-model="selectedPatient.fullName.firstname"
                                    v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.form.firstname.field')"
                                    v-bind:rules="[v => !!v || $t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.form.firstname.validation.NotEmpty')]"
                                    v-bind:disabled="isAutoFill"
                                    type="text"
                                    name="name"
                                    counter
                                    maxlength="30"
                                    browser-autocomplete="off"
                                    required
                            ></v-text-field>
                        </v-flex>
                        <v-flex xs4>
                            <v-text-field
                                    v-model="selectedPatient.fullName.patronymic"
                                    v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.form.patronymic.field')"
                                    v-bind:disabled="isAutoFill"
                                    type="text"
                                    name="name"
                                    counter
                                    maxlength="30"
                                    browser-autocomplete="off"
                            ></v-text-field>
                        </v-flex>
                        <v-flex xs8>
                            <v-text-field
                                    v-model="selectedPatient.address"
                                    v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.form.address.field')"
                                    v-bind:rules="[v => !!v || $t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.form.address.validation.NotEmpty')]"
                                    v-bind:disabled="isAutoFill"
                                    type="text"
                                    name="name"
                                    counter
                                    maxlength="100"
                                    browser-autocomplete="off"
                                    required
                            ></v-text-field>
                        </v-flex>
                        <v-flex xs4>
                            <RequiredBirthdaySingleDatePicker
                                    v-bind:date="selectedPatient.bday"
                                    v-bind:locale="locale"
                                    v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.form.bday.field')"
                                    v-bind:disabled="isAutoFill"
                                    v-on:change="(e) => selectedPatient.bday = e"
                            ></RequiredBirthdaySingleDatePicker>
                        </v-flex>
                    </v-layout>
                </v-container>
            </v-form>
        </v-flex>
    </v-layout>
</template>

<script lang="ts">
    import {Component, Emit, Prop, Ref, Vue, Watch} from 'vue-property-decorator';
    import {Patient} from '@/modules/patient/models/patient';
    import {PatientApiService} from '@/modules/patient/services/patientApiService';
    import Pagination from '@/modules/common/helpers/pagination';
    import {SortingDirection} from '@/modules/common/models/page';
    import AnalyseListPagination from '@/modules/analyse/components/AnalyseListPagination.vue';
    import RequiredBirthdaySingleDatePicker from '@/modules/common/components/RequiredBirthdaySingleDatePicker.vue';
    import {Locale} from '@/modules/user/store/userState';
    import {State} from 'vuex-class';
    import StringUtils from '@/utils/stringUtils';

    @Component({
        components: {AnalyseListPagination, RequiredBirthdaySingleDatePicker}
    })
    export default class CreateAnalyseFormStepPatient extends Vue {

        public static readonly ROWS_PER_PAGE = 5;

        @State((state) => state.user.settings.locale)
        public readonly locale!: Locale;

        @Prop()
        public selectedPatient!: Patient;

        public isAutoFill: boolean = false;

        public fetching: boolean = false;

        public patientWasSelected: boolean = false;

        public headers = [
            {
                text: 'analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.table.headers.lastname',
                align: 'left',
                value: 'lastname',
                width: '18%',
                sortable: false
            },
            {
                text: 'analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.table.headers.firstname',
                align: 'left',
                value: 'firstname',
                width: '18%',
                sortable: false
            },
            {
                text: 'analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.table.headers.patronymic',
                align: 'left',
                value: 'patronymic',
                width: '18%',
                sortable: false
            },
            {
                text: 'analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.table.headers.bday',
                align: 'left',
                value: 'bday',
                width: '10%'
            },
            {
                text: 'analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.table.headers.address',
                align: 'left',
                value: 'address',
                width: '26%',
                sortable: false
            },
            {
                text: 'analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.table.headers.actions',
                align: 'left',
                value: 'actions',
                width: '10%',
                sortable: false
            }
        ];

        public patients: Patient[] = [];

        public searchField: string = '';

        public sort?: string = ''; // format: property,asc|desc

        public page: number = 1;

        public totalItems: number = 0;

        public rowsPerPage: number = CreateAnalyseFormStepPatient.ROWS_PER_PAGE;

        @Watch('searchField')
        public onSearchChange(newVal: string | undefined, oldVal: string | undefined) {
            this.findPatients();
        }

        @Watch('page')
        public onPaginationChange(newVal: number, oldVal: number) {
            this.findPatients();
        }

        @Watch('sort')
        public onSortChange(newVal: string | undefined, oldVal: string | undefined) {
            this.findPatients();
        }

        @Watch('isAutoFill')
        public onIsAutoFillChange(newVal: boolean | undefined, oldVal: boolean | undefined) {
            if (!newVal) {
                this.patientWasSelected = false;
            }

            if (!newVal && this.selectedPatient.id != -1) {
                this.selectedPatient.id = -1;
                this.selectedPatient.fullName.firstname = '';
                this.selectedPatient.fullName.lastname = '';
                this.selectedPatient.fullName.patronymic = '';
                this.selectedPatient.bday = new Date();
                this.selectedPatient.address = '';
            }
        }

        public findPatients() {
            if (this.searchField != undefined && this.searchField.length > 0) {
                if (!this.fetching) {
                    this.fetching = true;
                }

                let newPage = undefined;
                if (this.page != undefined) {
                    newPage = this.page - 1;
                }

                let newSearchField = undefined;
                if (this.searchField != undefined) {
                    newSearchField = this.searchField;
                }

                let newSort = undefined;
                if (this.sort != undefined) {
                    newSort = this.sort;
                }

                PatientApiService
                    .findPatients(newSearchField, this.rowsPerPage, newPage, newSort)
                    .then((response) => {
                        this.patients = response.data.data.content;
                        this.totalItems = response.data.data.totalElements;
                        if (this.totalItems <= this.rowsPerPage) {
                            this.page = 1;
                        }
                    })
                    .catch((error) => this.$logger.error(error))
                    .finally(() => this.fetching = false)
            } else {
                this.page = 1;
                this.patients = [];
                this.totalItems = 0;
            }
        }

        get pagination(): Pagination {
            return {
                sortBy: this.sort ? this.sort.split(',')[0] : null,
                descending: this.sort ? this.sort.split(',')[1] === SortingDirection.DESC : null,
                page: null,
                totalItems: this.totalItems,
                rowsPerPage: null
            }
        }

        set pagination(pagination: Pagination) {
            this.updatePagination(this.mapSortingForApi(pagination.sortBy, pagination.descending));
        }

        @Emit('update:sort')
        public updatePagination(sort: string | undefined) {
            if (this.isAutoFill) {
                this.sort = sort;
            }
            return sort;
        }

        public mapSortingForApi(sortBy: string | null, descending: boolean | null): string | undefined {
            if (sortBy && descending !== null) {
                return `${sortBy},${descending ? SortingDirection.DESC : SortingDirection.ASC}`;
            } else {
                return undefined;
            }
        }

        public onPatientSelected(patient: Patient) {
            this.selectedPatient.id = patient.id;
            this.selectedPatient.fullName.firstname = patient.fullName.firstname;
            this.selectedPatient.fullName.lastname = patient.fullName.lastname;
            this.selectedPatient.fullName.patronymic = patient.fullName.patronymic;
            this.selectedPatient.bday = patient.bday;
            this.selectedPatient.address = patient.address;

            this.patientWasSelected = true;
        }

        public printSelectedPatient(): string {
            if (this.isAutoFill && (this.selectedPatient.fullName.firstname == undefined || this.selectedPatient.fullName.firstname.length == 0)) {
                return "-";
            } else {
                return StringUtils.fullName(this.selectedPatient.fullName) + " " + this.$moment(this.selectedPatient.bday).format('DD.MM.YYYY') + " " + this.selectedPatient.address;
            }
        }

        public resetAll() {
            this.isAutoFill = false;
            this.patientWasSelected = false;
            this.searchField = '';
            this.sort = '';
            this.page = 1;
            this.patients = [];
            this.totalItems = 0;
            this.selectedPatient.bday = new Date();
        }
    }
</script>
