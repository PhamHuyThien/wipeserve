package com.thiendz.wipe.wipeserve.utils;

public class NumberUtils {
    public static Long parseLong(Object number) {
        return number != null ? Long.parseLong(number.toString()) : null;
    }

    public static Long parseLong(Object number, Long def) {
        return number != null ? Long.parseLong(number.toString()) : def;
    }
}
