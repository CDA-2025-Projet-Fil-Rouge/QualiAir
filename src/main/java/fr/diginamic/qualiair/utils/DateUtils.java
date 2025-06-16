package fr.diginamic.qualiair.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    private DateUtils() {
    }

    /**
     * Converts a string in yyyy-MM-dd format to LocalDate
     *
     * @param dateStr date string in yyyy-MM-dd format
     * @return LocalDate
     * @throws DateTimeParseException if the string is not in the correct format
     */
    public static LocalDate toLocalDate(String dateStr) {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    /**
     * Converts a LocalDate to string in yyyy-MM-dd format
     *
     * @param date LocalDate to convert
     * @return date string in yyyy-MM-dd format
     */
    public static String toString(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    /**
     * Validates if a string is in yyyy-MM-dd format
     *
     * @param dateStr date string to validate
     * @return true if the string is in correct format
     */
    public static boolean isValidDateFormat(String dateStr) {
        try {
            LocalDate.parse(dateStr, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
