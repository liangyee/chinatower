package com.vastio.util;

import java.time.*;
import java.util.Date;

/**
 * 日期工具类
 * <p>
 * Created by xlch at 2018/4/18
 */
public final class DateUtil {

    private DateUtil() {

    }
    public static LocalDateTime longToLocalDateTime(long time) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
    }

    public static ZonedDateTime getStartOfDate(long time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        return localDateTime.toLocalDate().atStartOfDay(ZoneId.systemDefault());
    }

    public static ZonedDateTime getStartOfDate(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate().atStartOfDay(ZoneId.systemDefault());
    }

    public static LocalDateTime startOfLocalDateTime(LocalDateTime localDateTime) {
        return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIN);
    }

    public static LocalDateTime endOfLocalDateTime(LocalDateTime localDateTime) {
        return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MAX);
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
