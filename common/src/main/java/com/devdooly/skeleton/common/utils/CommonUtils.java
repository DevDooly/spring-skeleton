package com.devdooly.skeleton.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

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
            long half = pow(v, n/2);
            if (n % 2 == 0) {
                return half * half;
            } else {
                return v * half * half;
            }
        }
    }

}
