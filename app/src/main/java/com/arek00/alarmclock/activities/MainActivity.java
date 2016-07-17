package com.arek00.alarmclock.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.arek00.alarmclock.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.startscreen);
    }

    public void onSetNewAlarmButtonClick(View view) {
        Log.i("Main activity", "Clicked onSetNewAlarmButton");
        Intent intent = new Intent(this, SetAlarmActivity.class);
        startActivity(intent);
    }


    public void onManageAlarmsButtonClick(View view) {
        Log.i("Main activity", "Clicked onManageAlarmsButton");
    }
}
