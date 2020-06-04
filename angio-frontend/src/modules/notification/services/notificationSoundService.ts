import root, {Logger} from 'loglevel';

export default class NotificationSoundService {

    private static log: Logger = root.getLogger(NotificationSoundService.name);

    private _audio: HTMLAudioElement = new Audio(require('@/assets/small-tip.mp3'));

    private static _instance: NotificationSoundService;

    private constructor() {
    }

    public static getInstance() {
        return this._instance || (this._instance = new this());
    }

    public playNewNotificationSound() {
        this._audio.play()
            .then(() => {
                NotificationSoundService.log.debug('play new notification sound');
            })
            .catch((error) => {
                NotificationSoundService.log.error(error);
            });
    }
}
