export default class CollectionUtils {
    public static appendItem<T>(collection: Array<T>, item: T): Array<T> {
        return [...collection, item];
    }

    public static prependItem<T>(collection: Array<T>, item: T): Array<T> {
        return [item, ...collection];
    }

    public static updateItem<T>(collection: Array<T>, item: T, fieldToCompare: (item: T) => any): Array<T> {
        let index = collection.findIndex(e => fieldToCompare(e) === fieldToCompare(item));
        let result = [...collection];
        result[index] = item;
        return result;
    }

    public static removeByCondition<T>(collection: Array<T>, condition: (item: T) => boolean): Array<T> {
        let elementIndex: number | undefined = collection.findIndex(condition);
        if (elementIndex) {
            collection.splice(elementIndex, 1);
            return [...collection];
        }
        return collection;
    }
}
