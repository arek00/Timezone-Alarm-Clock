package com.arek00.alarmclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.arek00.alarmclock.content.City;

/**
 * Created by Admin on 2015-01-25.
 */
public class MyAdapter extends ArrayAdapter<City> {
    Context context;

    public MyAdapter(Context context, City[] cities) {
        super(context, R.layout.list_item, cities);
        this.context = context;
    }


    public View getView(int position, View view, ViewGroup group) {
        View line = view;

        if (line == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            line = inflater.inflate(R.layout.list_item, null);
        }

        City city = getItem(position);

        String cityName = city.getName();
        String timezone = "UTC: " + city.getUTCOffset();

        ((TextView) line.findViewById(R.id.cityField)).setText(cityName);
        ((TextView) line.findViewById(R.id.timezoneField)).setText(timezone);

        return line;
    }
}

