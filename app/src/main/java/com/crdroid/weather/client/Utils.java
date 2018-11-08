package com.crdroid.weather.client;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
    }

    public static String getSystemProperty(String key, String defaultValue) {
        String value;

        try {
            value = (String) Class.forName("android.os.SystemProperties")
                    .getMethod("get", String.class).invoke(null, key);
            return (value == null || value.isEmpty()) ? defaultValue : value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static Boolean isBuildValid(Context context) {
        PackageManager pm = context.getPackageManager();
        if (pm != null && !pm.hasSystemFeature("com.crdroid.weather.client.SUPPORTED")) {
            return false;
        }
        if (getSystemProperty("ro.crdroid.version", "").isEmpty()) {
            return false;
        }
        if (getSystemProperty("ro.crdroid.releasetype", "").isEmpty()) {
            return false;
        }
        if (getSystemProperty("ro.crdroid.device", "").isEmpty()) {
            return false;
        }
        return true;
    }

    public static String getSystemRevision() {
        return getSystemProperty("ro.crdroid.weather.revision", "1");
    }
}
