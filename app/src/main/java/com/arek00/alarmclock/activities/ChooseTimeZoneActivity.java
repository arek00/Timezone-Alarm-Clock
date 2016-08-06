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
import android.widget.*;
import com.arek00.alarmclock.MyAdapter;
import com.arek00.alarmclock.R;
import com.arek00.alarmclock.connections.TimeServiceConnection;
import com.arek00.alarmclock.handlers.IncomingMessagesAdapter;
import com.arek00.alarmclock.handlers.IncomingMessagesHandler;
import com.arek00.alarmclock.messages.Keys;
import com.arek00.alarmclock.services.TimeService;
import com.arek00.alarmclock.time.JodaTimezone;
import com.arek00.alarmclock.utils.BundleBuilder;
import com.arek00.alarmclock.utils.MessageWrapper;
import org.joda.time.DateTimeZone;


public class ChooseTimeZoneActivity extends Activity {

    private IncomingMessagesHandler handler;
    private Messenger handlerMessenger;
    private TimeServiceConnection serviceConnection;
    private String timezoneName = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JodaTimezone.initialize();
        initializeServiceHandling();
        initializeContentView();
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
        bindService(new Intent(this, TimeService.class), serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void initializeContentView() {
        setContentView(R.layout.set_timezone);
        Log.i("MyActivity", "OnCreate");

        setTimeZonesList(new DateTimeZone[]{});
        setListItemClickListener(new ListItemClickListener());
    }

    private void setTimeZonesList(DateTimeZone[] timeZones) {
        ListView list = (ListView) findViewById(R.id.citiesList);
        ListAdapter adapter = new MyAdapter(this, timeZones);
        list.setAdapter(adapter);
    }

    private void checkIfFoundAnyTimeZone(DateTimeZone[] timeZones) {
        if (timeZones.length == 0) {
            Toast toast = Toast.makeText(this, "Did not found anything.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    private void setListItemClickListener(ListItemClickListener listener) {
        ListView citiesList = (ListView) findViewById(R.id.citiesList);
        citiesList.setOnItemClickListener(listener);
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
    }

    public void onSetDateButtonClick(View view) {
        if (timezoneName.isEmpty()) {
            showToast("Choose timezone", Toast.LENGTH_SHORT);
            return;
        }

        Bundle bundle = new BundleBuilder()
                .withString(Keys.TIMEZONE_NAME.getKey(), timezoneName)
                .build();

        Intent intent = new Intent(this, DatePickerActivity.class);
        intent.putExtras(bundle);

        startActivity(intent, bundle);
        finish();
    }

    private void showToast(String text, int duration) {
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }

    public void onSearchForTimeZone(View view) {
        EditText searchField = (EditText) findViewById(R.id.searchField);
        String searchFieldText = searchField.getText().toString();

        DateTimeZone[] timeZonesByName = JodaTimezone.findTimeZonesByName(searchFieldText);
        checkIfFoundAnyTimeZone(timeZonesByName);
        setTimeZonesList(timeZonesByName);
        Log.i("Search Timezones", "Found " + timeZonesByName.length + " timezones.");
    }

    private void setTimezoneName(Message message) {
        this.timezoneName = message.getData().getString(Keys.TIMEZONE_NAME.getKey());
    }

    private void setDateToTextView(Message message) {
        MessageWrapper wrappedMessage = new MessageWrapper(message);
        String timeZoneName = wrappedMessage.getTimeZoneName();
        String date = wrappedMessage.getDateAsAString();
        setDateToTextView(timeZoneName, date);
    }

    private void setDateToTextView(String timeZoneName, String dateAsString) {
        TextView timeLabel = (TextView) findViewById(R.id.chosenTimeValueLabel);
        TextView timezoneNameLabel = (TextView) findViewById(R.id.timeZoneNameValueLabel);
        timeLabel.setText(dateAsString);
        timezoneNameLabel.setText(timeZoneName);
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
            setTimezoneName(message);
        }
    }

}