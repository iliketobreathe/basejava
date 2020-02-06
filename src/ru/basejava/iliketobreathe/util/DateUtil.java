package ru.basejava.iliketobreathe.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate parse(String date) {
        if (date == null || date.trim().length() == 0 || "По настоящее время".equals(date)) {
            return NOW;
        }
        return LocalDate.parse(date, FORMATTER);
    }

    public static String write(LocalDate date) {
        if (date == null) {
            return "";
        } else if (date.equals(NOW)) {
            return "По настоящее время";
        }
        return date.format(FORMATTER);
    }
}
