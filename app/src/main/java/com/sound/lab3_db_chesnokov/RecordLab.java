package com.sound.lab3_db_chesnokov;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sound.lab3_db_chesnokov.database.RecordCursorWrapper;

import com.sound.lab3_db_chesnokov.database.TasksBaseHelper;
import com.sound.lab3_db_chesnokov.database.TasksDbSchema;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sound on 14.12.2017.
 */

public class RecordLab {
    private static RecordLab srecordLab;
    private List<Record> mrecord;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    /*
    public static CrimeLab get(Context context) {

    }
    */

    public static RecordLab get(Context context) {
        if (srecordLab == null) {
            srecordLab = new RecordLab(context);
        }
        return srecordLab;
    }


    public RecordLab(Context context) {
        mrecord = new ArrayList<>();

        mContext = context.getApplicationContext();
        mDatabase = new TasksBaseHelper(mContext)
                .getWritableDatabase();


        CategoryLab cb = new CategoryLab(mContext);

        mrecord = getRecords();

    }


    public Record getRecord(int id) {
        for (Record cat : mrecord) {
            if (cat.getId()==(id)) {
                return cat;
            }
        }
        return null;
    }

    public void updateRecord(Record rec) {

        int id = rec.getId();
        ContentValues values = getContentValues(rec);
        mDatabase.update(TasksDbSchema.RecordTable.NAME, values,
                TasksDbSchema.RecordTable.Cols.RECORD_ID + " = ?",
                new String[] { Integer.toString(id) });
    }

    public void deleteRecord(Record rec){
        int id = rec.getId();
        mDatabase.delete(TasksDbSchema.RecordTable.NAME, TasksDbSchema.RecordTable.Cols.RECORD_ID + " = ?",new String[] { Integer.toString(id) });
    }


    private static ContentValues getContentValues(Record record){
        ContentValues values = new ContentValues();
        //Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        values.put(TasksDbSchema.RecordTable.Cols.RECORD_ID, record.getId());
        values.put(TasksDbSchema.RecordTable.Cols.STARTTS, record.getStartTS());
        values.put(TasksDbSchema.RecordTable.Cols.ENDTS, record.getEndTS());
        values.put(TasksDbSchema.RecordTable.Cols.CATEGORY, record.getCategory());
        values.put(TasksDbSchema.RecordTable.Cols.SUMMARY, record.getSummary());
        values.put(TasksDbSchema.RecordTable.Cols.NAME, record.getName());
        values.put(TasksDbSchema.RecordTable.Cols.INTERVAL, record.getTimeInterval());
        return values;
    }

    public void addrecord(Record c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(TasksDbSchema.RecordTable.NAME, null, values);

    }

    private RecordCursorWrapper queryrecord(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TasksDbSchema.RecordTable.NAME,
                null, // columns - с null выбираются все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );

        return new RecordCursorWrapper(cursor);
    }

    private RecordCursorWrapper queryrecord(String whereClause, String[] whereArgs, String groupby, String orderBy) {
        Cursor cursor = mDatabase.query(
                TasksDbSchema.RecordTable.NAME,
                null, // columns - с null выбираются все столбцы
                whereClause,
                whereArgs,
                groupby, // groupBy
                null, // having
                orderBy // orderBy
        );

        return new RecordCursorWrapper(cursor);
    }

    public List<Record> getRecords() {

        List<Record> recs = new ArrayList<>();
        RecordCursorWrapper cursor = queryrecord(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                recs.add(cursor.getRecord());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return recs;
    }

    public List<Record> getRecords_most_time() {

        List<Record> recs = new ArrayList<>();
        RecordCursorWrapper cursor = queryrecord(null, null,"","interval DESC");
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                recs.add(cursor.getRecord());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return recs;
    }
}
