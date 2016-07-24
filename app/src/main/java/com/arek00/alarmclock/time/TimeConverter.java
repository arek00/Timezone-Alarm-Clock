package com.arek00.alarmclock.time;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeConverter {

    private static final int MILLIS_IN_SECOND = 1000;
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int MINUTES_IN_HOUR = 60;

    @Getter private static final Converter millisConverter = new Milliseconds();
    @Getter private static final Converter secondsConverter = new Seconds();
    @Getter private static final Converter minutesConverter = new Minutes();
    @Getter private static final Converter hoursConverter = new Hours();

    public interface Converter {
        double toMillis(double value);

        double toSeconds(double value);

        double toMinutes(double value);

        double toHours(double value);
    }


    private static class Milliseconds implements Converter {

        @Override
        public double toMillis(double value) {
            return value;
        }

        @Override
        public double toSeconds(double value) {
            return value / MILLIS_IN_SECOND;
        }

        @Override
        public double toMinutes(double value) {
            return toSeconds(value) / SECONDS_IN_MINUTE;
        }

        @Override
        public double toHours(double value) {
            return toMinutes(value) / MINUTES_IN_HOUR;
        }
    }

    private static class Seconds implements Converter {

        @Override
        public double toMillis(double value) {
            return value * MILLIS_IN_SECOND;
        }

        @Override
        public double toSeconds(double value) {
            return value;
        }

        @Override
        public double toMinutes(double value) {
            return value / SECONDS_IN_MINUTE;
        }

        @Override
        public double toHours(double value) {
            return toMinutes(value) / 60;
        }
    }

    private static class Minutes implements Converter {

        @Override
        public double toMillis(double value) {
            double seconds = value * SECONDS_IN_MINUTE;
            return new Seconds().toMillis(seconds);
        }

        @Override
        public double toSeconds(double value) {
            return value * SECONDS_IN_MINUTE;
        }

        @Override
        public double toMinutes(double value) {
            return value;
        }

        @Override
        public double toHours(double value) {
            return value / MINUTES_IN_HOUR;
        }
    }

    private static class Hours implements Converter {

        @Override
        public double toMillis(double value) {
            double minutes = value * MINUTES_IN_HOUR;
            return new Minutes().toMillis(minutes);
        }

        @Override
        public double toSeconds(double value) {
            return toMillis(value) / MILLIS_IN_SECOND;
        }

        @Override
        public double toMinutes(double value) {
            return toSeconds(value) / SECONDS_IN_MINUTE;
        }

        @Override
        public double toHours(double value) {
            return value;
        }
    }
}
