package com.angio.angiobackend.util;

import com.google.common.collect.Maps;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Array;
import java.util.EnumSet;
import java.util.Map;

@UtilityClass
public class EnumUtils {

    /**
     * Convert string to enum if possible.
     *
     * @param name enum name
     * @param clazz enum type
     * @param <E> enum type
     * @return enum value
     */
    public static <E extends Enum<E>> E getIfPresent(String name, Class<E> clazz) {
        E[] emptyArray = (E[]) Array.newInstance(clazz, 0);
        E[] values = EnumSet.allOf(clazz).toArray(emptyArray);
        Map<String, E> index = Maps.newHashMapWithExpectedSize(values.length);
        for (E value : values) {
            index.put(value.name(), value);
        }
        return index.getOrDefault(name, null);
    }
}
