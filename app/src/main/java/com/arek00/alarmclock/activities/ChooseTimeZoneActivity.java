package com.arek00.alarmclock.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.arek00.alarmclock.MyAdapter;
import com.arek00.alarmclock.R;
import com.arek00.alarmclock.controllers.TimeServiceController;
import com.arek00.alarmclock.controllers.TimeZonesController;
import com.arek00.alarmclock.handlers.IncomingMessagesAdapter;
import com.arek00.alarmclock.messages.Keys;
import com.arek00.alarmclock.services.TimeService;
import com.arek00.alarmclock.utils.BundleBuilder;
import com.arek00.alarmclock.utils.MessageWrapper;
import com.arek00.alarmclock.utils.Toaster;
import com.arek00.alarmclock.utils.ViewUtils;
import org.joda.time.DateTimeZone;


public class ChooseTimeZoneActivity extends Activity {

    private String timezoneName = "";

    private TimeZonesController timeZonesController = new TimeZonesController();
    private TimeServiceController timeServiceController = new TimeServiceController();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeService();
        initializeContentView();
    }

    private void initializeService() {
        timeServiceController.registerIncomingMessagesListener(new OnIncomingMessagesListener());
        timeServiceController.bindService(this);
    }

    private void initializeContentView() {
        setContentView(R.layout.set_timezone);
        Log.i("MyActivity", "OnCreate");

        setTimeZoneListElements(new DateTimeZone[]{});
        setOnTimeZoneElementClickListener(new ListItemClickListener());
    }

    private void setTimeZoneListElements(DateTimeZone[] timeZones) {
        ListView list = (ListView) findViewById(R.id.citiesList);
        ListAdapter adapter = new MyAdapter(this, timeZones);
        list.setAdapter(adapter);
    }

    private void informWhenFoundNoTimeZone(DateTimeZone[] timeZones) {
        if (timeZones.length == 0) {
            Toaster.showToast(this, "Did not found anything.", Toast.LENGTH_SHORT);
        }
    }

    private void setOnTimeZoneElementClickListener(ListItemClickListener listener) {
        ListView timezones = (ListView) findViewById(R.id.citiesList);
        timezones.setOnItemClickListener(listener);
    }

    private void sendMessageToService(String searchPhrase) {
        try {
            timeServiceController.sendMessage(TimeService.SEARCH_CITY, searchPhrase);
        } catch (RemoteException e) {
            Log.e("Send Message", "Could not send message to service.");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        timeServiceController.unbindService(this);
    }

    private void startDatePickerActivity(boolean finishThisActivity) {
        String currentTimeZone = timeZonesController.getCurrentTimeZone();

        Bundle bundle = new BundleBuilder()
                .withString(Keys.TIMEZONE_NAME.getKey(), currentTimeZone)
                .build();

        Intent intent = new Intent(this, DatePickerActivity.class);
        intent.putExtras(bundle);
        startActivity(intent, bundle);

        if (finishThisActivity) {
            finish();
        }
    }

    public void onSetDateButtonClick(View view) {
        if (timeZonesController.isTimeZoneSet()) {
            startDatePickerActivity(true);
        } else {
            Toaster.showToast(this, "Choose Timezone", Toast.LENGTH_SHORT);
        }
    }

    public void onSearchTimeZoneButtonClick(View view) {
        String searchPhrase = ViewUtils.getTextFromViewElement(R.id.searchField, this);
        DateTimeZone[] timeZonesByName = timeZonesController.findTimeZonesByName(searchPhrase);
        informWhenFoundNoTimeZone(timeZonesByName);
        setTimeZoneListElements(timeZonesByName);
        Log.i("Search Timezones", "Found " + timeZonesByName.length + " timezones.");
    }

    private void setDateToTextView(MessageWrapper wrappedMessage) {
        String timeZoneName = wrappedMessage.getTimeZoneName();
        String date = wrappedMessage.getDateAsAString();
        ViewUtils.setTextFromViewElement(R.id.chosenTimeValueLabel, date, this);
        ViewUtils.setTextFromViewElement(R.id.timeZoneNameValueLabel, timeZoneName, this);
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
            MessageWrapper wrappedMessage = MessageWrapper.getWrappedMessage(message);
            setDateToTextView(wrappedMessage);
            timeZonesController.setCurrentTimeZone(wrappedMessage.getTimeZoneName());
        }
    }

}