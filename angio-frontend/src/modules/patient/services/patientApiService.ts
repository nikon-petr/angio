import axios, {AxiosPromise} from 'axios';
import root from 'loglevel';
import {Response} from '@/modules/common/models/response';
import {Patient, PatientRequest} from '@/modules/patient/models/patient';
import Page from '@/modules/common/models/page';

const log = root.getLogger('api/patient');

export class PatientApiService {
    public static getPatientById(id: number): AxiosPromise<Response<Patient>> {
        log.debug(`create getPatientById request with id ${id}`);
        return axios.get(`/patient/${id}`);
    }

    public static findPatients(search?: string, size?: number, page?: number, sort?: string): AxiosPromise<Response<Page<Patient>>> {
        log.debug(`create findPatients request with data search=${JSON.stringify(search)}, size=${JSON.stringify(size)}, page=${JSON.stringify(page)}, sort=${JSON.stringify(sort)}`);
        return axios.get<Response<Page<Patient>>>('/patient', {params: {search, size, page, sort}});
    }

    public static addPatient(patientRequest: PatientRequest): AxiosPromise<Response<Patient>> {
        log.debug(`create addPatient request with data =${JSON.stringify(patientRequest)}`);
        return axios.post<Response<Patient>>('/patient', patientRequest);
    }
}