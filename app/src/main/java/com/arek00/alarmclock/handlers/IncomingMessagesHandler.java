package com.arek00.alarmclock.handlers;

import android.os.Handler;
import android.os.Message;
import com.arek00.alarmclock.services.TimeService;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class IncomingMessagesHandler extends Handler {

    @Getter private final ObservableHandler listeningHandler = new ObservableHandler();

    @Override
    public void handleMessage(Message message) {
        listeningHandler.informListeners(message);
    }

    public interface IncomingMessagesListener {
        void onRegisterCityMessageReceived(Message message);

        void onDateMessageReceived(Message message);

        void onSearchMessageReceived(Message message);

        void onSetCityMessageReceived(Message message);
    }

    public class ObservableHandler {
        private List<IncomingMessagesListener> listeners = new ArrayList<IncomingMessagesListener>();

        public void registerListener(@NonNull IncomingMessagesListener listener) {
            listeners.add(listener);
        }

        public void removeListener(@NonNull IncomingMessagesListener listener) {
            listeners.remove(listener);
        }

        public void informListeners(Message message) {
            for (IncomingMessagesListener incomingListener : listeners) {
                informListenerById(incomingListener, message);
            }
        }

        private void informListenerById(IncomingMessagesListener listener, Message message) {
            int id = message.what;

            if (TimeService.REGISTER_CLIENT == id) {
                listener.onRegisterCityMessageReceived(message);
            }
            if (TimeService.SEARCH_CITY == id) {
                listener.onSearchMessageReceived(message);
            }
            if (TimeService.SEND_DATE == id) {
                listener.onDateMessageReceived(message);
            }
            if (TimeService.SET_CITY == id) {
                listener.onSetCityMessageReceived(message);
            }
        }
    }
}
