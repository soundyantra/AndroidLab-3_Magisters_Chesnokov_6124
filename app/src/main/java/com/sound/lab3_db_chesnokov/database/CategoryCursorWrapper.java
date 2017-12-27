package com.sound.lab3_db_chesnokov.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.sound.lab3_db_chesnokov.Category;
import com.sound.lab3_db_chesnokov.database.TasksDbSchema.CategoryTable;

/**
 * Created by sound on 14.12.2017.
 */

public class CategoryCursorWrapper extends CursorWrapper {
    public CategoryCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Category getCategory() {
        String id = getString(getColumnIndex(CategoryTable.Cols.CAT_ID));
        String title = getString(getColumnIndex(CategoryTable.Cols.TITLE));
        //long date = getLong(getColumnIndex(CategoryTable.Cols.DATE));
        //int isSolved = getInt(getColumnIndex(CategoryTable.Cols.SOLVED));
        String icon = getString(getColumnIndex(CategoryTable.Cols.ICON));
        Category cat = new Category(title,icon,Integer.parseInt(id));

        return cat;
    }


}
