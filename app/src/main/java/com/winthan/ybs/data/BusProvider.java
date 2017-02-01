package com.winthan.ybs.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.winthan.ybs.YBSApp;

/**
 * Created by winthanhtike on 1/12/17.
 */

public class BusProvider extends ContentProvider {

    public static final String LOG_TAG = BusProvider.class.getSimpleName();

    private static final int BUS = 100;

    private static final int BUS_LINE = 101;

    private static final UriMatcher sUriMather = new UriMatcher(UriMatcher.NO_MATCH);

    private BusDbHelper mDbHelper;

    static {

        sUriMather.addURI(BusContract.CONTENT_AUTHORITY, BusContract.PATH_BUS, BUS);
        sUriMather.addURI(BusContract.CONTENT_AUTHORITY, BusContract.PATH_BUS_LINE, BUS_LINE);

    }

    @Override
    public boolean onCreate() {
        mDbHelper = new BusDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        Cursor queryCursor;

        int match = sUriMather.match(uri);

        switch (match) {

            case BUS:
                queryCursor = database.query(BusContract.BusEntry.TABLE_COLUMN, strings, s, strings1, null, null, null, null);
                break;

            case BUS_LINE:
                queryCursor = database.query(BusContract.BusLineEntry.TABLE_COLUMN, strings, s, strings1, null, null, null, null);
                break;

            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);

        }

        Context context = getContext();
        if (context != null) {
            queryCursor.setNotificationUri(context.getContentResolver(), uri);
        }

        return queryCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        int match = sUriMather.match(uri);

        switch (match) {

            case BUS:
                return BusContract.BusEntry.CONTENT_LIST_TYPE;

            case BUS_LINE:
                return BusContract.BusLineEntry.CONTENT_LIST_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);

        }

    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMather.match(uri);

        Uri insertUri;

        switch (match) {

            case BUS:
                insertUri = insertBus(uri, contentValues);
                break;

            case BUS_LINE:
                insertUri = insertBusLine(uri, contentValues);
                break;

            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);

        }

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return insertUri;

    }

    private Uri insertBusLine(Uri uri, ContentValues contentValues) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long id = db.insert(BusContract.BusLineEntry.TABLE_COLUMN, null, contentValues);

        if (id > 0) {
            return ContentUris.withAppendedId(uri, id);
        } else {
            throw new SQLException("Failed to insert row into " + uri);
        }

    }

    private Uri insertBus(Uri uri, ContentValues values) {

        Integer busNo = values.getAsInteger(BusContract.BusEntry.COLUMN_BUS_NO);
        if (busNo != null && busNo < 0) {
            throw new IllegalArgumentException("Bus requires valid Bus No");
        }

        String busColor = values.getAsString(BusContract.BusEntry.COLUMN_BUS_COLOR);
        if (busColor == null) {
            throw new IllegalArgumentException("Bus requires valid Bus Color");
        }

        String busStopNames = values.getAsString(BusContract.BusEntry.COLUMN_BUS_STOP);
        if (busStopNames == null) {
            throw new IllegalArgumentException("Bus requires valid Bus Stop Names");
        }

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long id = db.insert(BusContract.BusEntry.TABLE_COLUMN, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        return ContentUris.withAppendedId(uri, id);

    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        int insertedCount = 0;

        try {

            db.beginTransaction();
            for (ContentValues cv : values) {
                long _id = db.insert(tableName, null, cv);
                if (_id > 0) {
                    insertedCount++;
                }
            }
            db.setTransactionSuccessful();

        }finally {
            db.endTransaction();
            db.close();
        }

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return insertedCount;

    }

    private String getTableName(Uri uri) {
        final int matchUri = sUriMather.match(uri);

        switch (matchUri) {
            case BUS:
                return BusContract.BusEntry.TABLE_COLUMN;
            case BUS_LINE:
                return BusContract.BusLineEntry.TABLE_COLUMN;

            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
