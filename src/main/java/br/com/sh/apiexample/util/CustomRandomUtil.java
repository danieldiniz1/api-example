package br.com.sh.apiexample.util;

import java.util.Random;

public class CustomRandomUtil {


    private CustomRandomUtil() {
        // Utility class, prevent instantiation
    }

    public static Boolean generateRandomString() {
        return new Random().nextBoolean();

    }
}
