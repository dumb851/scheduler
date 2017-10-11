package data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import model.ScheduleItem;

public class DbLab {

    private static DbLab sDbLab;
    private SQLiteDatabase mDataBase;
    private static int sSortOrder;
    private static ArrayList<ScheduleItemListListener> sScheduleItemListListeners =
            new ArrayList<>();

    // constructor
    private DbLab(Context context) {

        mDataBase = new DbHelper(context).getWritableDatabase();
    }

    // interface ScheduleItemListListener
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

    //
    public static DbLab getLab(Context context) {

        if (sDbLab == null) {
            sDbLab = new DbLab(context);
        }
        return sDbLab;
    }

    public void saveSchedule(ScheduleItem scheduleItem) {

        ContentValues values = new ContentValues();
        values.put(DbContract.ScheduleListEntry.COLUMN_SCHEDULE_TITLE, scheduleItem.getTitle());
        values.put(DbContract.ScheduleListEntry.COLUMN_SORT_ORDER, sSortOrder);

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

    //! TODO
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
                            DbContract.ScheduleListEntry.COLUMN_SCHEDULE_TITLE
                    )
            );

            int ID = cursor.getInt(
                    cursor.getColumnIndex(
                            DbContract.ScheduleListEntry._ID
                    )
            );

            scheduleItem.setID(ID);
            scheduleItem.setTitle(title);
            itemArrayList.add(scheduleItem);
        }

        cursor.close();

        return itemArrayList;
    }

}
