package com.arek00.alarmclock.connections;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.*;
import android.support.annotation.NonNull;
import android.util.Log;
import com.arek00.alarmclock.messages.Keys;
import com.arek00.alarmclock.services.TimeService;
import com.arek00.alarmclock.utils.BundleBuilder;

public class TimeServiceConnection implements ServiceConnection {
    private Messenger serviceMessenger;
    private Messenger handlerMessenger;

    public TimeServiceConnection(@NonNull Messenger handlerMessenger) {
        this.handlerMessenger = handlerMessenger;
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder service) {
        serviceMessenger = new Messenger(service);
        Log.i("ServiceConnection", "Creating service connection");
        try {
            registerClient();
        } catch (RemoteException e) {
            Log.e("TimeServiceConnection", "Could not register client");
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        serviceMessenger = null;
    }

    public void sendMessage(int messageType, String messageString) throws RemoteException {
        if (serviceMessenger == null) {
            return;
        }

        Message message = createMessage(messageString, messageType);
        sendMessage(message);
        Log.i("MyActivity", "Sent message");
    }

    public void sendMessage(Message message) throws RemoteException {
        if (serviceMessenger == null) {
            return;
        }

        serviceMessenger.send(message);
    }

    private void registerClient() throws RemoteException {
        Message message = Message.obtain(null, TimeService.REGISTER_CLIENT);
        message.replyTo = handlerMessenger;
        serviceMessenger.send(message);
    }

    private Message createMessage(String text, int messageType) {
        String timeZoneKeyName = Keys.TIMEZONE_NAME.getKey();
        Message message = Message.obtain(null, messageType);
        Bundle bundle = new BundleBuilder()
                .withString(timeZoneKeyName, text)
                .build();

        message.setData(bundle);
        message.replyTo = handlerMessenger;
        return message;
    }
}
