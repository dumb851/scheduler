package data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import model.ScheduledTimer;
import model.TimePoint;

final public class DbLab {

    private static DbLab sDbLab;
    private SQLiteDatabase mDataBase;
    private static ArrayList<ScheduleItemListListener> sScheduleItemListListeners =
            new ArrayList<>();
    private static ArrayList<TimePointListListener> sTimePointListListeners =
            new ArrayList<>();

    // constructor
    private DbLab(Context context) {

        mDataBase = new DbHelper(context).getWritableDatabase();
    }

    public static DbLab getLab(Context context) {

        if (sDbLab == null) {
            sDbLab = new DbLab(context);
        }
        return sDbLab;
    }


    // ScheduleItemActivity

    public int saveScheduledTimer(ScheduledTimer scheduledTimer) {

        ContentValues values = new ContentValues();
        values.put(DbContract.ScheduleListEntry.COLUMN_TITLE, scheduledTimer.getTitle());
        values.put(DbContract.ScheduleListEntry.COLUMN_SORT_ORDER, scheduledTimer.getSortOrder());

        int isRunningInt = 0;
        if (scheduledTimer.isRunning()) {
            isRunningInt = 1;
        }

        values.put(DbContract.ScheduleListEntry.COLUMN_IS_RUNNING, isRunningInt);

        int resultID;

        if (scheduledTimer.getID() != ScheduledTimer.NO_ID) {

            resultID = scheduledTimer.getID();

            String whereClause = DbContract.ScheduleListEntry._ID + "=?";
            String[] whereArgs = {String.valueOf(scheduledTimer.getID())};

            mDataBase.update(DbContract.ScheduleListEntry.TABLE_NAME,
                    values,
                    whereClause,
                    whereArgs
            );

        } else {
            resultID = (int) mDataBase.insert(
                    DbContract.ScheduleListEntry.TABLE_NAME,
                    null,
                    values
            );
        }

        notifyScheduleItemListChanged();

        return resultID;
    }

    public void deleteSchedule(ScheduledTimer scheduledTimer) {

        String whereClause = DbContract.ScheduleListEntry._ID + "=?";
        String[] whereArgs = {String.valueOf(scheduledTimer.getID())};

        int result = mDataBase.delete(DbContract.ScheduleListEntry.TABLE_NAME,
                whereClause,
                whereArgs
        );

        notifyScheduleItemListChanged();

    }

    public ArrayList<ScheduledTimer> getScheduleItemList() {
        return getScheduleItemList(null, null);
    }

    public ScheduledTimer getScheduleItem(int ID) {

        String whereClause = DbContract.ScheduleListEntry._ID + "=?";
        String[] whereArgs = {String.valueOf(ID)};

        ArrayList<ScheduledTimer> itemArrayList = getScheduleItemList(whereClause, whereArgs);

        return itemArrayList.get(0);
    }

    private ArrayList<ScheduledTimer> getScheduleItemList(String whereClause, String[] whereArgs) {

        ArrayList<ScheduledTimer> itemArrayList = new ArrayList<>();

        Cursor cursor = mDataBase.query(
                DbContract.ScheduleListEntry.TABLE_NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                DbContract.ScheduleListEntry.COLUMN_SORT_ORDER
        );

        while (cursor.moveToNext()) {

            ScheduledTimer scheduledTimer = new ScheduledTimer();

            String title = cursor.getString(
                    cursor.getColumnIndex(
                            DbContract.ScheduleListEntry.COLUMN_TITLE
                    )
            );

            int ID = cursor.getInt(
                    cursor.getColumnIndex(
                            DbContract.ScheduleListEntry._ID
                    )
            );

            int isRunningInt = cursor.getInt(
                    cursor.getColumnIndex(
                            DbContract.ScheduleListEntry.COLUMN_IS_RUNNING
                    )
            );

            float sortOrder = cursor.getFloat(
                    cursor.getColumnIndex(
                            DbContract.ScheduleListEntry.COLUMN_SORT_ORDER
                    )
            );

            scheduledTimer.setID(ID);
            scheduledTimer.setTitle(title);

            if (isRunningInt == 1) {
                scheduledTimer.setIsRunning(true);
            }

            scheduledTimer.setSortOrder(sortOrder);
            itemArrayList.add(scheduledTimer);
        }

        cursor.close();

        return itemArrayList;
    }

    public static ScheduledTimer changeScheduleRunningState(int id) {
        ScheduledTimer scheduledTimer = sDbLab.getScheduleItem(id);

        scheduledTimer.setIsRunning(!scheduledTimer.isRunning());
        sDbLab.saveScheduledTimer(scheduledTimer);

        return scheduledTimer;
    }

    //// interface ScheduleItemListListener
    public interface ScheduleItemListListener {

        void onScheduleItemListChanged();

    }

    public static void registerScheduleItemListListener(ScheduleItemListListener listener) {
        sScheduleItemListListeners.add(listener);
    }

    private void notifyScheduleItemListChanged() {

        for (ScheduleItemListListener listener : sScheduleItemListListeners) {
            listener.onScheduleItemListChanged();
        }
    }

    public static void unregisterScheduleItemListListener(ScheduleItemListListener listener) {
        sScheduleItemListListeners.remove(listener);
    }


