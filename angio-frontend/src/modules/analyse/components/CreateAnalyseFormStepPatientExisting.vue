<template>
    <v-layout row wrap>
        <v-flex xs12>
            <v-text-field
                    v-bind:label="$t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.searchField')"
                    v-model="searchField"
                    hide-details
                    clearable
                    solo
                    mb-3
            ></v-text-field>
        </v-flex>
        <v-flex xs12>
            <v-data-table
                    v-bind:headers="headers"
                    v-bind:items="patients"
                    v-bind:loading="fetching"
                    v-bind:pagination.sync="pagination"
                    v-bind:total-items="totalItems"
                    v-bind:no-data-text="$t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.table.noDataText')"
                    sort-icon="arrow_drop_down"
                    disable-initial-sort
                    class="elevation-2"
                    item-key="id"
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
                                v-on:click="onFormStepAnalysePatientExistingChanged(props.item)"
                                color="accent"
                                class="ma-2"
                                round
                                flat
                        >
                            {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.table.add') }}
                        </v-btn>
                    </td>
                </template>
            </v-data-table>
        </v-flex>

        <v-flex class="pt-2" xs12>
            <BasePagination
                    v-bind:page.sync="page"
                    v-bind:rows-per-page="rowsPerPage"
                    v-bind:total-items="totalItems"
            ></BasePagination>
        </v-flex>

        <v-flex class="pt-2 mb-2" xs12>
            <span class="font-weight-medium text--secondary">
                {{ $t('analyse.component.createAnalyseFormDialog.stepper.stepPatient.data.form.selectedPatientInfo') }}
            </span>
            <span class="font-weight-medium subtitle-1">
                {{ printSelectedPatient() }}
            </span>
        </v-flex>
    </v-layout>
</template>

<script lang="ts">
    import BasePagination from '@/modules/common/components/BasePagination.vue';
    import SingleDatePicker from '@/modules/common/components/SingleDatePicker.vue';
    import {CommonEvent} from '@/modules/common/helpers/commonEvent';
    import Pagination from '@/modules/common/helpers/pagination';
    import EventWithValidation from '@/modules/common/models/eventWithValidation';
    import {SortingDirection} from '@/modules/common/models/page';
    import {Patient} from '@/modules/patient/models/patient';
    import {PatientApiService} from '@/modules/patient/services/patientApiService';
    import {Locale} from '@/modules/user/store/userState';
    import StringUtils from '@/utils/stringUtils';
    import {Component, Emit, Vue, Watch} from 'vue-property-decorator';
    import {State} from 'vuex-class';

    @Component({
        components: {BasePagination, SingleDatePicker}
    })
    export default class CreateAnalyseFormStepPatientExisting extends Vue {

        public static readonly ROWS_PER_PAGE = 5;

        @State((state) => state.user.settings.locale)
        public readonly locale!: Locale;

        public fetching: boolean = false;

        public existingPatient: Patient = {
            id: undefined,
            fullName: {
                firstname: '',
                lastname: '',
                patronymic: ''
            },
            bday: new Date(),
            address: ''
        };

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
                align: 'center',
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

        public rowsPerPage: number = CreateAnalyseFormStepPatientExisting.ROWS_PER_PAGE;

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
            this.sort = sort;

            return sort;
        }

        public mapSortingForApi(sortBy: string | null, descending: boolean | null): string | undefined {
            if (sortBy && descending !== null) {
                return `${sortBy},${descending ? SortingDirection.DESC : SortingDirection.ASC}`;
            } else {
                return undefined;
            }
        }

        @Emit(CommonEvent.CHANGE)
        public onFormStepAnalysePatientExistingChanged(patient: Patient): EventWithValidation<Patient> {
            this.existingPatient.id = patient.id;
            this.existingPatient.fullName.firstname = patient.fullName.firstname;
            this.existingPatient.fullName.lastname = patient.fullName.lastname;
            this.existingPatient.fullName.patronymic = patient.fullName.patronymic;
            this.existingPatient.bday = patient.bday;
            this.existingPatient.address = patient.address;

            return {
                isValid: true,
                payload: this.existingPatient
            }
        }

        public printSelectedPatient(): string {
            if (this.existingPatient.fullName.firstname == undefined || this.existingPatient.fullName.firstname.length == 0) {
                return "-";
            } else {
                return StringUtils.fullName(this.existingPatient.fullName) + " " + this.$moment(this.existingPatient.bday).format('DD.MM.YYYY') + " " + this.existingPatient.address;
            }
        }
    }
</script>
