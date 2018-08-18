package com.example.adammb.redesignpelemzamannow.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DailyReminderReceiver.setAlarm(context);
        DailyReleaseReminder.setAlarm(context);
    }
}
