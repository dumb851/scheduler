package data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import model.ScheduleItem;
import model.TimePoint;

final public class DbLab {

    private static DbLab sDbLab;
    private SQLiteDatabase mDataBase;
    private static int sSortOrder;
    private static ArrayList<ScheduleItemListListener> sScheduleItemListListeners =
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


    // ScheduleItem

    public void saveSchedule(ScheduleItem scheduleItem) {

        ContentValues values = new ContentValues();
        values.put(DbContract.ScheduleListEntry.COLUMN_TITLE, scheduleItem.getTitle());
        values.put(DbContract.ScheduleListEntry.COLUMN_SORT_ORDER, sSortOrder);

        int isRunningInt = 0;
        if (scheduleItem.isRunning()) {
            isRunningInt = 1;
        }

        values.put(DbContract.ScheduleListEntry.COLUMN_IS_RUNNING, isRunningInt);

        if (scheduleItem.getID() != 0) {

            String whereClause = DbContract.ScheduleListEntry._ID + "=?";
            String[] whereArgs = {String.valueOf(scheduleItem.getID())};

            mDataBase.update(DbContract.ScheduleListEntry.TABLE_NAME,
                    values,
                    whereClause,
                    whereArgs
            );

        } else {
            mDataBase.insert(
                    DbContract.ScheduleListEntry.TABLE_NAME,
                    null,
                    values
            );
        }

        notifyScheduleItemListChanged();
    }

    public ArrayList<ScheduleItem> getScheduleItemList() {
        return getScheduleItemList(null, null);
    }

    public ScheduleItem getScheduleItem(int ID) {

        String whereClause = DbContract.ScheduleListEntry._ID + "=?";
        String[] whereArgs = {String.valueOf(ID)};

        ArrayList<ScheduleItem> itemArrayList = getScheduleItemList(whereClause, whereArgs);

        return itemArrayList.get(0);
    }

    private ArrayList<ScheduleItem> getScheduleItemList(String whereClause, String[] whereArgs) {

        ArrayList<ScheduleItem> itemArrayList = new ArrayList<>();

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

            ScheduleItem scheduleItem = new ScheduleItem();

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

            scheduleItem.setID(ID);
            scheduleItem.setTitle(title);
            if (isRunningInt == 1) {
                scheduleItem.setIsRunning(true);
            }

            itemArrayList.add(scheduleItem);
        }

        cursor.close();

        return itemArrayList;
    }

    public static void changeScheduleRunningState(int id) {
        ScheduleItem scheduleItem = sDbLab.getScheduleItem(id);

        scheduleItem.setIsRunning(!scheduleItem.isRunning());
        sDbLab.saveSchedule(scheduleItem);

    }

    //// interface ScheduleItemListListener
    public interface ScheduleItemListListener{

        void scheduleItemListChanged();

    }

    public static void registerScheduleItemListListener(ScheduleItemListListener listener) {
        sScheduleItemListListeners.add(listener);
    }

    private void notifyScheduleItemListChanged() {

        for (ScheduleItemListListener listener : sScheduleItemListListeners) {
            listener.scheduleItemListChanged();
        }
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

//!        notifyScheduleItemListChanged();
    }

    public ArrayList<TimePoint> getTimePointList(int scheduleItemID) {
        //// TODO: 19.10.2017

        return null;
    }

    public TimePoint getTimePoint(int ID) {

        //// TODO: 19.10.2017

//        String whereClause = DbContract.ScheduleListEntry._ID + "=?";
//        String[] whereArgs = {String.valueOf(ID)};
//
//        ArrayList<ScheduleItem> itemArrayList = getScheduleItemList(whereClause, whereArgs);

        //return itemArrayList.get(0);

        return null;
    }

    private ArrayList<TimePoint> getTimePointList(String whereClause, String[] whereArgs) {

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

    //!
    public void addDemo() {

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

        itemArrayList.clear();

    }


}
