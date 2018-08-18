package com.example.adammb.redesignpelemzamannow.notification;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.adammb.redesignpelemzamannow.BuildConfig;
import com.example.adammb.redesignpelemzamannow.R;
import com.example.adammb.redesignpelemzamannow.data.model.Pelem;
import com.example.adammb.redesignpelemzamannow.screen.main.MainActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class DailyReleaseReminder extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        final Calendar dateNow = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Pelem> pelemList = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + BuildConfig.TMDB_KEY;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String hasil = new String(responseBody);

                try {
                    JSONObject responseObject = new JSONObject(hasil);
                    JSONArray responseResult = responseObject.getJSONArray("results");
                    for (int i = 0; i < responseResult.length(); i++) {
                        JSONObject objectResult = responseResult.getJSONObject(i);
                        Pelem pelem = new Pelem();
                        pelem.setId(objectResult.getInt("id"));
                        pelem.setOriginal_title(objectResult.getString("original_title"));
                        pelem.setOverview(objectResult.getString("overview"));
                        pelem.setRelease_date(objectResult.getString("release_date"));
                        pelem.setPoster_path("http://image.tmdb.org/t/p/w185" + objectResult.getString("poster_path"));
                        pelem.setVote_average(objectResult.getDouble("vote_average"));
                        pelem.setVote_count(objectResult.getInt("vote_count"));
                        pelemList.add(pelem);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sendNotification(context, pelemList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        setAlarm(context);
    }

    public static void setAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        Intent intent = new Intent(context, DailyReleaseReminder.class);

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            else
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    public void sendNotification(Context context, ArrayList<Pelem> pelemList) {
        if(pelemList.size()==0) return;

        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        String content = pelemList.get(0).getOriginal_title() + " release hari ini";
        if(pelemList.size() > 1) {
            content = pelemList.get(0).getOriginal_title() + " dan " + (pelemList.size() - 1) + " film lainnya release hari ini";
        }

        NotificationCompat.Builder notification = (NotificationCompat.Builder) new NotificationCompat.Builder(context, "My Notification Release Reminder")
                .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                .setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notifications_white_24dp))
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(content)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification.build());
    }
}
