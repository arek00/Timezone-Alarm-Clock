package com.arek00.alarmclock.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.arek00.alarmclock.R;
import com.arek00.alarmclock.activities.datepicker.DatePickerController;
import com.arek00.alarmclock.connections.TimeServiceConnection;
import com.arek00.alarmclock.handlers.IncomingMessagesAdapter;
import com.arek00.alarmclock.handlers.IncomingMessagesHandler;
import com.arek00.alarmclock.messages.Keys;
import com.arek00.alarmclock.services.TimeService;

public class DatePickerActivity extends Activity {

    private RelativeLayout periodicAlarmLayout;
    private RelativeLayout simpleAlarmLayout;
    private RelativeLayout concreteAlarmLayout;
    private DatePickerController controller;
    private ServiceConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_picker);
        setLabels(getIntent());
        initializeService();
        initializeLayouts();
        controller = new DatePickerController();
        controller.setNormalType();

    }

    private void setLabels(Intent intent) {
        Bundle bundle = intent.getExtras();
        String timezoneName = bundle.getString(Keys.TIMEZONE_NAME.getKey());
        TextView timeZoneNameLabel = (TextView) findViewById(R.id.timeZoneIdValueLabel);
        timeZoneNameLabel.setText(timezoneName);
    }

    private void initializeService() {
        IncomingMessagesHandler handler = new IncomingMessagesHandler();
        IncomingMessagesHandler.ObservableHandler listeningHandler = handler.getListeningHandler();
        listeningHandler.registerListener(new IncomingMessagesListener());
        connection = new TimeServiceConnection(new Messenger(handler));
        bindService(new Intent(this, TimeService.class), connection, Context.BIND_AUTO_CREATE);
    }

    private void setDateLabels(Message message) {
        Bundle data = message.getData();
        TextView timeZoneIdLabel = (TextView) findViewById(R.id.timeZoneIdValueLabel);
        TextView timeZoneDate = (TextView) findViewById(R.id.timeZoneHourValueLabel);

        timeZoneIdLabel.setText(data.getString(Keys.TIMEZONE_NAME.getKey()));
        timeZoneDate.setText(data.getString(Keys.DATE_AS_STRING.getKey()));
    }

    private void initializeLayouts() {
        this.periodicAlarmLayout = (RelativeLayout) findViewById(R.id.periodicAlarmLayout);
        this.simpleAlarmLayout = (RelativeLayout) findViewById(R.id.simpleAlarmLayout);
        this.concreteAlarmLayout = (RelativeLayout) findViewById(R.id.concreteDateLayout);

        hideAlarmLayouts();
        simpleAlarmLayout.setVisibility(View.VISIBLE);
    }

    public void onSimpleAlarmButtonClick(View view) {
        hideAlarmLayouts();
        controller.setNormalType();
        simpleAlarmLayout.setVisibility(View.VISIBLE);
    }

    public void onPeriodicAlarmLayout(View view) {
        hideAlarmLayouts();
        controller.setPeriodicType();
        periodicAlarmLayout.setVisibility(View.VISIBLE);
    }

    public void onConcreteAlarmButtonClick(View view) {
        hideAlarmLayouts();
        controller.setConcreteDateType();
        concreteAlarmLayout.setVisibility(View.VISIBLE);
    }

    private void hideAlarmLayouts() {
        simpleAlarmLayout.setVisibility(View.GONE);
        periodicAlarmLayout.setVisibility(View.GONE);
        concreteAlarmLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(this.connection);
    }

    public void onBackToTimeZoneSelectionClick(View view) {
        Intent intent = new Intent(this, ChooseTimeZoneActivity.class);
        startActivity(intent);
        finish();
    }

    private class IncomingMessagesListener extends IncomingMessagesAdapter {
        @Override
        public void onDateMessageReceived(Message message) {
            setDateLabels(message);
        }
    }
}
