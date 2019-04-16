import FullName from '@/modules/common/models/fullName';
import StaticFile from '@/modules/common/models/staticFile';

export default interface AnalyseItem {
    id: number;
    name: string;
    originalImage: StaticFile;
    shortDescription: string;
    analyseType: AnalyseType;
    analyseDate: Date;
    status: AnalyseStatus;
    patient: PatientItem;
    diagnostician: FullName;
    starred: boolean;
}

export enum AnalyseType {
    PRIMARY = 'PRIMARY',
    SUBSEQUENT = 'SUBSEQUENT'
}

export interface AnalyseStatus {
    extension: string;
    type: AnalyseStatusType;
}

export enum AnalyseStatusType {
    CREATED = 'CREATED',
    IN_PROGRESS = 'IN_PROGRESS',
    SUCCESS = 'SUCCESS',
    FAILED = 'FAILED',
    DELETED = 'DELETED'
}

export interface PatientItem {
    fullName: FullName;
    policy: string;
}

export interface AnalyseFilterModel {
    search?: string;
    statuses?: AnalyseStatusType[];
    startDate?: Date;
    endDate?: Date;
    date?: Date;
    isStarred?: boolean;
}