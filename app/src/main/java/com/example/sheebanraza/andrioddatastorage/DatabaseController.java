package com.example.sheebanraza.andrioddatastorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sheeban Raza on 03-Mar-16.
 */
public class DatabaseController {

    public static final String DATABASE_NAME = "dbName";
    public static final String TABLE_NAME = "Messages";
    public static final String MESSAGE = "message";
    public static final Integer DATABASE_VERSION = 4;
    public static final String QUERY = "create table Messages (message value not null)";
    public static final String QUERY_DROP_TABLE = "DROP TABLE IF EXISTS";

    DataBaseHelper dbHelper;
    Context context;
    SQLiteDatabase db;

    public DatabaseController(Context context) {
        this.context = context;
        dbHelper = new DataBaseHelper(context);
    }

    public DatabaseController open() {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long insert(String message) {
        ContentValues content = new ContentValues();
        content.put(MESSAGE, message);
        return db.insertOrThrow(TABLE_NAME, null, content);
    }

    public Cursor retrieve() {
        return db.query(TABLE_NAME, new String[]{MESSAGE}, null, null, null, null, null);
    }

    private static class DataBaseHelper extends SQLiteOpenHelper {
        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(QUERY);
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(QUERY_DROP_TABLE + TABLE_NAME);
            onCreate(db);
        }
    }

}
