package fr.diginamic.qualiair.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void shouldRemoveQuotes_whenStringContainsQuotes() {
        String input = "\"Hello\"";
        String expected = "Hello";
        String result = StringUtils.removeQuotes(input);
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnSameString_whenNoQuotes() {
        String input = "Bonjour";
        String result = StringUtils.removeQuotes(input);
        assertEquals(input, result);
    }

    @Test
    void shouldRemoveMultipleQuotes() {
        String input = "\"Hello\" \"World\"";
        String expected = "Hello World";
        String result = StringUtils.removeQuotes(input);
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnEmptyString_whenOnlyQuotes() {
        String input = "\"\"";
        String expected = "";
        String result = StringUtils.removeQuotes(input);
        assertEquals(expected, result);
    }

    @Test
    void shouldHandleEmptyString() {
        String result = StringUtils.removeQuotes("");
        assertEquals("", result);
    }
}
