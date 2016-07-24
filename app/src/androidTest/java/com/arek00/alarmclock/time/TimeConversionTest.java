package com.arek00.alarmclock.time;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TimeConversionTest {

    @Test
    public void shouldConvertMillis() {
        //given
        TimeConverter.Converter millisConverter = TimeConverter.getMillisConverter();

        //when
        double second = millisConverter.toSeconds(1000d);
        double minute = millisConverter.toMinutes(1000d * 60);
        double hour = millisConverter.toHours(1000d * 60 * 60);

        //then
        assertTrue(second == 1d);
        assertTrue(minute == 1d);
        assertTrue(hour == 1d);
    }

    @Test
    public void shouldConvertSeconds() {
        //given
        TimeConverter.Converter secondsConverter = TimeConverter.getSecondsConverter();

        //when
        double millis = secondsConverter.toMillis(1d); //1000 ms
        double second = secondsConverter.toSeconds(1d); //1 sec
        double minute = secondsConverter.toMinutes(60); //1 min
        double hour = secondsConverter.toHours(60 * 60); //1 h

        //then
        assertTrue(millis == 1000d);
        assertTrue(second == 1d);
        assertTrue(minute == 1d);
        assertTrue(hour == 1d);
    }


}