    // TimePoint
    public void saveTimePoint(TimePoint timePoint) {

        ContentValues values = new ContentValues();
        values.put(DbContract.TimePointListEntry.COLUMN_TITLE, timePoint.getTitle());
        values.put(DbContract.TimePointListEntry.COLUMN_SCHEDULE_ID, timePoint.getScheduleID());
        values.put(DbContract.TimePointListEntry.COLUMN_HOUR, timePoint.getHour());
        values.put(DbContract.TimePointListEntry.COLUMN_MINUTE, timePoint.getMinute());

        if (timePoint.getID() != 0) {

            String whereClause = DbContract.TimePointListEntry._ID + "=?";
            String[] whereArgs = {String.valueOf(timePoint.getID())};

            mDataBase.update(DbContract.TimePointListEntry.TABLE_NAME,
                    values,
                    whereClause,
                    whereArgs
            );

        } else {
            mDataBase.insert(
                    DbContract.TimePointListEntry.TABLE_NAME,
                    null,
                    values
            );
        }
    }

    public ArrayList<TimePoint> getTimePointList(int scheduleItemID) {

        String whereClause = DbContract.TimePointListEntry.COLUMN_SCHEDULE_ID + "=?";
        String[] whereArgs = {String.valueOf(scheduleItemID)};

        return getTimePointList(whereClause, whereArgs);

    }

    public TimePoint getTimePoint(int id) {

        String whereClause = DbContract.TimePointListEntry._ID + "=?";
        String[] whereArgs = {String.valueOf(id)};

        ArrayList<TimePoint> itemArrayList = getTimePointList(whereClause, whereArgs);

        return itemArrayList.get(0);

    }

    private ArrayList<TimePoint> getTimePointList(String whereClause, String[] whereArgs) {

        //!return addDemoTimePoint();

        ArrayList<TimePoint> itemArrayList = new ArrayList<>();

        //// TODO: 19.10.2017 orderBy
        String orderBy = null;

        Cursor cursor = mDataBase.query(
                DbContract.TimePointListEntry.TABLE_NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                orderBy
        );

        while (cursor.moveToNext()) {

            TimePoint item = new TimePoint();

            String title = cursor.getString(cursor
                    .getColumnIndex(DbContract.TimePointListEntry.COLUMN_TITLE));

            int ID = cursor.getInt(cursor
                    .getColumnIndex(DbContract.TimePointListEntry._ID));

            int scheduleID = cursor.getInt(cursor
                    .getColumnIndex(DbContract.TimePointListEntry.COLUMN_SCHEDULE_ID));

            int hour = cursor.getInt(cursor
                    .getColumnIndex(DbContract.TimePointListEntry.COLUMN_HOUR));

            int minute = cursor.getInt(cursor
                    .getColumnIndex(DbContract.TimePointListEntry.COLUMN_MINUTE));

            item.setID(ID);
            item.setTitle(title);
            item.setScheduleID(scheduleID);
            item.setHour(hour);
            item.setMinute(minute);

            itemArrayList.add(item);
        }

        cursor.close();

        return itemArrayList;

    }

    //// interface TimePointListListener
    public interface TimePointListListener {

        void timePointListChanged();

    }

    public static void registerTimePointListListener(TimePointListListener listener) {
        sTimePointListListeners.add(listener);
    }

    public static void unregisterTimePointListListener(TimePointListListener listener) {
        sTimePointListListeners.add(listener);
    }

    private void notifyTimePointListChanged() {

        for (TimePointListListener listener : sTimePointListListeners) {
            listener.timePointListChanged();
        }
    }

    //!
    public void addDemo() {

        ArrayList<ScheduledTimer> itemArrayList = new ArrayList<>();

        ScheduledTimer scheduledTimer1 = new ScheduledTimer();
        scheduledTimer1.setTitle("schedule 1");

        ScheduledTimer scheduledTimer2 = new ScheduledTimer();
        scheduledTimer2.setTitle("SCHEDULE 2");

        ScheduledTimer scheduledTimer3 = new ScheduledTimer();
        scheduledTimer3.setTitle("SCHEdule 3");

        ScheduledTimer scheduledTimer4 = new ScheduledTimer();
        scheduledTimer4.setTitle("scheDULE 4");

        ScheduledTimer scheduledTimer5 = new ScheduledTimer();
        scheduledTimer5.setTitle("schedule 5 with long name");

        ScheduledTimer scheduledTimer6 = new ScheduledTimer();
        scheduledTimer6.setTitle("по русски");

        ScheduledTimer scheduledTimer7 = new ScheduledTimer();
        scheduledTimer7.setTitle("");

        itemArrayList.add(scheduledTimer1);
        itemArrayList.add(scheduledTimer2);
        itemArrayList.add(scheduledTimer3);
        itemArrayList.add(scheduledTimer4);
        itemArrayList.add(scheduledTimer5);
        itemArrayList.add(scheduledTimer6);
        itemArrayList.add(scheduledTimer7);

        itemArrayList.clear();

    }

    public ArrayList<TimePoint> addDemoTimePoint() {

        ArrayList<TimePoint> itemArrayList = new ArrayList<>();

        TimePoint item1 = new TimePoint();
        item1.setTitle("TimePoint 1");

        itemArrayList.add(item1);
        itemArrayList.add(item1);

        itemArrayList.add(item1);
        itemArrayList.add(item1);
        itemArrayList.add(item1);
        itemArrayList.add(item1);
        itemArrayList.add(item1);

        itemArrayList.add(item1);
        itemArrayList.add(item1);
        itemArrayList.add(item1);
        itemArrayList.add(item1);
        itemArrayList.add(item1);

        itemArrayList.add(item1);
        itemArrayList.add(item1);
        itemArrayList.add(item1);
        itemArrayList.add(item1);
        itemArrayList.add(item1);
        itemArrayList.add(item1);
        itemArrayList.add(item1);
        itemArrayList.add(item1);
        itemArrayList.add(item1);
        itemArrayList.add(item1);

        return itemArrayList;
    }

}
