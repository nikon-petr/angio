import axios, {AxiosPromise} from 'axios';
import root from 'loglevel';
import {Response} from '@/modules/common/models/response';
import {Patient} from '@/modules/patient/models/patient';

const log = root.getLogger('api/patient');

export class PatientApiService {
    public static getPatientById(id: number): AxiosPromise<Response<Patient>> {
        log.debug(`create getPatientById request with id ${id}`);
        return axios.get(`/patient/${id}`);
    }
}