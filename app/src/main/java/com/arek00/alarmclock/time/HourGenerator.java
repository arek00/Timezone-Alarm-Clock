package com.arek00.alarmclock.time;

import android.util.Log;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.TimeZone;

public class HourGenerator {

    private static HourGenerator instance = new HourGenerator();
    // private Calendar calendar = Calendar.getInstance();
    private TimeZone userTimeZone;
    private int userUTCOffset;

    private HourGenerator() {
        userTimeZone = TimeZone.getDefault();
        userUTCOffset = userTimeZone.getRawOffset();
        userUTCOffset = millisecondToHour(userUTCOffset);

        Log.i("UTC Timezone Offset: ", Integer.toString(userUTCOffset));
    }

    public static HourGenerator getInstance() {
        return instance;
    }

    public Hour getCurrentHourInTimeZone(DateTimeZone timezone) {
        DateTime currentTimeInTimeZone = new DateTime(timezone);

        return new Hour(currentTimeInTimeZone.getHourOfDay(),
                currentTimeInTimeZone.getMinuteOfHour(),
                currentTimeInTimeZone.getSecondOfMinute());
    }

    private double validateHour(double hour) {

        Log.i("Validate hour: ", Double.toString(hour));

        if (hour < 0) {
            return 24 + hour;
        } else if (hour > 23) {
            return hour % 24;
        }

        Log.i("Validate hour: ", Double.toString(hour));

        return hour;
    }

    private int millisecondToHour(int millisecond) {
        return (millisecond / 3600000);
    }
}
