package com.clearpicture.platform.util;


import org.apache.commons.lang3.RandomStringUtils;

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

    /**
     * Generate  lower cased random string by given length
     *
     * @param length
     * @return
     */
    public static String generateAuthCode(int length) {
        String generatedString = RandomStringUtils.random(length, true, true);
        return generatedString.toLowerCase();
    }


    public static String convertStringToHex(String str) {

        char[] chars = str.toCharArray();

        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }

        return hex.toString();
    }

    public static String convertHexToString(String hex) {

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char) decimal);

            temp.append(decimal);
        }


        return sb.toString();
    }


}
