package com.arek00.alarmclock.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Messenger;
import android.os.RemoteException;
import com.arek00.alarmclock.connections.TimeServiceConnection;
import com.arek00.alarmclock.handlers.IncomingMessagesHandler;
import com.arek00.alarmclock.services.TimeService;
import lombok.Getter;

public class TimeServiceController {

    @Getter private IncomingMessagesHandler incomingMessagesHandler = new IncomingMessagesHandler();
    @Getter private TimeServiceConnection serviceConnection;

    public TimeServiceController() {
        initializeServiceConnection();
    }

    private void initializeServiceConnection() {
        incomingMessagesHandler = new IncomingMessagesHandler();
        Messenger messenger = new Messenger(incomingMessagesHandler);
        serviceConnection = new TimeServiceConnection(messenger);
    }

    public void bindService(Context context) {
        context.bindService(new Intent(context, TimeService.class), serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void unbindService(Context context) {
        context.unbindService(serviceConnection);
    }


    public void registerIncomingMessagesListener(IncomingMessagesHandler.IncomingMessagesListener listener) {
        incomingMessagesHandler.getObservable().registerListener(listener);
    }

    public void sendMessage(int messageType, String messageText) throws RemoteException {
        TimeServiceConnection serviceConnection = getServiceConnection();
        serviceConnection.sendMessage(messageType, messageText);
    }

}
