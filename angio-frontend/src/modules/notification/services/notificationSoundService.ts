import root from 'loglevel';

const log = root.getLogger('NotificationSoundService');

export default class NotificationSoundService {

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
                log.debug('play new notification sound');
            })
            .catch((error) => {
                log.error(error);
            });
    }
}
