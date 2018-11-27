package com.library.android.common.utils;

final class StringUtils {

    private StringUtils() {
    }

    private static boolean isNotNullNotEmpty(String s) {
        return s != null && !s.isEmpty();
    }

    static String getDefaultString(String value, String defaultString) {
        if (isNotNullNotEmpty(value)) {
            return value;
        } else {
            return defaultString;
        }
    }

}
