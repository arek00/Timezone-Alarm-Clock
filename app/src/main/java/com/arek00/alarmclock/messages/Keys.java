package com.arek00.alarmclock.messages;

import lombok.Getter;

public enum Keys {

    TIMEZONE_NAME("timezone"),
    DATE_AS_STRING("dateAsaString"),
    HOUR("hour"),
    MINUTE("minute"),
    SECOND("second"),
    MILLIS("millis"),
    UTC_OFFSET("offset");

    @Getter private final String key;

    Keys(String key) {
        this.key = key;
    }
}
