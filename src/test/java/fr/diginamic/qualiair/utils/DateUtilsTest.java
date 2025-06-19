package fr.diginamic.qualiair.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {

    @Test
    void toLocalDate_shouldConvertValidString() {
        String input = "2024-05-01";
        LocalDate expected = LocalDate.of(2024, 5, 1);
        assertEquals(expected, DateUtils.toLocalDate(input));
    }

    @Test
    void toLocalDate_shouldThrowException_onInvalidFormat() {
        String input = "01-05-2024";
        assertThrows(DateTimeParseException.class, () -> DateUtils.toLocalDate(input));
    }

    @Test
    void toString_shouldFormatLocalDate() {
        LocalDate input = LocalDate.of(2025, 6, 15);
        String expected = "2025-06-15";
        assertEquals(expected, DateUtils.toString(input));
    }

    @Test
    void toString_shouldFormatLocalDateTime() {
        LocalDateTime input = LocalDateTime.of(2025, 6, 15, 14, 30);
        String expected = "2025-06-15 14:30";
        assertEquals(expected, DateUtils.toString(input));
    }

    @Test
    void toLocalDateTime_shouldConvertUnixTimestamp() {
        long timestamp = 1718278200L; // Correspond à 2024-06-13 15:30:00 dans certaines zones
        LocalDateTime result = DateUtils.toLocalDateTime(timestamp);
        assertNotNull(result);
    }

    @Test
    void toLocalDateTime_shouldConvertUnixString() {
        String timestamp = "1718278200";
        LocalDateTime result = DateUtils.toLocalDateTime(timestamp);
        assertNotNull(result);
    }

    @Test
    void isValidDateFormat_shouldReturnTrue_onValidDate() {
        assertTrue(DateUtils.isValidDateFormat("2023-12-31"));
    }

    @Test
    void isValidDateFormat_shouldReturnFalse_onInvalidDate() {
        assertFalse(DateUtils.isValidDateFormat("31-12-2023"));
        assertFalse(DateUtils.isValidDateFormat("2023/12/31"));
        assertFalse(DateUtils.isValidDateFormat("not a date"));
    }
}
