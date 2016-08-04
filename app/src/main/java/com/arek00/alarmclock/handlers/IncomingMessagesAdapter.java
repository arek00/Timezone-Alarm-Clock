package com.arek00.alarmclock.handlers;

import android.os.Message;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Empty implementations of {@link com.arek00.alarmclock.handlers.IncomingMessagesHandler.IncomingMessagesListener}
 * Designated to extend and implement only demanded methods.
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class IncomingMessagesAdapter implements IncomingMessagesHandler.IncomingMessagesListener {
    @Override
    public void onRegisterCityMessageReceived(Message message) {

    }

    @Override
    public void onDateMessageReceived(Message message) {

    }

    @Override
    public void onSearchMessageReceived(Message message) {

    }

    @Override
    public void onSetCityMessageReceived(Message message) {

    }
}
