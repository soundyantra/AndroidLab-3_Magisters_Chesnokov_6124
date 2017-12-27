package com.sound.lab3_db_chesnokov.database;
import android.content.Context;
import android.database.sqlite.*;

import com.sound.lab3_db_chesnokov.database.TasksDbSchema.CategoryTable;
import com.sound.lab3_db_chesnokov.database.TasksDbSchema.PhotoTable;
import com.sound.lab3_db_chesnokov.database.TasksDbSchema.RecordTable;

/**
 * Created by sound on 14.12.2017.
 */

public class TasksBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "Tasks.db";
    public TasksBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //create category table
        db.execSQL("create table " + CategoryTable.NAME + "(" +
                " cat_id integer primary key autoincrement, " +
                CategoryTable.Cols.ICON + " text, " +
                CategoryTable.Cols.TITLE + " text " + ")"
        );
        //create records table
        db.execSQL("create table " + RecordTable.NAME + "(" +
                " record_id integer primary key autoincrement, " +
                RecordTable.Cols.STARTTS + " text, " +
                RecordTable.Cols.ENDTS + " text, " +
                RecordTable.Cols.SUMMARY +" text, "+
                RecordTable.Cols.NAME +" text, "+
                RecordTable.Cols.CATEGORY +" text, "+
                RecordTable.Cols.INTERVAL +" integer, "+
                "FOREIGN KEY (category) REFERENCES Category(cat_id) ON DELETE CASCADE" +
                ")"
        );
        //create photo table

        db.execSQL("create table " + PhotoTable.NAME + "(" +
                " photo_id integer primary key autoincrement, " +
                PhotoTable.Cols.DESC + " text, " +
                PhotoTable.Cols.RECORD_ID + " integer, "+
                "FOREIGN KEY(record) REFERENCES Record(record_id) ON DELETE CASCADE" +
                ")"
        );

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
