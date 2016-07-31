package com.arek00.alarmclock.activities.datepicker.date;

import com.arek00.alarmclock.time.Hour;
import lombok.Getter;

public class NormalDate extends PickedDate {

    @Getter private Hour hour;

    public NormalDate(Hour hour, PickedDateType type) {
        super(type);
        this.hour = hour;
    }
}
