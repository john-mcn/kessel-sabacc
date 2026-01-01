package com.johnm.sabacc.backend.util;

import java.util.Arrays;
import java.util.List;

public class EnumUtils {
    public static String sanitiseString(String string) {
        return string.trim().toUpperCase().replaceAll(" ", "_");
    }

    public static String sanitiseStringFromEnum(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase().replaceAll("_", " ");
    }

    public static String capitaliseEachWordFromEnum(String str) {
        String capitalised = str.toLowerCase().replaceAll("_", " ");
        List<String> words = Arrays.stream(capitalised.split(" "))
                .map(s -> s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase())
                .toList();
        return String.join(" ", words);
    }
}
