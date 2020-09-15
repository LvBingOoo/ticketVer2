package com.xhh.ticketver2.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;

import java.util.Calendar;

/**
 * Created by cx on 2017/5/9.
 */

public class TimeDiaglog {
    private static DatePickerDialog datePickerDialog;
    private static TimePickerDialog timePickerDialog;
    public static void dateDialogShow(Context mContent, DatePickerDialog.OnDateSetListener listener){
        Calendar c = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(mContent,listener,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)){
            @Override
            protected void onStop() {//取消触发两次onDateSet
//                super.onStop();
            }
        };
        datePickerDialog.show();
    }
    public static void dateDialogShow(Context mContent, TimePickerDialog.OnTimeSetListener listener){
        Calendar c = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(mContent,listener,c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),true){
            @Override
            protected void onStop() {//取消触发两次ontimeset
//                super.onStop();
            }
        };
        timePickerDialog.show();
    }
}
