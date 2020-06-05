export interface InfoModel {
    build?: BuildInfoModel;
}

export interface BuildInfoModel {
    version: string;
    artifact: string;
    name: string;
    group: string;
    time: string;
}

export interface MetricsModel {
    name: string;
    measurements: MeasurementModel[];
}

export interface MeasurementModel {
    statistic: string;
    value: number;
}

export enum Metrics {
    JVM_MEMORY_MAX = 'jvm.memory.max',
    JVM_MEMORY_USED = 'jvm.memory.used',
    JVM_MEMORY_COMMITTED = 'jvm.memory.committed',
    JVM_GC_PAUSE = 'jvm.gc.pause',
    JVM_GC_MEMORY_PROMOTED = 'jvm.gc.memory.promoted',
    JVM_GC_MEMORY_ALLOCATED = 'jvm.gc.memory.allocated',
    HTTP_SERVER_REQUESTS = 'http.server.requests',
    SYSTEM_CPU_COUNT = 'system.cpu.count',
    SYSTEM_CPU_USAGE = 'system.cpu.usage',
    PROCESS_UPTIME = 'process.uptime',
    PROCESS_CPU_USAGE = 'process.cpu.usage',
    PROCESS_START_TIME = 'process.start.time',
}

export interface LoggersConfigurationModel {
    loggers: Map<string, LoggerStateModel>;
}

export interface LoggerStateModel {
    configuredLevel: LoggingLevel;
    effectiveLevel: LoggingLevel
}

export const ROOT_LOGGER = 'ROOT';

export enum LoggingLevel {
    TRACE = 'TRACE',
    DEBUG = 'DEBUG',
    INFO = 'INFO',
    WARN = 'WARN',
    ERROR = 'ERROR',
    FATAL = 'FATAL',
    OFF = 'OFF'
}
