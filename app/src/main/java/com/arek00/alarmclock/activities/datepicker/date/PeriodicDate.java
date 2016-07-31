package com.arek00.alarmclock.activities.datepicker.date;

import lombok.Getter;

import java.util.Set;

public class PeriodicDate extends PickedDate {

    @Getter private Set<Day> pickedDays;

    public PeriodicDate(Set<Day> pickedDays, PickedDateType type) {
        super(type);
        this.pickedDays = pickedDays;
    }
}
