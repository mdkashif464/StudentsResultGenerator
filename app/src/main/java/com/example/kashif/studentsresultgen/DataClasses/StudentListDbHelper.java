package com.example.kashif.studentsresultgen.DataClasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kashif on 2/6/17.
 */

public class StudentListDbHelper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "studentlist.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;

    // constructor
    public StudentListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create a table to hold studentlist data
        final String SQL_CREATE_STUDENTLIST_TABLE = "CREATE TABLE " + StudentListContract.StudentlistEntry.TABLE_NAME + " (" +
                StudentListContract.StudentlistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                StudentListContract.StudentlistEntry.COLUMN_STUDENT_NAME + " TEXT NOT NULL, " +
                StudentListContract.StudentlistEntry.COLUMN_GENDER + " TEXT NOT NULL, " +
                StudentListContract.StudentlistEntry.COLUMN_AGE + " INTEGER NOT NULL, " +
                StudentListContract.StudentlistEntry.COLUMN_HINDI_MARKS + " INTEGER NOT NULL, " +
                StudentListContract.StudentlistEntry.COLUMN_ENGLISH_MARKS + " INTEGER NOT NULL, " +
                StudentListContract.StudentlistEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        db.execSQL(SQL_CREATE_STUDENTLIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
