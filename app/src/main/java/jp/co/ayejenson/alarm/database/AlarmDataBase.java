package jp.co.ayejenson.alarm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 2015/05/10.
 */
public class AlarmDataBase extends SQLiteOpenHelper{
    private final static String DB_NAME = "alarmdb";
    private final static int DB_VERSION = 1;
    private final static String TABLE_NAME = "alarm";
    private final static String TABLE_CREATE_SQL =
            "CREATE TABLE "+TABLE_NAME+" (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT ," +
            "name TEXT NOT NULL ," +
            "date INTEGER," +
            "enabled BOOLEAN NOT NULL" +
            ")";
    private static final String TABLE_DROP_SQL = "drop table "+TABLE_NAME+";";

    public AlarmDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_DROP_SQL);
        db.execSQL(TABLE_CREATE_SQL);
    }
    public String getTableName(){
        return TABLE_NAME;
    }
}
