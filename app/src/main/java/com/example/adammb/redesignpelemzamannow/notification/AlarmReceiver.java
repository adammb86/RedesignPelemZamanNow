package com.example.adammb.redesignpelemzamannow.notification;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.adammb.redesignpelemzamannow.R;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver{
    public static final int NOTIFICATION_ID=1;
    private NotificationCompat.Builder notification;

    public void setAlarm(Context context){
        AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar=Calendar.getInstance();
        Intent intent=new Intent(context,AlarmReceiver.class);

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,15);
        calendar.set(Calendar.MINUTE,20);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        Log.e("NOTIFICATION", "setNotification: diset pada " );
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        setAlarm(context);
        sendNotification(context);

        Log.e("onReceive","method ini dipanggil");
    }

    public void sendNotification(Context context){
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://codelabs.unikom.ac.id"));

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);

        notification=(NotificationCompat.Builder)new NotificationCompat.Builder(context,"My Notification")
                .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                .setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_notifications_white_24dp))
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(context.getResources().getString(R.string.content_text))
                .setAutoCancel(true);

        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notification.build());
    }
}
