package utils;

import android.content.Context;

import model.ScheduleItem;

public class AlarmHelper {

    public static void runSchedule(Context context, ScheduleItem scheduleItem) {

        new Alarm().setAlarm(context);
    }

    public static void stopSchedule(ScheduleItem scheduleItem) {




    }

}
