package jp.co.ayejenson.alarm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 2015/05/10.
 */
public class AlarmDB extends SQLiteOpenHelper{
    private final static String DB_NAME = "alarmdb";
    private final static int DB_VERSION = 1;
    private final static String DB_CREATE_SQL =
            "CREATE TABLE alarm {" +
            "name " +
            "date" +
            "enabled" +
            "}";

    public AlarmDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
