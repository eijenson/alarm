package jp.co.ayejenson.alarm.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by user on 2015/05/17.
 */
public class AlertService extends IntentService {

    public AlertService(){
        super("AlertService");

    }

    public AlertService(String name) {
        super(name);
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("service", "サービス起動");
    }
}
