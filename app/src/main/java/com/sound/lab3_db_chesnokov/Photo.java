package com.sound.lab3_db_chesnokov;

/**
 * Created by sound on 14.12.2017.
 */

public class Photo {
    int id;
    String desc;
    String link;
    int record_id;

    public Photo(int id, String desc, int record_id) {
        this.id = id;
        this.desc = desc;
        //this.link = link;
        this.record_id = record_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }
}
