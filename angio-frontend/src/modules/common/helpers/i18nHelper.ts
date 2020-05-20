import i18n from '@/plugins/i18n';

export default class I18nHelper {
    public static formatBoolean(value: boolean): string {
        if(value) {
            return i18n.t('common.helper.i18n.formatBoolean.true').toString();
        }  else {
            return i18n.t('common.helper.i18n.formatBoolean.false').toString();
        }
    }
}
