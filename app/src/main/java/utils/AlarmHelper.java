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

        for (TimePoint timePoint :
                timePoints) {
            new Alarm().setAlarm(context, timePoint.getHour(), timePoint.getMinute());
        }
    }

    public static void stopSchedule(Context context, ScheduleItem scheduleItem) {

        new Alarm().cancelAlarm(context);


    }

}
