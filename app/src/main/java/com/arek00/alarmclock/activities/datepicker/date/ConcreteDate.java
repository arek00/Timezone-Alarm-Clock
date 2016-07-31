package com.arek00.alarmclock.activities.datepicker.date;

import lombok.Getter;
import org.joda.time.DateTime;

public class ConcreteDate extends PickedDate {

    @Getter private DateTime dateTime;

    public ConcreteDate(DateTime time, PickedDateType type) {
        super(type);
        this.dateTime = time;
    }
}
