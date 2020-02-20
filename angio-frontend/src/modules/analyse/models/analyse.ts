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

export interface AnalyseAdditionalInfo {
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
    id: number,
    starred: boolean,
    analyseDate: Date,
    originalImage: StaticFile;
    additionalInfo: AnalyseAdditionalInfo,
    geometricAnalyse: GeometricAnalyse,
    bloodFlowAnalyse: BloodFlowAnalyse;
}

export interface GeometricAnalyse {
    binarizedImage: StaticFile,
    skeletonizedImage: StaticFile,
    vessels: Vessel[];
}

export interface Vessel {
    id: number,
    vesselImage: StaticFile,
    mainVesselImage: StaticFile,
    tortuosityDegree: number,
    countOfBranches: number,
    branchingDegree: number,
    area: number,
    areaPercent: number;
}

export interface BloodFlowAnalyse {
    ischemiaImage: StaticFile,
    ischemias: Ischemia[],
    macula: Macula,
    densityImage: StaticFile,
    densities: Density[];
}

export interface Ischemia {
    id: number,
    area: number,
    zoneNumber: number,
    x: number,
    y: number;
}

export interface Macula {
    area: number,
    radius: number,
    x: number,
    y: number;
}

export interface Density {
    id: number,
    sectorNumber: number,
    density: number;
}
