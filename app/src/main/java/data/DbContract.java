package data;

import android.provider.BaseColumns;

class DbContract {

    static final class ScheduleListEntry implements BaseColumns {
        static final String TABLE_NAME = "scheduleList";
        static final String COLUMN_SCHEDULE_TITLE = "scheduleTitle";
        static final String COLUMN_SORT_ORDER = "sortOrder";
        static final String COLUMN_IS_RUNNING = "isRunning";

    }
}
