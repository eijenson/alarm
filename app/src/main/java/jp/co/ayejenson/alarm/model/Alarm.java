package jp.co.ayejenson.alarm.model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jp.co.ayejenson.alarm.database.AlarmDataBase;
import jp.co.ayejenson.alarm.entity.AlarmData;
import jp.co.ayejenson.alarm.receiver.AlarmReceiver;

public class Alarm {
    private List<AlarmData> alarmList;
    private SQLiteDatabase alarmTable;
    private String tableName;
    private static Alarm alarm;
    private String newAlarmName = "新規アラーム";
    private Context context;
    public static Alarm getInstance(Context c){
        if(alarm == null){
           alarm =  new Alarm(c);
        }
        alarm.context = c;
        return alarm;
    }

    private Alarm(Context c){
        context = c;
        AlarmDataBase alarmDB = new AlarmDataBase(c);
        alarmTable = alarmDB.getWritableDatabase();
        tableName = alarmDB.getTableName();
    }
    /*アラームリスト取得*/
    public List<AlarmData> getAlarmList(){
        alarmList = new ArrayList<AlarmData>();
        //query(テーブル名,取得列名,where句,where句,groupBy句,Having句,orderBy句,limit句)
        Cursor c = alarmTable.query(tableName,null,null,null,null,null,null,null);
        if(c.moveToFirst()) {
            while (c.moveToNext()) {
                alarmList.add(new AlarmData(c.getLong(c.getColumnIndex("id")), c.getString(c.getColumnIndex("name")), c.getLong(c.getColumnIndex("date")), c.getInt(c.getColumnIndex("enabled"))>0));
                Log.d("アラームリスト取得", c.getString(0) + c.getString(1) + c.getString(2) + c.getString(3));
            }
        }
        return alarmList;
    }


    public AlarmData getAlarmData(Long alarmId){
        String[] whereText = new String[]{String.valueOf(alarmId)};
        //query(テーブル名,取得列名,where句,検索条件のはてな,groupBy句,Having句,orderBy句,limit句)
        Cursor c = alarmTable.query(tableName,null,"id=?",whereText,null,null,null,null);
        if(c.moveToFirst()){
            Log.d("一件アラーム取得",c.getString(0)+c.getString(1)+c.getString(2)+"  "+c.getString(3));
            return new AlarmData(c.getLong(c.getColumnIndex("id")),c.getString(c.getColumnIndex("name")),c.getLong(c.getColumnIndex("date")),c.getInt(c.getColumnIndex("enabled"))>0);
        }else{
            return null;
        }
    }

    public long setAlarmData(AlarmData ad){
        ContentValues value = new ContentValues();
        value.put("name",ad.getName());
        value.put("date",ad.getDate().getTime());
        value.put("enabled",ad.isEnabled());
        return alarmTable.insert(tableName,null,value);
    }

    public void setAlarmList(List<AlarmData> alarmList){
        this.alarmList = alarmList;
    }

    public long updateAlarmData(AlarmData ad){
        ContentValues value = new ContentValues();
        value.put("id",ad.getId());
        value.put("name",ad.getName());
        value.put("date",ad.getDate().getTime());
        value.put("enabled",ad.isEnabled());
        Log.d("enabled=",""+ad.isEnabled());
        int updateRow = alarmTable.update(tableName,value,"id=?",new String[]{String.valueOf(ad.getId())});
        setAlarmService(ad);
        return updateRow;

    }

    public long deleteAlarmData(Long alarmId){
        int deleteRow = alarmTable.delete(tableName,"id=?",new String[]{String.valueOf(alarmId)});
        return deleteRow;
    }

    public long newAlarmData(){
        ContentValues value = new ContentValues();
        value.put("name",newAlarmName);
        value.put("date",new Date().getTime());
        value.put("enabled",true);
        alarmTable.insert(tableName,null,value);
        //query(テーブル名,取得列名,where句,検索条件のはてな,groupBy句,Having句,orderBy句,limit句)
        Cursor c = alarmTable.query(tableName,new String[]{"MAX(id) as id"},null,null,null,null,null,null);
        c.moveToFirst();
        return c.getLong(0);
    }
    public void setAlarmService(AlarmData ad){
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        //TODO うまく行ったら消す
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 3);
        long time = cal.getTimeInMillis();
        //TODO ここまで
        if(ad.isEnabled()) {
            Log.d("アラーム登録",""+ad.getDate());
            Log.d("アラーム登録",""+new Date(time));
            am.setExact(AlarmManager.RTC_WAKEUP, time, getPendingIntent(ad));
        }else{
            am.cancel(getPendingIntent(ad));
        }
    }

    private PendingIntent getPendingIntent(AlarmData ad){

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("alarmId",ad.getId());
        PendingIntent pendintIntent = PendingIntent.getBroadcast(context, 99, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendintIntent;
    }

}