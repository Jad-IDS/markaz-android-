package com.ids.markaz.utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import com.ids.markaz.controller.Activities.ActivitySplash;
import com.ids.markaz.controller.MyApplication;


public class MyExceptionHandler implements Thread.UncaughtExceptionHandler {

    private Activity activity;
    public MyExceptionHandler(Activity a) {
        activity = a;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        Crashlytics.log(Log.ERROR, "Exception: ", ex.getMessage());
        //FirebaseCrash.logcat(Log.ERROR, "Exception: ", ex.getMessage());

        Intent intent = new Intent(activity, ActivitySplash.class);
        intent.putExtra("crash", true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.instance.getBaseContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr = (AlarmManager) MyApplication.instance.getBaseContext().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);
        activity.finish();
        System.exit(2);
    }
}