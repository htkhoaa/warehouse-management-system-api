package com.miuky.warehouse.util;

public class AppUtils {

    public static String createSku(String productName) {
        String prefix = productName.replaceAll("[^A-Z0-9]", "");

        if (prefix.isEmpty()) {
            prefix = productName.substring(0, Math.min(productName.length(), 3)).toUpperCase();
        }

        int randomNum = (int) (Math.random() * 9000) + 1000;
        return prefix + "-" + randomNum;
    }
}
