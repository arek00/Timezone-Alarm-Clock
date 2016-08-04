package com.arek00.alarmclock.services;

import android.app.Service;
import android.content.Intent;
import android.os.*;
import android.util.Log;
import com.arek00.alarmclock.messages.Keys;
import com.arek00.alarmclock.time.Hour;
import com.arek00.alarmclock.time.HourGenerator;
import com.arek00.alarmclock.time.TimeConverter;
import com.arek00.alarmclock.utils.BundleBuilder;
import org.joda.time.DateTimeZone;

import java.util.Timer;
import java.util.TimerTask;

public class TimeService extends Service {

    public static final int REGISTER_CLIENT = 1;
    public static final int SET_CITY = 2;
    public static final int SEARCH_CITY = 3;
    public static final int SEND_DATE = 4;

    private final long REFRESH_TIME = 1000;

    private boolean isStarted = false;
    private Messenger clientMessenger;
    private Messenger serviceMessenger = new Messenger(new IncomingHandler());

    private DateTimeZone currentTimezone;
    private HourGenerator hourGenerator = HourGenerator.getInstance();

    private Timer timer = new Timer();

    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {

        Log.i("TimeService", "Started service");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return serviceMessenger.getBinder();
    }

    private boolean findTimezone(Message message) {
        String timezoneKey = Keys.TIMEZONE_NAME.getKey();
        String timeZoneId = message.getData().getString(timezoneKey);
        DateTimeZone timeZone = createTimeZoneFromId(timeZoneId);

        if (timeZone == null) {
            return false;
        }

        this.currentTimezone = timeZone;
        Log.i("City has been set: ", timeZoneId + " UTC " + timeZone.getOffset(0l));
        return true;
    }

    private DateTimeZone createTimeZoneFromId(String id) {
        DateTimeZone timeZone = null;

        try {
            timeZone = DateTimeZone.forID(id);
        } catch (IllegalArgumentException exception) {
            Log.i("City has not been set ", "Could not found timezone with id: " + id);
        }

        return timeZone;
    }

    private void sendMessage() {
        String timeZoneName;
        Hour hour;
        timeZoneName = currentTimezone.getID();
        hour = hourGenerator.getCurrentHourInTimeZone(currentTimezone);

        try {
            Message message = Message.obtain(null, TimeService.SEND_DATE);
            Bundle bundle = createMessageBundle(timeZoneName, hour);
            message.setData(bundle);
            clientMessenger.send(message);
            Log.i("Time Service", " Sent " + hour.toString() + " message to activity");

        } catch (RemoteException e) {
            Log.i("Time Service", " Couldnt send message to activity");
        }
    }

    private Bundle createMessageBundle(String timeZoneName, Hour hour) {
        double offsetInHours = TimeConverter.getMillisConverter().toHours(currentTimezone.getOffset(0l));

        return new BundleBuilder()
                .withString(Keys.TIMEZONE_NAME.getKey(), timeZoneName)
                .withString(Keys.DATE_AS_STRING.getKey(), hour.toString())
                .withInteger(Keys.HOUR.getKey(), hour.getHour())
                .withInteger(Keys.MINUTE.getKey(), hour.getMinute())
                .withInteger(Keys.SECOND.getKey(), hour.getSecond())
                .withDouble(Keys.UTC_OFFSET.getKey(), offsetInHours)
                .build();
    }

    private void checkServiceIsStarted() {
        if (!isStarted)
            startTimeService();
    }

    private void startTimeService() {
        timer.schedule(new Schedule(), 0L, this.REFRESH_TIME);
        this.isStarted = true;
    }

    private void stopTimeService() {
        timer.cancel();
    }

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            //TODO handling incoming messages

            switch (message.what) {
                case TimeService.REGISTER_CLIENT:
                    clientMessenger = message.replyTo;
                    Log.i("TimeService", "Registered client");
                    break;
                case TimeService.SEARCH_CITY:
                    if (findTimezone(message)) {
                        checkServiceIsStarted();
                    }
                    break;
                default:
                    Log.i("Timezone unknown", Integer.toString(message.what));
                    break;
            }
        }
    }

    class Schedule extends TimerTask {

        @Override
        public void run() {
            sendMessage();
        }
    }

}
