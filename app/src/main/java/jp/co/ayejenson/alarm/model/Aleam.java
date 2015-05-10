package jp.co.ayejenson.alarm.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.ayejenson.alarm.entity.AleamData;

public class Aleam {
    private List<AleamData> aleamList;

    public List<AleamData> getAleamList(){
        aleamList = new ArrayList<AleamData>();
        aleamList.add(new AleamData("date1",new Date()));
        aleamList.add(new AleamData("date2",new Date()));
        aleamList.add(new AleamData("date3",new Date()));
        return aleamList;
    }

    public void setAleamList(List<AleamData> aleamList){
        this.aleamList = aleamList;
    }
}
