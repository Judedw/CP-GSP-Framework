package com.clearpicture.platform.util;


import java.util.Random;

/**
 * GeneratorUtil - Sprint 8 : Rise of The TruVerus
 * <p>
 * Created by Raveen on 2019/03/18.
 */

public class GeneratorUtil {

    /**
     * Generate random password by using A-Z letters with 6 letter length
     */
    public static String generateRandomPassword() {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;
    }


    /**
     * Generate random digit by given length
     *
     * @param count
     * @return
     */
    public static String randomDigitbyLength(int count) {
        String NUMERIC_STRING = "0123456789";
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * NUMERIC_STRING.length());
            builder.append(NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
