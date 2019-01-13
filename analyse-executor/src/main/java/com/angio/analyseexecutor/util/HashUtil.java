package com.angio.analyseexecutor.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.security.MessageDigest.getInstance;

@Slf4j
@UtilityClass
public class HashUtil {

    public static String hash(@NonNull String source) {

        log.info("hash() - start");
        MessageDigest digest = null;
        try {
            digest = getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        log.info("hash() - update digest");
        digest.update(source.getBytes(), 0, source.length());

        String result = new BigInteger(1, digest.digest()).toString(16);

        log.info("hash() - result: {}", result);
        return result;
    }
}
