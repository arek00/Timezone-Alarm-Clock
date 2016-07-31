package com.arek00.alarmclock.activities.datepicker.date;

import lombok.Getter;

/**
 * Base class for picked date
 */
public class PickedDate {

    @Getter private PickedDateType type;

    public PickedDate(PickedDateType type) {
        this.type = type;
    }
}
