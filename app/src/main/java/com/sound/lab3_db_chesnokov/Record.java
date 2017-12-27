package com.sound.lab3_db_chesnokov;

import android.text.format.DateUtils;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;

/**
 * Created by sound on 14.12.2017.
 */

public class Record {
    int id;
    String startTS;
    String endTS;
    int category;
    String summary;
    String name;
    String categoryname;
    long interval;
    public Record(){

    }

    public int getId() {
        return id;
    }

    public String getStartTS() {
        return startTS;
    }

    public String getEndTS() {
        return endTS;
    }

    public int getCategory() {
        return category;
    }

    public String getSummary() {
        return summary;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartTS(String startTS) {
        this.startTS = startTS;
    }

    public void setEndTS(String endTS) {
        this.endTS = endTS;
    }

    public void setCategory_id(int category_id) {
        this.category = category_id;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Record(int id, String startTS, String endTS, int category, String summary,String name) {
        this.id = id;
        this.startTS = startTS;
        this.endTS = endTS;
        this.category = category;
        this.summary = summary;
        this.name=name;
        this.interval = getTimeInterval();
        //this.categoryname=categoryname;

    }

    public long getTimeInterval(){
        //JodaTimeAndroid.init(this);
        //DateTime start = DateTime.parse(startTS);

        //DateTime start = DateTime.parse(startTS,
        //        DateTimeFormat.forPattern("HH:mm"));
        DateTime start = DateTime.parse(startTS,
               DateTimeFormat.forPattern("dd.MM.yyyy HH:mm"));

        //DateTime end = DateTime.parse(endTS,
        //        DateTimeFormat.forPattern("HH:mm"));

        DateTime end = DateTime.parse(endTS,
                DateTimeFormat.forPattern("dd.MM.yyyy HH:mm"));


        Interval hey = new Interval(start, end);
        long minutes = hey.toDuration().getStandardMinutes();
        interval = minutes;
        return interval;

    }

    public boolean isbetween(String starts, String ends){

        DateTime start = DateTime.parse(startTS,
                DateTimeFormat.forPattern("dd.MM.yyyy HH:mm"));

        DateTime end = DateTime.parse(endTS,
                DateTimeFormat.forPattern("dd.MM.yyyy HH:mm"));

        DateTime startgiven = DateTime.parse(starts,
                DateTimeFormat.forPattern("dd.MM.yyyy"));

        DateTime endgiven = DateTime.parse(ends,
                DateTimeFormat.forPattern("dd.MM.yyyy"));

        if (start.isAfter(startgiven)&&start.isBefore(endgiven)){
            return true;
        }
        else return false;


    }
}
