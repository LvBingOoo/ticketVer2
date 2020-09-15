package com.cc.util.yc;

import android.util.Log;

/**
 * 自定义Log
 * 
 */
public class MyLog {

    private static final String TAG = "UnlockLog";

    public static void LogI(String msg) {
        Log.i(TAG, msg);
    }

    public static void LogE(String msg) {
        Log.e(TAG, msg);
    }
}
