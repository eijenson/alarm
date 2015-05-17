package jp.co.ayejenson.alarm.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import jp.co.ayejenson.alarm.AlertActivity;

/**
 * Created by user on 2015/05/17.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("レシーバー実行","");
        Intent alertActivity = new Intent(context, AlertActivity.class);
        alertActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        alertActivity.putExtra("alarmId",intent.getLongExtra("alarmId",-1));
        context.startActivity(alertActivity);
    }
}
