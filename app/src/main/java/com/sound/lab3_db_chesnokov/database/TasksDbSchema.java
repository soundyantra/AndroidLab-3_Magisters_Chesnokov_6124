package com.sound.lab3_db_chesnokov.database;

/**
 * Created by sound on 14.12.2017.
 */

public class TasksDbSchema {
        public static final class CategoryTable{
            public static final String NAME="Category";
            public static final class Cols{
                public static final String CAT_ID = "cat_id";
                public static final String TITLE = "title";
                public static final String ICON = "icon";
            }

        }

        public static final class RecordTable{
            public static final String NAME="Record";
            public static final class Cols{
                public static final String RECORD_ID = "record_id";
                public static final String STARTTS = "startTS";
                public static final String ENDTS = "endTS";
                public static final String CATEGORY = "category";
                public static final String SUMMARY = "summary";
                public static final String NAME = "name";
                public static final String INTERVAL = "interval";
            }


        }
        public static final class PhotoTable {
            public static final String NAME = "Photo";

            public static final class Cols {
                public static final String PHOTO_ID = "photo_id";
                public static final String DESC = "description";
                public static final String RECORD_ID = "record";
            }
        }

}
