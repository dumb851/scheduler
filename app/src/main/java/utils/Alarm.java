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

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        // Put here YOUR code.
        Toast.makeText(context, "Alarm !yeaph!!!!!", Toast.LENGTH_LONG).show();

        Log.i("MeLog", "onReceive: yeaph");

        wl.release();
    }

    public void setAlarm(Context context, int hour, int minute) {

        Intent intent = new Intent(context, Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
//                1000 * 5, pendingIntent); // Millisec * Second * Minute
        // TODO: 07.03.2018
        Log.i("MeLog", "setExact: yeaph");

        int hourTrigger = hour == 0 ? 1 : hour;
        int minuteTrigger = minute == 0 ? 1 : minute;

        long trigger = 1000 * minuteTrigger * hourTrigger;
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + trigger,
                pendingIntent
        );
    }

    public void cancelAlarm(Context context) {
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Log.i("MeLog", "cancel: yeaph");
        alarmManager.cancel(pendingIntent);
    }
}