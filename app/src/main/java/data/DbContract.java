package data;

import android.provider.BaseColumns;

final class DbContract {

    static final class ScheduleListEntry implements BaseColumns {
        static final String TABLE_NAME = "scheduleList";
        static final String COLUMN_TITLE = "title";
        static final String COLUMN_SORT_ORDER = "sortOrder";
        static final String COLUMN_IS_RUNNING = "isRunning";

    }

    static final class TimePointListEntry implements BaseColumns {
        static final String TABLE_NAME = "timePointList";
        static final String COLUMN_TITLE = "title";
        static final String COLUMN_HOUR = "hour";
        static final String COLUMN_MINUTE = "minute";
        static final String COLUMN_SCHEDULE_ID = "scheduleID";

    }
}
