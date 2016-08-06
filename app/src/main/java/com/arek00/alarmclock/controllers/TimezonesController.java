package com.arek00.alarmclock.controllers;


import com.arek00.alarmclock.time.JodaTimezone;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTimeZone;

public class TimeZonesController {

    @Getter @Setter private String currentTimeZone;

    public TimeZonesController() {
        JodaTimezone.initialize();
    }

    public DateTimeZone[] findTimeZonesByName(String searchPhrase) {
        return JodaTimezone.findTimeZonesByName(searchPhrase);
    }

    public boolean isTimeZoneSet() {
        return !currentTimeZone.isEmpty();
    }


}
