package data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import model.ScheduleItem;

public class DbLab {

    private static DbLab sDbLab;
    private SQLiteDatabase mDataBase;

    // Constructor
    private DbLab(Context context) {

        mDataBase = new DbHelper(context).getWritableDatabase();
    }

    public static DbLab getLab(Context context) {

        if (sDbLab == null) {
            sDbLab = new DbLab(context);
        }
        return sDbLab;
    }

    public void insertSchedule(ScheduleItem scheduleItem) {

        //TODO

    }

    public ArrayList<ScheduleItem> getScheduleItemList() {

        //! temp test
        ArrayList<ScheduleItem> itemArrayList = new ArrayList<>();

        ScheduleItem scheduleItem1 = new ScheduleItem();
        scheduleItem1.setTitle("schedule 1");

        ScheduleItem scheduleItem2 = new ScheduleItem();
        scheduleItem2.setTitle("SCHEDULE 2");

        ScheduleItem scheduleItem3 = new ScheduleItem();
        scheduleItem3.setTitle("SCHEdule 3");

        ScheduleItem scheduleItem4 = new ScheduleItem();
        scheduleItem4.setTitle("scheDULE 4");

        ScheduleItem scheduleItem5 = new ScheduleItem();
        scheduleItem5.setTitle("schedule 5 with long name");

        ScheduleItem scheduleItem6 = new ScheduleItem();
        scheduleItem6.setTitle("по русски");

        ScheduleItem scheduleItem7 = new ScheduleItem();
        scheduleItem7.setTitle("");

        itemArrayList.add(scheduleItem1);
        itemArrayList.add(scheduleItem2);
        itemArrayList.add(scheduleItem3);
        itemArrayList.add(scheduleItem4);
        itemArrayList.add(scheduleItem5);
        itemArrayList.add(scheduleItem6);
        itemArrayList.add(scheduleItem7);

        return itemArrayList;
        //

        //return null;
    }

}