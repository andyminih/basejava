package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static String formatMMyyyy(LocalDate date) {
        return date.equals(NOW) ? "наст.вр." : date.format(DateTimeFormatter.ofPattern("MM/yyyy"));
    }

    public static LocalDate ofMMyyyy(String date) {
        return LocalDate.parse("01/" + date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
