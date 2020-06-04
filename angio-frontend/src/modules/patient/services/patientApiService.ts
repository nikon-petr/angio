import axios, {AxiosPromise} from 'axios';
import root from 'loglevel';
import {Response} from '@/modules/common/models/response';
import {Patient, PatientRequest} from '@/modules/patient/models/patient';
import Page from '@/modules/common/models/page';

export class PatientApiService {
    private static log = root.getLogger(PatientApiService.name);

    public static getPatientById(id: number): AxiosPromise<Response<Patient>> {
        PatientApiService.log.debug(`create getPatientById request with id ${id}`);
        return axios.get(`/patient/${id}`);
    }

    public static findPatients(search?: string, size?: number, page?: number, sort?: string): AxiosPromise<Response<Page<Patient>>> {
        PatientApiService.log.debug(`create findPatients request with data search=${search}, size=${size}, page=${page}, sort=${sort}`);
        return axios.get<Response<Page<Patient>>>('/patient', {params: {search, size, page, sort}});
    }

    public static addPatient(patientRequest: PatientRequest): AxiosPromise<Response<Patient>> {
        PatientApiService.log.debug(`create addPatient request with data =${JSON.stringify(patientRequest)}`);
        return axios.post<Response<Patient>>('/patient', patientRequest);
    }
}
