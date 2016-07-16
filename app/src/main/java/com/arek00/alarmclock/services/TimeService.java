package com.arek00.alarmclock.services;

import android.app.Service;
import android.content.Intent;
import android.os.*;
import android.util.Log;
import com.arek00.alarmclock.content.City;
import com.arek00.alarmclock.content.CitySearcher;
import com.arek00.alarmclock.content.HourGenerator;

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

    private City currentCity;
    private CitySearcher searcher;
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

    private boolean searchCity(Message message) {
        String searchPhrase;
        CitySearcher searcher = new CitySearcher();
        searchPhrase = message.getData().getString("name");
        City searchedCity = searcher.citySearch(searchPhrase);


        if (searchedCity != null) {
            this.currentCity = searchedCity;
            Log.i("City has been set: ", searchedCity.getName() + " UTC " + searchedCity.getUTCOffset());
            return true;
        } else {
            Log.i("City has not been set: ", "Couldnt find city");
            return false;

        }

    }

    private void sendMessageToActivity() {
        String cityName;
        String date;

        cityName = currentCity.getName();
        date = hourGenerator.getCurrentHourInTimeZone(currentCity).toString();

        try {
            Message message = Message.obtain(null, TimeService.SEND_DATE);
            Bundle bundle = new Bundle();

            bundle.putString("cityName", cityName);
            bundle.putString("date", date);

            message.setData(bundle);
            clientMessenger.send(message);
            Log.i("Time Service", " Sent " + date + " message to activity");

        } catch (RemoteException e) {
            Log.i("Time Service", " Couldnt send message to activity");
        }
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
                    if (searchCity(message)) {
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
            sendMessageToActivity();
        }
    }

}
