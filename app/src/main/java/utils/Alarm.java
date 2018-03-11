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

        int requestCode = intent.getIntExtra(ALARM_ID, -1);

        Log.i("MeLog", "onReceive: id=" + requestCode);

        wl.release();
    }

    public static void setAlarm(Context context, int requestCode, int hour, int minute) {

        Intent intent = new Intent(context, Alarm.class);
        intent.putExtra(ALARM_ID, requestCode);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        int hourTrigger = hour == 0 ? 1 : hour;
        int minuteTrigger = minute == 0 ? 1 : minute;

        long trigger = 1000 * minuteTrigger * hourTrigger;
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + trigger,
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