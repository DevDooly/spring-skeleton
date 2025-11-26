package com.devdooly.skeleton.common.utils;

import java.text.NumberFormat;

public class CommonUtils {

    public static String padding(int len, char pad, String str) {
        final int padding = len - str.length();
        if (padding <= 0) {
            return str;
        } else {
            return String.valueOf(pad).repeat(padding) + str;
        }
    }

    public static long pow(long v, int n) {
        if (n == 0) {
            return 1L;
        } else if (n == 1) {
            return v;
        } else {
            long half = pow(v, n / 2);
            if (n % 2 == 0) {
                return half * half;
            } else {
                return v * half * half;
            }
        }
    }

    public static String formatting(long num) {
        return NumberFormat.getInstance().format(num);
    }

}

