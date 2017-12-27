package com.sound.lab3_db_chesnokov.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.sound.lab3_db_chesnokov.Photo;
import com.sound.lab3_db_chesnokov.Record;

/**
 * Created by sound on 16.12.2017.
 */

public class PhotoCursorWrapper extends CursorWrapper {
    public PhotoCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Photo getPhoto() {
        String id = getString(getColumnIndex("photo_id"));
        String desc = getString(getColumnIndex("description"));
        String record_id = getString(getColumnIndex("record"));

        Photo photo = new Photo(Integer.parseInt(id), desc,Integer.parseInt(record_id));

        return photo;
    }
}
