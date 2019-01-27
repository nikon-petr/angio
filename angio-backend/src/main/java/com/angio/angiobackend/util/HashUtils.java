package com.angio.angiobackend.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.security.MessageDigest.getInstance;

@Slf4j
@UtilityClass
public class HashUtils {

    /**
     * Applies hash function to given string.
     *
     * @param source source string
     * @return hashed result string
     */
    public static String hash(@NonNull String source) {

        log.trace("hash() - start");
        MessageDigest digest = null;
        try {
            digest = getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        log.trace("hash() - update digest");
        digest.update(source.getBytes(), 0, source.length());

        String result = new BigInteger(1, digest.digest()).toString(16);

        log.trace("hash() - result: {}", result);
        return result;
    }
}
