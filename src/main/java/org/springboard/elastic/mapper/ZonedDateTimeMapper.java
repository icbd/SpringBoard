package org.springboard.elastic.mapper;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public interface ZonedDateTimeMapper {

    /**
     * ISO 8601: UTC Date Time
     * '2011-12-03T10:15:30Z'
     *
     * @param zonedDateTime
     * @return
     */
    default String localDateTimeToString(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) {
            return null;
        }

        return zonedDateTime.format(DateTimeFormatter.ISO_INSTANT);
    }
}
