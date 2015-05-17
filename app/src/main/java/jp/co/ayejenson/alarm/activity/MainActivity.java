package jp.co.ayejenson.alarm.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.media.AudioManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import jp.co.ayejenson.alarm.ListFragment;
import jp.co.ayejenson.alarm.R;
import jp.co.ayejenson.alarm.SettingFragment;
import jp.co.ayejenson.alarm.model.Alarm;


public class MainActivity extends ActionBarActivity implements ListFragment.ListFragmentListener,SettingFragment.SettingFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_RING);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_main);
        ListFragment lf = ListFragment.newInstance();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container,lf).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_newAlarm) {
            Log.d("Menu", "Setting");
            Alarm alarm =  Alarm.getInstance(this);
            Long newAlarmId = alarm.newAlarmData();
            ListFragment lf = (ListFragment)getFragmentManager().findFragmentById(R.id.container);
            moveSettingFragment(newAlarmId);
            lf.alarmListReload();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void moveSettingFragment(Long alarmId) {
        SettingFragment sf = SettingFragment.newInstance(alarmId);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container,sf).addToBackStack(null).commit();
    }

    @Override
    public void moveListFragment() {
        ListFragment lf = ListFragment.newInstance();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container,lf).addToBackStack(null).commit();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
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
