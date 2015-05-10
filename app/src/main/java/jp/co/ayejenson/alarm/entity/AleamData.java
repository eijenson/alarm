package jp.co.ayejenson.alarm.entity;

import java.util.Date;

/**
 * Created by user on 2015/04/30.
 */
public class AleamData {
    private String name;
    private Date date;
    private boolean enabled;
    public AleamData(){

    }
    public AleamData(String name , Date date){
        setName(name);
        setDate(date);
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
}
