package com.example.adammb.redesignpelemzamannow.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbpelemzamannow";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_NOTE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s REAL NOT NULL," +
                    " %s INTEGER NOT NULL)",
            DatabaseContract.TABLE_NAME,
            DatabaseContract.PelemColumn._ID,
            DatabaseContract.PelemColumn.ORIGINAL_TITLE,
            DatabaseContract.PelemColumn.OVERVIEW,
            DatabaseContract.PelemColumn.RELEASE_DATE,
            DatabaseContract.PelemColumn.POSTER_PATH,
            DatabaseContract.PelemColumn.VOTE_AVERAGE,
            DatabaseContract.PelemColumn.VOTE_COUNT
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DatabaseContract.TABLE_NAME);

        onCreate(db);
    }
}
