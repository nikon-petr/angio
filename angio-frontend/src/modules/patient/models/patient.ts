import FullName from '@/modules/common/models/fullName';

export interface Patient {
    id: number;
    fullName: FullName;
    bday: Date;
    address: string;
}

export interface PatientRequest {
    fullName: FullName;
    bday: Date;
    address: string;
}