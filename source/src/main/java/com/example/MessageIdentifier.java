package com.example;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

public class MessageIdentifier implements Supplier<String> {

    private static final AtomicLong IDENTITY = new AtomicLong(0L);

    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Tokyo");

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_ZONED_DATE_TIME;

    @Override
    public String get() {
        final ZonedDateTime now = ZonedDateTime.now(ZONE_ID);
        return String.format("[%05d] - %s", IDENTITY.incrementAndGet(), now.format(FORMATTER));
    }

    public int hour() {
        final ZonedDateTime now = ZonedDateTime.now(ZONE_ID);
        return now.getHour();
    }
}
