package fr.diginamic.qualiair.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartementUtilsTest {

    @Test
    void normalizeCodeDetp_shouldReturnNullIfNullInput() {
        assertNull(DepartementUtils.normalizeCodeDetp(null));
    }

    @Test
    void normalizeCodeDetp_shouldReturnNullIfEmpty() {
        assertNull(DepartementUtils.normalizeCodeDetp(""));
        assertNull(DepartementUtils.normalizeCodeDetp("   "));
    }

    @Test
    void normalizeCodeDetp_shouldReturnSameIfAlreadyTwoOrMoreChars() {
        assertEquals("10", DepartementUtils.normalizeCodeDetp("10"));
        assertEquals("2A", DepartementUtils.normalizeCodeDetp("2A"));
        assertEquals("123", DepartementUtils.normalizeCodeDetp("123"));
    }

    @Test
    void normalizeCodeDetp_shouldPadIfOnlyOneChar() {
        assertEquals("01", DepartementUtils.normalizeCodeDetp("1"));
        assertEquals("09", DepartementUtils.normalizeCodeDetp("9"));
    }
}
