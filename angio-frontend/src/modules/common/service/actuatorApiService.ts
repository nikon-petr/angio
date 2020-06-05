import {
    InfoModel,
    LoggersConfigurationModel,
    LoggingLevel,
    Metrics,
    MetricsModel
} from '@/modules/common/models/actuator';
import axios, {AxiosPromise} from 'axios';
import root from 'loglevel';

export default class ActuatorApiService {
    private static log = root.getLogger('ActuatorApiService');

    public static getInfo(): AxiosPromise<InfoModel> {
        ActuatorApiService.log.debug('create getInfo request');
        return axios.get('/actuator/info');
    }

    public static getMetrics(metric: Metrics): AxiosPromise<MetricsModel> {
        ActuatorApiService.log.debug(`create getMetrics request: metric=${metric}`);
        return axios.get(`/actuator/metrics/${metric}`);
    }

    public static getLoggers(): AxiosPromise<LoggersConfigurationModel> {
        ActuatorApiService.log.debug('create getLoggers request');
        return axios.get('/actuator/loggers');
    }

    public static setLoggingLevel(packageName: string, level: LoggingLevel | null): AxiosPromise<void> {
        ActuatorApiService.log.debug(`create setLoggingLevel request: loggerName=${packageName}, level=${level}`);
        return axios.post(`/actuator/loggers/${packageName}`, {configuredLevel: level});
    }
}
