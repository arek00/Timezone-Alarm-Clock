package com.arek00.alarmclock.time;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTimeZone;
import org.joda.time.tz.Provider;
import org.junit.Test;

import java.util.Set;

@Slf4j
public class JodaTimezonesTest {

    @Test
    public void shouldDisplayJodaTimezones() {
        Set<String> availableIDs = DateTimeZone.getAvailableIDs();

        for (String id : availableIDs) {
            log.info(id);
            DateTimeZone timeZone = DateTimeZone.forID(id);

            int offset = timeZone.getOffset(0l);
            double offsetInHours = TimeConverter.getMillisConverter().toHours(offset);
            log.info("Offset: " + offsetInHours);
        }
    }

    private void displayAvailableIds(Provider provider) {
        Set<String> availableIDs = provider.getAvailableIDs();
        log.info(provider.getClass().getName());
        for (String id : availableIDs) {
            log.info(id);
            log.info("Offset: " + DateTimeZone.forID(id).getOffset(0l));
        }
    }
}