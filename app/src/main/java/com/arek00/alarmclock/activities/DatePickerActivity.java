package com.arek00.alarmclock.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import com.arek00.alarmclock.R;
import com.arek00.alarmclock.activities.datepicker.DatePickerController;

public class DatePickerActivity extends Activity {

    private RelativeLayout periodicAlarmLayout;
    private RelativeLayout simpleAlarmLayout;
    private RelativeLayout concreteAlarmLayout;
    private DatePickerController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_picker);
        initializeLayouts();
        controller = new DatePickerController();
        controller.setNormalType();
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
}
