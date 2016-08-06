package com.arek00.alarmclock.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.arek00.alarmclock.R;
import com.arek00.alarmclock.services.TimeService;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startscreen);
        startService(new Intent(MainActivity.this, TimeService.class));

    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService(new Intent(MainActivity.this, TimeService.class));
    }

    public void onSetNewAlarmButtonClick(View view) {
        Log.i("Main activity", "Clicked onSetNewAlarmButton");
        Intent intent = new Intent(this, ChooseTimeZoneActivity.class);
        startActivity(intent);
    }


    public void onManageAlarmsButtonClick(View view) {
        Log.i("Main activity", "Clicked onManageAlarmsButton");
    }
}
