package com.thiendz.wipe.wipeserve.utils;

import com.thiendz.wipe.wipeserve.data.model.User;

public class DestinationUtils {
    public static String getDestinationOfConvertAndSend(User user) {
        return "/messages/" + Md5Utils.getMd5(user.getUsername());
    }
}
