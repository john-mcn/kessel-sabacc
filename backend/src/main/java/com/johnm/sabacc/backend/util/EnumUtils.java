package com.johnm.sabacc.backend.util;

import com.johnm.sabacc.backend.domain.components.CardFamily;

public class EnumUtils {
    public static String sanitiseString(String string) {
        return string.trim().toUpperCase().replaceAll(" ", "_");
    }
}
