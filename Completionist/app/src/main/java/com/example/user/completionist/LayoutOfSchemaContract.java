package com.example.user.completionist;

import android.provider.BaseColumns;

/**
 * Created by user on 24/03/2018.
 */

public final class LayoutOfSchemaContract {
    private LayoutOfSchemaContract() {
    }

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_COMPLETION_STATUS = "completion_status";
        public static final String COLUMN_NAME_PRIORITY_STATUS = "priority_status";
        public static final String COLUMN_NAME_DEADLINE = "deadline";
    }
}
