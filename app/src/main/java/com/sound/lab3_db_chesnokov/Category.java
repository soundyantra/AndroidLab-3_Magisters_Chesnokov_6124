package com.sound.lab3_db_chesnokov;

import android.content.ContentValues;

import com.sound.lab3_db_chesnokov.database.TasksDbSchema;

/**
 * Created by sound on 14.12.2017.
 */

public class Category {
    String title;
    String icon;
    int id;

    public int getId() {
        return id;
    }

    public Category(String title, String icon,int id){
        this.title=title;
        this.icon=icon;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getIcon() {
        return icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


}
