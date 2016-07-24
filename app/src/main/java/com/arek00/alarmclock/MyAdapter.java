package com.arek00.alarmclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.arek00.alarmclock.time.TimeConverter;
import org.joda.time.DateTimeZone;

public class MyAdapter extends ArrayAdapter<DateTimeZone> {
    Context context;

    public MyAdapter(Context context, DateTimeZone[] zones) {
        super(context, R.layout.list_item, zones);
        this.context = context;
    }


    public View getView(int position, View view, ViewGroup group) {
        View line = view;

        if (line == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            line = inflater.inflate(R.layout.list_item, null);
        }

        DateTimeZone timeZone = getItem(position);

        String timezoneDescription = timeZone.getID();
        String timezoneOffset = getOffsetString(timeZone.getOffset(0l));

        ((TextView) line.findViewById(R.id.cityField)).setText(timezoneDescription);
        ((TextView) line.findViewById(R.id.timezoneField)).setText(timezoneOffset);

        return line;
    }

    private String getOffsetString(int offsetInMillis) {
        double offsetInHours = TimeConverter.getMillisConverter().toHours(offsetInMillis);

        return new StringBuilder()
                .append("UTC ")
                .append(getHoursString(offsetInHours))
                .toString();
    }

    private String getHoursString(double offsetInHours) {
        double offsetMinutes = offsetInHours - Math.floor(offsetInHours);
        String hoursString = String.format("%.0fh", offsetInHours);

        if (offsetInHours == 0d) {
            return "";
        }
        if (offsetInHours > 0d) {
            hoursString = "+" + hoursString;
        }
        if (offsetMinutes != 0d) {
            double minutes = TimeConverter.getHoursConverter().toMinutes(offsetMinutes);
            hoursString = String.format("%s %.0fmin", hoursString, minutes);
        }

        return hoursString;
    }
}

