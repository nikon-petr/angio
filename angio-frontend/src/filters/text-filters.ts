/**
 * Truncate string to given length and place ending to the end of string.
 * @param value
 * @param length length to truncate
 * @param ending string to place in the end
 */
import FullName from "@/modules/common/models/fullName";
import StringUtils from "@/utils/stringUtils";
import i18nHelper from "@/modules/common/helpers/i18nHelper";

export function truncate(value: string, length: number, ending: string = '...'): string | undefined {
    if (!value) {
        return;
    }

    return `${value.substr(0, length)}${ending}`;
}

/**
 * Return placeholder if value undefined or null or empty string.
 * @param value
 * @param placeholderStr
 */
export function placeholder(value: string, placeholderStr: string) {
    if (!value || value === ' ' || value == null) {
        return placeholderStr;
    } else {
        return value;
    }
}

/**
 * Calculate number value in representation format.
 * @param value
 */
export function round(value: number): string {
    return (Math.round(value * 100) / 100).toString();
}

/**
 * Returns the integer part of a number by removing any fractional digits.
 * @param value
 */
export function trunc(value: number): string {
    return (Math.trunc(value)).toString();
}

/**
 * Format full name.
 * @param fullName object
 */
export function fullName(fullName: FullName): string {
    return StringUtils.fullName(fullName);
}

/**
 * Format compact full name.
 * @param fullName object
 */
export function compactFullName(fullName: FullName): string {
    return StringUtils.compactFullName(fullName);
}

/**
 * Format boolean to yes/no.
 * @param value boolean value
 */
export function formatBoolean(value: boolean): string {
    return i18nHelper.formatBoolean(value);
}
