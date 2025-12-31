package com.johnm.sabacc.backend.util;

import com.johnm.sabacc.backend.domain.components.CardFamily;

public class EnumUtils {
    public static String sanitiseString(String string) {
        return string.trim().toUpperCase().replaceAll(" ", "_");
    }

    public static String sanitiseStringFromEnum(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase().replaceAll("_", " ");
    }
}
