package com.project.forms.utils;

public final class Utils {

    /**
     * Method to generate random 5-digit numeric ID
     *
     * @return ID as a string
     */
    public static String generateRandomId() {
        return String.valueOf((int)((Math.random() * (99999 - 10000)) + 10000));
    }
}
