package com.example.apollo.util;

import android.util.Log;

import com.eonliu.apollo.Apollo;

public class Logger {

    private static final String TAG = "Apollo-Sample";

    public static void d(String message) {
        if (Apollo.logEnable()) {
            Log.d(TAG, message);
        }
    }

    public static void e(String message) {
        if (Apollo.logEnable()) {
            Log.e(TAG, message);
        }
    }
}
