package com.arek00.alarmclock.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtil {

    public static boolean containsIgnoreCaseAndSpaces(String contains, String in) {
        in = in.toLowerCase();
        in = in.replace(" ", "_");

        contains = contains.toLowerCase();
        contains = contains.replace(" ", "_");

        return in.contains(contains);
    }

}
