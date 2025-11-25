package com.devdooly.skeleton.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtil {
    private static final Pattern formatPattern = Pattern.compile("\\{(0|[1-9][0-9]*)(,[-*][1-9][0-9]*)?}");

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * log4j style. 치환 "{}". escape sequence '//'
     */
    public static String simpleFormat(String format, Object... args) {
        if (format == null)
            return "null";

        StringBuilder sb = new StringBuilder();
        int end = format.length();
        int argIdx = 0;
        for (int pos = 0; pos < end; pos++) {
            char c = format.charAt(pos);
            int next = pos + 1;
            if (c == '{') {
                if (next < end && format.charAt(next) == '}') {
                    if (argIdx < args.length) {
                        sb.append(args[argIdx++]);
                    } else {
                        sb.append("{}");
                    }
                    pos = next;
                } else {
                    sb.append(c);
                }
            } else if (c == '\\') {
                if (next < end) {
                    sb.append(format.charAt(next));
                    pos = next;
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * {index[,alignment]} e.g {0,+16}
     * index : start with 0 index.
     * alignment : (optional) signed decimal number. The rest of length will be padded by white space(+:right align, -:left align).
     *
     * @param format format string
     * @param args   values to replace the index with. the order of values matches the index.
     */
    public static String format(String format, Object... args) {
        if (format == null) {
            return "null";
        }

        int next = 0;
        int startIdx;
        int endIdx;
        StringBuilder sb = new StringBuilder();
        for (Matcher matcher = formatPattern.matcher(format); matcher.find(); next = endIdx) {
            startIdx = matcher.start();
            endIdx = matcher.end();
            String[] holder = format.substring(startIdx + 1, endIdx - 1).split(",");
            int argIdx = Integer.parseInt(holder[0]);
            if (argIdx >= args.length) {
                sb.append(format, next, endIdx);
            } else {
                sb.append(format, next, startIdx);
                formatting(sb, holder.length == 2 ? holder[1] : null, args[argIdx]);
            }
        }

        return next < format.length() ? sb.append(format, next, format.length()).toString() : sb.toString();
    }

    private static void formatting(StringBuilder sb, String alignment, Object arg) {
        if (alignment == null) {
            sb.append(arg);
        } else {
            String s = String.valueOf(arg);
            int padding = Integer.parseInt(alignment.substring(1)) - s.length();
            if (padding < 0) padding = 0;

            if (alignment.charAt(0) == '-') {
                sb.append(s);
                sb.append(" ".repeat(padding));
            } else {
                sb.append(" ".repeat(padding));
                sb.append(s);
            }
        }
    }
}
