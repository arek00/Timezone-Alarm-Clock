package com.arek00.alarmclock.time;


import com.arek00.alarmclock.utils.StringUtil;
import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.joda.time.DateTimeZone;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JodaTimezone {

    private static String[] availableTimeZones;

    public static String[] getAvailableTimeZones() {
        initialize();
        return availableTimeZones;
    }

    public static void initialize() {
        if (availableTimeZones == null) {
            Set<String> availableIDs = DateTimeZone.getAvailableIDs();
            String[] timeZonesArray = new String[availableIDs.size()];
            availableTimeZones = availableIDs.toArray(timeZonesArray);
        }
    }

    public static DateTimeZone[] findTimeZonesByName(String searchPhrase) {
        initialize();

        Set<DateTimeZone> matchedTimeZones = Sets.newHashSet();

        for (String timeZoneId : availableTimeZones) {

            if (StringUtil.containsIgnoreCaseAndSpaces(searchPhrase, timeZoneId)) {
                matchedTimeZones.add(DateTimeZone.forID(timeZoneId));
            }
        }

        return matchedTimeZones.toArray(new DateTimeZone[matchedTimeZones.size()]);
    }
}
