package com.arek00.alarmclock.time;

import android.util.Log;

/**
 * Created by Admin on 2015-01-25.
 */
public class CitySearcher {

    public Timezone citySearch(String name) {
        for (Timezone timezone : Timezone.values()) {
            String cityName = timezone.getDescription();
            if (name.toLowerCase().equals(cityName.toLowerCase())) {

                Log.i("CitySearcher", "Found City");


                return timezone;
            }
        }

        Log.i("CitySearcher", "Didn't Found City");

        return null;
    }
}