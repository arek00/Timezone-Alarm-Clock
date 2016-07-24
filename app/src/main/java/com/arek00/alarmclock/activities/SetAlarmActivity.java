package com.arek00.alarmclock.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.arek00.alarmclock.MyAdapter;
import com.arek00.alarmclock.R;
import com.arek00.alarmclock.connections.TimeServiceConnection;
import com.arek00.alarmclock.handlers.IncomingMessagesAdapter;
import com.arek00.alarmclock.handlers.IncomingMessagesHandler;
import com.arek00.alarmclock.services.TimeService;
import com.arek00.alarmclock.time.JodaTimezone;
import org.joda.time.DateTimeZone;


public class SetAlarmActivity extends Activity {

    private IncomingMessagesHandler handler;
    private Messenger handlerMessenger;
    private TimeServiceConnection serviceConnection;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeServiceHandling();
        initializeContentView();

        startService(new Intent(SetAlarmActivity.this, TimeService.class));
        bindService(new Intent(this, TimeService.class), serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void initializeServiceHandling() {
        initializeMessagesHandler();
        initializeService();
    }

    private void initializeMessagesHandler() {
        handler = new IncomingMessagesHandler();
        IncomingMessagesHandler.ObservableHandler listeningHandler = handler.getListeningHandler();
        listeningHandler.registerListener(new OnIncomingMessagesListener());
    }

    private void initializeService() {
        handlerMessenger = new Messenger(handler);
        serviceConnection = new TimeServiceConnection(handlerMessenger);
    }

    private void initializeContentView() {
        setContentView(R.layout.set_alarm);

        Log.i("MyActivity", "OnCreate");

        ListView list = (ListView) findViewById(R.id.citiesList);


        ListAdapter adapter = new MyAdapter(this, JodaTimezone.getAvailableTimeZones());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new ListItemClickListener());
    }

    private void sendMessageToService(String searchPhrase) {
        try {
            serviceConnection.sendMessage(searchPhrase, TimeService.SEARCH_CITY);
        } catch (RemoteException e) {
            Log.e("Send Message", "Could not send message to service.");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        unbindService(serviceConnection);
        stopService(new Intent(SetAlarmActivity.this, TimeService.class));
    }

    public void citiesList(View view) {
        Intent citiesListActivity = new Intent(this, MyAdapter.class);
        startActivity(citiesListActivity);
    }

    private void setDateToTextView(Message message) {
        String cityName, date;
        cityName = message.getData().getString("cityName");
        date = message.getData().getString("date");

        TextView textView = (TextView) findViewById(R.id.chosenTimeValueLabel);
        textView.setText(date);

    }

    public void onSearchClick(View view) {
        Log.i("MyActivity", "Not implemented");
    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            String text = ((DateTimeZone) adapterView.getItemAtPosition(i)).getID();
            sendMessageToService(text);
        }
    }

    private class OnIncomingMessagesListener extends IncomingMessagesAdapter {
        @Override
        public void onDateMessageReceived(Message message) {
            super.onDateMessageReceived(message);
            setDateToTextView(message);
        }
    }

}