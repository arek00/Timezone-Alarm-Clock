package com.arek00.alarmclock.messages;

public enum Keys {

    TIMEZONE("timezone"),
    DATE_AS_STRING("dateAsaString"),
    HOUR("hour"),
    MINUTE("minute"),
    SECOND("second"),
    MILLIS("millis"),
    UTC_OFFSET("offset");

    private final String key;

    Keys(String key) {
        this.key = key;
    }
}
