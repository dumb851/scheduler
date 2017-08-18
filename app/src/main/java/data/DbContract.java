package data;

import android.provider.BaseColumns;

//TODO
public class DbContract {

    public static final class ScheduleListEntry implements BaseColumns {
        public static final String TABLE_NAME = "scheduleList";
        public static final String COLUMN_SCHEDULE_TITLE = "scheduleTitle";
        public static final String COLUMN_SORT_ORDER = "sortOrder";
    }
}
