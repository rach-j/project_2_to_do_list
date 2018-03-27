package com.example.user.completionist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 24/03/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "to_do_list.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + LayoutOfSchemaContract.FeedEntry.TABLE_NAME + " (" +
                    LayoutOfSchemaContract.FeedEntry._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    LayoutOfSchemaContract.FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
                    LayoutOfSchemaContract.FeedEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    LayoutOfSchemaContract.FeedEntry.COLUMN_NAME_COMPLETION_STATUS + " INTEGER," +
                    LayoutOfSchemaContract.FeedEntry.COLUMN_PRIORITY_STATUS + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + LayoutOfSchemaContract.FeedEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    boolean addTask(String taskTitle, String taskDescription, Integer priority) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LayoutOfSchemaContract.FeedEntry.COLUMN_NAME_TITLE, taskTitle);
        values.put(LayoutOfSchemaContract.FeedEntry.COLUMN_NAME_DESCRIPTION, taskDescription);
        values.put(LayoutOfSchemaContract.FeedEntry.COLUMN_NAME_COMPLETION_STATUS, 0);
//        Newly added  tasks default to not completed
        values.put(LayoutOfSchemaContract.FeedEntry.COLUMN_PRIORITY_STATUS, priority);

        return db.insert(LayoutOfSchemaContract.FeedEntry.TABLE_NAME, null, values) != -1;
//        .insert returns -1 if table is empty so above returns true if stuff has been added and
//        false otherwise
    }

    Cursor getAllTasks() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + LayoutOfSchemaContract.FeedEntry.TABLE_NAME, null);
    }

    boolean markAsComplete(int id) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(LayoutOfSchemaContract.FeedEntry.COLUMN_NAME_COMPLETION_STATUS, 1);

        return db.update(LayoutOfSchemaContract.FeedEntry.TABLE_NAME, values, LayoutOfSchemaContract.FeedEntry._ID + " = ? ", new String[]{String.valueOf(id)}) > 0;
    }

    boolean deleteEntry(int id) {

        SQLiteDatabase db = getWritableDatabase();
        return db.delete(LayoutOfSchemaContract.FeedEntry.TABLE_NAME, LayoutOfSchemaContract.FeedEntry._ID + " = ? ", new String[] {String.valueOf(id)}) > 0;
    }

    boolean editEntry(int id, String title, String description, Integer priority) {

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + LayoutOfSchemaContract.FeedEntry.TABLE_NAME + " WHERE " + LayoutOfSchemaContract.FeedEntry._ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        cursor.moveToFirst();

        int completionStatusID = cursor.getColumnIndex(LayoutOfSchemaContract.FeedEntry.COLUMN_NAME_COMPLETION_STATUS);

        Integer completionStatus = cursor.getInt(completionStatusID);

        ContentValues values = new ContentValues();

        values.put(LayoutOfSchemaContract.FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(LayoutOfSchemaContract.FeedEntry.COLUMN_NAME_DESCRIPTION, description);
        values.put(LayoutOfSchemaContract.FeedEntry.COLUMN_NAME_COMPLETION_STATUS, completionStatus);
        values.put(LayoutOfSchemaContract.FeedEntry.COLUMN_PRIORITY_STATUS, priority);

        return db.update(LayoutOfSchemaContract.FeedEntry.TABLE_NAME, values, LayoutOfSchemaContract.FeedEntry._ID + " = ? ", new String[]{String.valueOf(id)}) > 0;
    }

    Cursor getAllTasksOrderedByPriority() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + LayoutOfSchemaContract.FeedEntry.TABLE_NAME + " ORDER BY " + LayoutOfSchemaContract.FeedEntry.COLUMN_PRIORITY_STATUS + " ASC";
        return db.rawQuery(query, null);
    }

    Cursor getOnlyIncompleteTasks() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + LayoutOfSchemaContract.FeedEntry.TABLE_NAME + " WHERE " + LayoutOfSchemaContract.FeedEntry.COLUMN_NAME_COMPLETION_STATUS + " = ? ";
        return db.rawQuery(query, new String[]{String.valueOf(0)});
    }

    Cursor getOnlyIncompleteTasksOrderedByPriority() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + LayoutOfSchemaContract.FeedEntry.TABLE_NAME + " WHERE " + LayoutOfSchemaContract.FeedEntry.COLUMN_NAME_COMPLETION_STATUS + " = ? "  + " ORDER BY " + LayoutOfSchemaContract.FeedEntry.COLUMN_PRIORITY_STATUS + " ASC";
        return db.rawQuery(query, new String[]{String.valueOf(0)});
    }
}
