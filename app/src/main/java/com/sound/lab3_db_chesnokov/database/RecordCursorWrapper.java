package com.sound.lab3_db_chesnokov.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.sound.lab3_db_chesnokov.CategoryLab;
import com.sound.lab3_db_chesnokov.Record;

import java.util.Date;


/**
 * Created by sound on 14.12.2017.
 */

public class RecordCursorWrapper extends CursorWrapper {
    public RecordCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Record getRecord() {
        String id = getString(getColumnIndex(TasksDbSchema.RecordTable.Cols.RECORD_ID));
        String name = getString(getColumnIndex(TasksDbSchema.RecordTable.Cols.NAME));
        String summary = getString(getColumnIndex(TasksDbSchema.RecordTable.Cols.SUMMARY));
        //String category = getString(getColumnIndex(TasksDbSchema.RecordTable.Cols.CATEGORY));
        int category = Integer.parseInt(getString(getColumnIndex(TasksDbSchema.RecordTable.Cols.CATEGORY)));
        //String categoryname = getString(getColumnIndex(TasksDbSchema.RecordTable.Cols.CATEGORY);
        //java.sql.Timestamp sqldate = new java.sql.Timestamp(getColumnIndex(TasksDbSchema.RecordTable.Cols.STARTTS));
        //java.util.Date newDate = sqldate.getDate();
        //new Date(Date)
        //long date = getLong(getColumnIndex(recordTable.Cols.DATE));
        //int isSolved = getInt(getColumnIndex(recordTable.Cols.SOLVED));
        String date1 = getString(getColumnIndex(TasksDbSchema.RecordTable.Cols.STARTTS));
        String date2 = getString(getColumnIndex(TasksDbSchema.RecordTable.Cols.ENDTS));
        //CategoryLab cb = new CategoryLab();
        Record rec = new Record(Integer.parseInt(id), date1,date2,category,summary,name);

        return rec;
    }
}
