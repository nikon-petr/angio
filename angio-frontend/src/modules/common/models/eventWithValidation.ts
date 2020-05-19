export default interface EventWithValidation<T> {
    isValid: boolean,
    payload?: T
}
