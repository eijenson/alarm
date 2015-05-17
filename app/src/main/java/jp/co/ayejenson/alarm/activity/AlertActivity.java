package jp.co.ayejenson.alarm.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import jp.co.ayejenson.alarm.AlertFragment;
import jp.co.ayejenson.alarm.R;


public class AlertActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_DTMF);
        setContentView(R.layout.activity_alert);
        Intent i = getIntent();
        AlertFragment af = AlertFragment.newInstance(i.getLongExtra("alarmId",-1));
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container,af).commit();
    }

    @Override
    public void onBackPressed(){
        int backStackCnt = getFragmentManager().getBackStackEntryCount();
        if(backStackCnt != 0) {
            getFragmentManager().popBackStack();
        }else{
            super.onBackPressed();
        }
    }
}
