package com.arek00.alarmclock.utils;

import android.os.Message;
import com.arek00.alarmclock.messages.Keys;

public class MessageWrapper {

    private Message message;

    public MessageWrapper(Message message) {
        this.message = message;
    }

    private String getString(Keys key) {
        return message.getData().getString(key.getKey());
    }

    public String getDateAsAString() {
        return getString(Keys.DATE_AS_STRING);
    }

    public String getTimeZoneName() {
        return getString(Keys.TIMEZONE_NAME);
    }

    public double getOffset() {
        return message.getData().getDouble(Keys.UTC_OFFSET.getKey());
    }
}
