/**
 * Truncate string to given length and place ending to the end of string.
 * @param value
 * @param length length to truncate
 * @param ending string to place in the end
 */
export function truncate(value: string, length: number, ending: string = '...'): string | undefined {
    if (!value) {
        return;
    }

    return `${value.substr(0, length)}${ending}`;
}

/**
 * Return placeholder if value undefined or null or empty string.
 * @param value
 * @param placeholder
 */
export function placeholder(value: string, placeholder: string) {
    if(!value || value === ' ' || value == null) {
        return placeholder;
    } else {
        return value;
    }
}