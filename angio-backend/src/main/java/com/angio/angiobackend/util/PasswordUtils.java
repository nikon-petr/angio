package com.angio.angiobackend.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
@UtilityClass
public class PasswordUtils {

    /**
     * Generate simple number password.
     * example: 6 -> 759212
     *
     * @param length password length
     * @return password
     */
    public static String generateNumberPassword(int length) {

        log.debug("generateNumberPassword() - start generating password with length={}", length);
        List<Character> charList = getRandomNumbers(length).collect(Collectors.toList());

        log.debug("generateNumberPassword() - char list: {}", charList);
        Collections.shuffle(charList);
        String password = charList.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        log.debug("generateNumberPassword() - result: {}", password);

        log.debug("generateNumberPassword() - end");
        return password;
    }

    private Stream<Character> getRandomNumbers(int count) {
        Random random = new SecureRandom();
        IntStream specialChars = random.ints(count, 48, 57);
        return specialChars.mapToObj(data -> (char) data);
    }
}
