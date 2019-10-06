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
    singleDate?: Date;
    isStarred?: boolean;
}

export interface AnalyseStarred {
    starred: boolean;
}

interface Diagnostician {
    username: string;
    fullName: FullName;
}

interface AnalyseAdditionalInfo {
    name: string;
    patientId: number;
    diagnostician: Diagnostician;
    shortDescription: string;
    fullDescription: string;
    type: string;
    comment: string;
    conclusion: string;
}

export interface Analyse {
    additionalInfo: AnalyseAdditionalInfo;
}
