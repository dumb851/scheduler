package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import data.DbContract.*;


final class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "scheduler.db";
    private static final int DATABASE_VERSION = 1;

    // Constructor
    DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold ScheduleItem data
        final String SQL_CREATE_SCHEDULE_LIST_TABLE = "CREATE TABLE " + ScheduleListEntry.TABLE_NAME + " (" +
                ScheduleListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + ", " +
                ScheduleListEntry.COLUMN_TITLE + " TEXT" + ", " +
                ScheduleListEntry.COLUMN_SORT_ORDER + " INTEGER NOT NULL" + ", "+
                ScheduleListEntry.COLUMN_IS_RUNNING + " INTEGER NOT NULL" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_SCHEDULE_LIST_TABLE);

        // Create a table to hold TimePoint data
        final String SQL_CREATE_TIME_POINT_LIST_TABLE = "CREATE TABLE " + TimePointListEntry.TABLE_NAME + " (" +
                TimePointListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + ", " +
                TimePointListEntry.COLUMN_TITLE + " TEXT" + ", " +
                TimePointListEntry.COLUMN_SCHEDULE_ID + " INTEGER NOT NULL" + ", "+
                TimePointListEntry.COLUMN_HOUR + " INTEGER NOT NULL" + ", "+
                TimePointListEntry.COLUMN_MINUTE + " INTEGER NOT NULL" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_TIME_POINT_LIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.

        // temporarily
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ScheduleListEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

        //!TODO should recalculate COLUMN_SORT_ORDER
    }

}
