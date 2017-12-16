package com.sadicomputing.foodordering.utils;

import java.security.SecureRandom;

/**
 * Created by modykane on 14/12/2017.
 */

public class Constantes {

    private static final String ALPHA_NUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC = "0123456789";
    private static SecureRandom rnd = new SecureRandom();

    public static String randomString(){
        int length = 10;
        StringBuilder sb = new StringBuilder(length);
        for(int i=0; i<length; i++)
            sb.append(NUMERIC.charAt(rnd.nextInt(NUMERIC.length())));
        return sb.toString();
    }
}
