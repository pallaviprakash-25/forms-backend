package com.project.forms.utils;

public final class Utils {
    public static String generateRandomId() {
        return String.valueOf((int)((Math.random() * (99999 - 10000)) + 10000));
    }
}
