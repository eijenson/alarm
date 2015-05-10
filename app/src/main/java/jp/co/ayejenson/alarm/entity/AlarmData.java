package jp.co.ayejenson.alarm.entity;

import java.util.Date;

/**
 * Created by user on 2015/04/30.
 */
public class AlarmData {
    private long id;
    private String name;
    private Date date;
    private boolean enabled;
    public AlarmData(){

    }

    public AlarmData(long id,String name, long time){
        setId(id);
        setName(name);
        setDate(new Date(time));
    }
    public AlarmData(long id , String name , Date date , boolean enable){
        setId(id);
        setName(name);
        setDate(date);
        setEnabled(enable);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String toString(){
        return name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
