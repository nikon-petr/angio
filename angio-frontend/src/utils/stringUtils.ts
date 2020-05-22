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
        if (!name) {
            return '';
        }

        if (!name.lastname && !name.patronymic && name.firstname) {
            return name.firstname;
        }

        const patronymic: string = name.patronymic ? name.patronymic : '';
        return `${name.lastname} ${name.firstname} ${patronymic}`;
    }

    public static compactFullName(name: FullName): string {
        if (!name) {
            return '';
        }

        if (!name.lastname && !name.patronymic && name.firstname) {
            return name.firstname;
        }

        const firstname: string = name.firstname ? name.firstname.substr(0, 1) : '';
        const patronymic: string = name.patronymic ? name.patronymic.substr(0, 1) : '';
        let result = `${name.lastname}\u00A0${firstname}.`;
        if (patronymic) {
            result += `${patronymic}.`
        }

        return result;
    }
}
