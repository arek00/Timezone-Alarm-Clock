package com.arek00.alarmclock.time;


import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.joda.time.DateTimeZone;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JodaTimezone {

    private static DateTimeZone[] availableTimeZones;

    public static DateTimeZone[] getAvailableTimeZones() {
        if (availableTimeZones == null) {
            initialize();
        }

        return availableTimeZones;
    }

    private static void initialize() {
        Set<DateTimeZone> dateTimeZones = Sets.newHashSet();
        Set<String> availableIDs = DateTimeZone.getAvailableIDs();

        for (String id : availableIDs) {
            DateTimeZone dateTimeZone = DateTimeZone.forID(id);
            dateTimeZones.add(dateTimeZone);
        }

        DateTimeZone[] timeZonesArray = new DateTimeZone[availableIDs.size()];
        JodaTimezone.availableTimeZones = dateTimeZones.toArray(timeZonesArray);
    }

    public static DateTimeZone[] findTimeZoneByName(String searchPhrase) {
        Set<DateTimeZone> matchedTimeZones = Sets.newHashSet();

        for (DateTimeZone timeZone : availableTimeZones) {
            String timeZoneId = timeZone.getID();

            if (timeZoneId.contains(searchPhrase)) {
                matchedTimeZones.add(timeZone);
            }
        }

        return matchedTimeZones.toArray(new DateTimeZone[matchedTimeZones.size()]);
    }
}
