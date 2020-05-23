import CollectionUtils from '@/utils/collectionUtils';

interface TestElement {
    id: number;
    prop: string;
}

describe('store/modules/user.js', () => {
    it('testAppendElement', async () => {
        //given
        let array: TestElement[] = [
            {id: 1, prop: 'prop'},
            {id: 2, prop: 'prop'},
            {id: 3, prop: 'prop'}
        ];
        let newElement: TestElement = {id: 4, prop: ''};

        // when
        let result = CollectionUtils.appendItem(array, newElement);

        // then
        expect(result).toContain(newElement);
        expect(result[result.length - 1]).toEqual(newElement);
        expect(result).not.toBe(array);
    });

    it('testPrependElement', async () => {
        //given
        let array: TestElement[] = [
            {id: 1, prop: 'prop'},
            {id: 2, prop: 'prop'},
            {id: 3, prop: 'prop'}
        ];
        let newElement: TestElement = {id: 4, prop: ''};

        // when
        let result = CollectionUtils.prependItem(array, newElement);

        // then
        expect(result).toContain(newElement);
        expect(result[0]).toEqual(newElement);
        expect(result).not.toBe(array);
    });

    it('testUpdateElement', async () => {
        //given
        let array: TestElement[] = [
            {id: 1, prop: 'prop'},
            {id: 2, prop: 'prop'},
            {id: 3, prop: 'prop'}
        ];
        let newElement: TestElement = {id: 3, prop: ''};

        // when
        let result = CollectionUtils.updateItem(array, newElement, e => e.id);

        // then
        expect(result).toContain(newElement);
        expect(result).not.toBe(array);
    });

    it('removeByCondition', async () => {
        //given
        let array: TestElement[] = [
            {id: 1, prop: 'prop'},
            {id: 2, prop: 'prop'},
            {id: 3, prop: 'prop'}
        ];

        // when
        let result = CollectionUtils.removeByCondition(array, e => e.id === 2);

        // then
        expect(result).not.toContain(array[1]);
        expect(result).not.toBe(array);
    });
});
