package com.thiendz.wipe.wipeserve.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static Long currentLongTime() {
        return new Date().getTime();
    }
}
