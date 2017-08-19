package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import data.DbContract.*;


public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "scheduler.db";
    public static final int DATABASE_VERSION = 1;


    // Constructor
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold ScheduleList data
        final String SQL_CREATE_SCHEDULE_LIST_TABLE = "CREATE TABLE " + ScheduleListEntry.TABLE_NAME + " (" +
                ScheduleListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ScheduleListEntry.COLUMN_SCHEDULE_TITLE + " TEXT, " +
                ScheduleListEntry.COLUMN_SORT_ORDER + " INTEGER NOT NULL AUTOINCREMENT" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_SCHEDULE_LIST_TABLE);
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
    }
}
