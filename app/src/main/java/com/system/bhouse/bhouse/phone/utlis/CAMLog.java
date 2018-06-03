package com.system.bhouse.bhouse.phone.utlis;

import android.util.Log;

public class CAMLog {
    public static boolean DEBUG = false;
    private static final String TAG = "cam";

    public static void d(String str) {
        if (DEBUG) {
            Log.d(TAG, str);
        }
    }

    public static void d(String str, String str2) {
        if (DEBUG) {
            Log.d(str, str2);
        }
    }

    public static void e(String str) {
        if (DEBUG) {
            Log.e(TAG, str);
        }
    }

    public static void e(String str, String str2) {
        if (DEBUG) {
            Log.e(str, str2);
        }
    }

    public static void e(String str, String str2, Throwable th) {
        if (DEBUG) {
            Log.e(str, str2, th);
        }
    }

    public static void i(String str) {
        if (DEBUG) {
            Log.i(TAG, str);
        }
    }

    public static void i(String str, String str2) {
        if (DEBUG) {
            Log.i(str, str2);
        }
    }

    public static void v(String str) {
        if (DEBUG) {
            Log.v(TAG, str);
        }
    }

    public static void v(String str, String str2) {
        if (DEBUG) {
            Log.v(str, str2);
        }
    }
}
