package com.winthan.ybs.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by winthanhtike on 1/12/17.
 */

public class BusContract {

    public static final String CONTENT_AUTHORITY = "com.winthan.ybs";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_BUS = "buses";
    public static final String PATH_BUS_LINE = "busline";

    public BusContract() {
    }

    public static final class BusEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BUS);

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BUS;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BUS;

        public static final String TABLE_COLUMN = "buses";

        public static final String COLUMN_BUS_ID = BaseColumns._ID;
        public static final String COLUMN_BUS_NO = "bus_no";
        public static final String COLUMN_BUS_COLOR = "bus_color";
        public static final String COLUMN_BUS_STOP = "bus_stop";

    }

    public static final class BusLineEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BUS_LINE);

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BUS_LINE;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BUS_LINE;

        public static final String TABLE_COLUMN = "busline";

        public static final String COLUMN_BUS_STOP_ID = BaseColumns._ID;
        public static final String COLUMN_BUS_STOP_NAME = "bus_stop_name";
        public static final String COLUMN_BUS_NO = "bus_no";

    }


}
