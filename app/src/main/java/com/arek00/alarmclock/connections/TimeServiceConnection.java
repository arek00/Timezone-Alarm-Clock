package com.arek00.alarmclock.connections;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.*;
import android.support.annotation.NonNull;
import android.util.Log;
import com.arek00.alarmclock.builders.BundleBuilder;
import com.arek00.alarmclock.services.TimeService;

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
            Message message = Message.obtain(null, TimeService.REGISTER_CLIENT);
            message.replyTo = handlerMessenger;
            serviceMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        serviceMessenger = null;
    }

    public void sendMessage(String messageString, int messageType) throws RemoteException {
        if (serviceMessenger == null) {
            return;
        }

        serviceMessenger.send(createMessage(messageString, messageType));
        Log.i("MyActivity", "Sent message");
    }

    private Message createMessage(String text, int messageType) {
        Message message = Message.obtain(null, messageType);
        Bundle bundle = new BundleBuilder()
                .withString("name", text)
                .build();

        message.setData(bundle);
        message.replyTo = handlerMessenger;
        return message;
    }
}
