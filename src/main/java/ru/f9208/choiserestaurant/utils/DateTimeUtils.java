package ru.f9208.choiserestaurant.utils;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeUtils {
    // DB doesn't support LocalDate.MIN/MAX
    public static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    public static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);

    public static final LocalTime TOO_LATE = LocalTime.of(23, 59, 0);

    private DateTimeUtils() {
    }


}
