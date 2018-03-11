package utils;

import android.content.Context;

import java.util.ArrayList;

import data.DbLab;
import model.ScheduleItem;
import model.TimePoint;

public class AlarmHelper {

    public static void runSchedule(Context context, ScheduleItem scheduleItem) {

        DbLab dbLab = DbLab.getLab(context);
        ArrayList<TimePoint> timePoints = dbLab.getTimePointList(scheduleItem.getID());

        for (TimePoint timePoint : timePoints) {

            int hour = timePoint.getHour();
            int minute = timePoint.getMinute();

            int hourTrigger = hour == 0 ? 1 : hour;
            int minuteTrigger = minute == 0 ? 1 : minute;

            long trigger = 1000 * minuteTrigger * hourTrigger;
            Alarm.setAlarm(context, timePoint.getID(), trigger);
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
