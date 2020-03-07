import FullName from '@/modules/common/models/fullName';

export default class StringUtils {
    public static isNotEmpty(data?: string): boolean {
        if (data) {
            return data.length > 0;
        } else {
            return false;
        }
    }

    public static fullName(name: FullName): string {
        const patronymic: string = name.patronymic ? name.patronymic : '';
        return `${name.lastname} ${name.firstname} ${patronymic}`;
    }
}
