package com.sound.lab3_db_chesnokov;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sound.lab3_db_chesnokov.database.CategoryCursorWrapper;
import com.sound.lab3_db_chesnokov.database.TasksBaseHelper;
import com.sound.lab3_db_chesnokov.database.TasksDbSchema;
import com.sound.lab3_db_chesnokov.database.TasksDbSchema.CategoryTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by sound on 14.12.2017.
 */

public class CategoryLab {
    private static CategoryLab sCategoryLab;
    public List<Category> mCategory;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    /*
    public static CrimeLab get(Context context) {

    }
    */

    public static CategoryLab get(Context context) {
        if (sCategoryLab == null) {
            sCategoryLab = new CategoryLab(context);
        }
        return sCategoryLab;
    }


    public CategoryLab(Context context) {
        mCategory = new ArrayList<>();

        mContext = context.getApplicationContext();
        mDatabase = new TasksBaseHelper(mContext)
                .getWritableDatabase();


        mCategory = getCategories();

    }


    public Category getRecord(int id) {
        List<Category> cats =  getCategories();
        for (int i=0;i<cats.size();i++){
            if (cats.get(i).id == id){
                return cats.get(i);
            }
        }

        return null;
    }



    private static ContentValues getContentValues(Category category){
        ContentValues values = new ContentValues();
        values.put(CategoryTable.Cols.TITLE, category.getTitle());
        values.put(CategoryTable.Cols.ICON, category.getIcon());
        return values;
    }

    public void addCategory(Category c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(CategoryTable.NAME, null, values);

    }

    public void updateCategory(Category rec) {

        int id = rec.getId();
        ContentValues values = getContentValues(rec);
        mDatabase.update(TasksDbSchema.CategoryTable.NAME, values,
                CategoryTable.Cols.CAT_ID + " = ?",
                new String[] { Integer.toString(id) });
    }

    public void deleteCategory(Category rec){
        int id = rec.getId();
        mDatabase.delete(TasksDbSchema.CategoryTable.NAME, CategoryTable.Cols.CAT_ID + " = ?",new String[] { Integer.toString(id) });
        mDatabase.delete(TasksDbSchema.RecordTable.NAME, TasksDbSchema.RecordTable.Cols.CATEGORY + " = ?",new String[] { Integer.toString(id) });
    }

    private CategoryCursorWrapper queryCategory(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CategoryTable.NAME,
                null, // columns - с null выбираются все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );

        return new CategoryCursorWrapper(cursor);
    }

    public List<Category> getCategories() {

        List<Category> cats = new ArrayList<>();
        CategoryCursorWrapper cursor = queryCategory(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                cats.add(cursor.getCategory());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return cats;
    }

}
