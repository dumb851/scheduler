package utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import data.DbLab;
import model.ScheduleItem;
import model.TimePoint;

public class AlarmHelper {

    public static void runSchedule(Context context, ScheduleItem scheduleItem) {

        DbLab dbLab = DbLab.getLab(context);
        ArrayList<TimePoint> timePoints = dbLab.getTimePointList(scheduleItem.getID());

        long previousTrigger = -1;

        for (TimePoint timePoint : timePoints) {

            if (previousTrigger == -1) {
                previousTrigger = System.currentTimeMillis();
            }

            long trigger = previousTrigger
                    + TimeUnit.MINUTES.toMillis(timePoint.getMinute())
                    + TimeUnit.HOURS.toMillis(timePoint.getHour());

            Alarm.setAlarm(context, timePoint.getID(), trigger);

            previousTrigger = trigger;

        }
    }

    public static void stopSchedule(Context context, ScheduleItem scheduleItem) {

        DbLab dbLab = DbLab.getLab(context);
        ArrayList<TimePoint> timePoints = dbLab.getTimePointList(scheduleItem.getID());

        for (TimePoint timePoint :
                timePoints) {
            Alarm.cancelAlarm(context, timePoint.getID());
        }
    }

}
