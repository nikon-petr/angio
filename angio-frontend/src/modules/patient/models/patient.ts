import FullName from '@/modules/common/models/fullName';

export interface Patient {
    id: number;
    fullName: FullName;
    bday: Date;
    address: string;
}