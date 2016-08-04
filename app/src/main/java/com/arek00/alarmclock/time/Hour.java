package com.arek00.alarmclock.time;

import lombok.Getter;

public class Hour {

    @Getter private int hour;
    @Getter private int minute;
    @Getter private int second;

    public Hour(int hour, int minute, int second) {

        if (isCorrectHour(hour)) {
            this.hour = hour;
        }
        if (isCorrectMinute(minute)) {
            this.minute = minute;
        }
        if (isCorrectSecond(second)) {
            this.second = second;
        }
    }

    @Override
    public String toString() {
        return String.format("%d - %d : %d", this.hour, this.minute, this.second);
    }

    private boolean isCorrectHour(int hour) {
        if (hour < 0 && hour > 23) {
            throw new IllegalArgumentException("Błędna wartość godziny");
        }

        return true;
    }

    private boolean isCorrectMinute(int minute) {
        if (minute < 0 && minute > 59) {
            throw new IllegalArgumentException("Błędna wartość minut");
        }

        return true;
    }

    private boolean isCorrectSecond(int second) {
        if (second < 0 && second > 23) {
            throw new IllegalArgumentException("Błędna wartość sekund");
        }

        return true;
    }


}
