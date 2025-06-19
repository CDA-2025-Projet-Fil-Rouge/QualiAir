package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.exception.ParsedDataException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordonneeUtilsTest {

    @Test
    void toKey_shouldReturnConcatenatedLatLon() {
        double lat = 48.8566;
        double lon = 2.3522;
        String expected = "48.8566|2.3522";
        assertEquals(expected, CoordonneeUtils.toKey(lat, lon));
    }

    @Test
    void toDouble_shouldConvertValidString() throws ParsedDataException {
        assertEquals(12.34, CoordonneeUtils.toDouble("12.34"));
        assertEquals(-56.78, CoordonneeUtils.toDouble("  -56.78 "), 0.0001);
    }

    @Test
    void toDouble_shouldThrowException_onNullInput() {
        ParsedDataException exception = assertThrows(ParsedDataException.class,
                () -> CoordonneeUtils.toDouble(null));
        assertEquals("Impossible to convert coordinates to double", exception.getMessage());
    }

    @Test
    void toDouble_shouldThrowException_onEmptyInput() {
        ParsedDataException exception = assertThrows(ParsedDataException.class,
                () -> CoordonneeUtils.toDouble("  "));
        assertEquals("Impossible to convert coordinates to double", exception.getMessage());
    }

    @Test
    void toDouble_shouldThrowNumberFormatException_onInvalidInput() {
        assertThrows(NumberFormatException.class, () -> CoordonneeUtils.toDouble("abc"));
    }
}
