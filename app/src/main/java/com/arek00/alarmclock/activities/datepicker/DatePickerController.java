package com.arek00.alarmclock.activities.datepicker;

import com.arek00.alarmclock.activities.datepicker.date.PickedDateType;
import lombok.Getter;

public class DatePickerController {

    @Getter private PickedDateType currentPickerType = PickedDateType.NORMAL;

    public void setPeriodicType() {
        this.currentPickerType = PickedDateType.PERIODIC;
    }

    public void setNormalType() {
        this.currentPickerType = PickedDateType.NORMAL;
    }

    public void setConcreteDateType() {
        this.currentPickerType = PickedDateType.CONCRETE;
    }
}
