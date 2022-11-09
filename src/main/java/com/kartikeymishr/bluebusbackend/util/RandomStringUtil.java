package com.kartikeymishr.bluebusbackend.util;

public class RandomStringUtil {

    /**
     * Generates a random string of length n
     *
     * @param n Length of random string
     * @param inputString String to manipulate
     * @return Returns a random string of length n
     */
    public static String getAlphaNumericString(int n, String inputString) {
        // chose a Character random from this String
        String inputStringUpperCase = inputString.trim().toUpperCase().replaceAll(" ", "").concat("123456789");

        // create StringBuffer size of inputString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            // generate a random number between
            // 0 to inputString variable length
            int index = (int) (inputStringUpperCase.length() * Math.random());

            // add Character one by one in end of sb
            sb.append(inputStringUpperCase.charAt(index));
        }

        return sb.toString();
    }
}

