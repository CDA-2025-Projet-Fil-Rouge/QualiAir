package fr.diginamic.qualiair.utils;

import org.apache.commons.lang3.StringUtils;

public class DepartementUtils {
    public static String normalizeCodeDetp(String code) {
        if (code == null || code.trim().isEmpty()) return null;

        if (StringUtils.length(code) >= 2) {
            return code;
        } else {
            return StringUtils.leftPad(code, 2, "0");
        }
    }
}
