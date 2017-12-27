package com.sound.lab3_db_chesnokov;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sound.lab3_db_chesnokov.database.PhotoCursorWrapper;
import com.sound.lab3_db_chesnokov.database.RecordCursorWrapper;
import com.sound.lab3_db_chesnokov.database.TasksBaseHelper;
import com.sound.lab3_db_chesnokov.database.TasksDbSchema;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sound on 16.12.2017.
 */

public class PhotoLab {
    private static PhotoLab sPhotoLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static PhotoLab get(Context context) {
        if (sPhotoLab == null) {
            sPhotoLab = new PhotoLab(context);
        }
        return sPhotoLab;
    }

    public PhotoLab(Context context) {

        mContext = context.getApplicationContext();
        mDatabase = new TasksBaseHelper(mContext)
                .getWritableDatabase();

    }

    public void deleteRecord(Photo rec){
        int id = rec.getId();
        mDatabase.delete(TasksDbSchema.PhotoTable.NAME, TasksDbSchema.PhotoTable.Cols.PHOTO_ID + " = ?",new String[] { Integer.toString(id) });
    }

    public void deletePhoto_by_id(int id){

        mDatabase.delete(TasksDbSchema.PhotoTable.NAME, TasksDbSchema.PhotoTable.Cols.PHOTO_ID + " = ?",new String[] { Integer.toString(id) });
    }

    public void addrecord(Photo c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(TasksDbSchema.PhotoTable.NAME, null, values);

    }

    private static ContentValues getContentValues(Photo photo){
        ContentValues values = new ContentValues();
        //Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        values.put(TasksDbSchema.PhotoTable.Cols.PHOTO_ID, photo.getId());
        values.put(TasksDbSchema.PhotoTable.Cols.DESC, photo.getDesc());
        values.put(TasksDbSchema.PhotoTable.Cols.RECORD_ID, photo.getRecord_id());

        return values;
    }

    public List<Photo> getPhotos() {

        List<Photo> recs = new ArrayList<>();
        PhotoCursorWrapper cursor = queryrecord(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                recs.add(cursor.getPhoto());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return recs;
    }

    private PhotoCursorWrapper queryrecord(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TasksDbSchema.PhotoTable.NAME,
                null, // columns - с null выбираются все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );

        return new PhotoCursorWrapper(cursor);
    }
}
