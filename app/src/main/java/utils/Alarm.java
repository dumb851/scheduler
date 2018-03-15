package utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

public class Alarm extends BroadcastReceiver {

    private static final String ALARM_ID = "ALARM_ID";

    @Override
    public void onReceive(Context context, Intent intent) {

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        Toast.makeText(context, "Alarm !yeaph!!!!!", Toast.LENGTH_LONG).show();

        int timePointID = intent.getIntExtra(ALARM_ID, -1);

        //TODO fill notification properties, don't know how yet
        //getNotificationProperties maybe, but getting from TimePoint is better, not sure
        //think about it

        if (timePointID != -1) {
//            NotificationHelper notificationHelper = new NotificationHelper(context, timePointID);
//            notificationHelper.getNotificationProperties()
        }



        Log.i("MeLog", "onReceive: id=" + timePointID);

        wl.release();
    }

    public static void setAlarm(Context context, int requestCode, long trigger) {

        Intent intent = new Intent(context, Alarm.class);
        intent.putExtra(ALARM_ID, requestCode);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                trigger,
                pendingIntent
        );

        Log.i("MeLog", "setExact: id=" + requestCode + " trigger=" + trigger);
    }

    public static void cancelAlarm(Context context, int requestCode) {

        Intent intent = new Intent(context, Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Log.i("MeLog", "cancel: id=" + requestCode);
        alarmManager.cancel(pendingIntent);
    }
}