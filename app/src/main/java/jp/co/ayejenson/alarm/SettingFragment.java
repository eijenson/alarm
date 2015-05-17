package jp.co.ayejenson.alarm;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import jp.co.ayejenson.alarm.entity.AlarmData;
import jp.co.ayejenson.alarm.model.Alarm;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link jp.co.ayejenson.alarm.SettingFragment.SettingFragmentListener} interface
 * to handle interaction events.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String FORM_ALARM_NAME = "form_alarm_name";
    private static final String FORM_ALARM_DATE = "form_alarm_date";
    private static final String FORM_ALARM_ENABLED = "form_alarm_enabled";
    private static final String ALEAM_ID = "alarmId";

    private SettingFragmentListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param alarmId
     * @return 生成したフラグメント
     */
    public static SettingFragment newInstance(Long alarmId) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        Alarm alarm = Alarm.getInstance(fragment.getActivity());
        AlarmData ad = alarm.getAlarmData(alarmId);
        args.putLong(ALEAM_ID,ad.getId());
        args.putString(FORM_ALARM_NAME, ad.getName());
        args.putSerializable(FORM_ALARM_DATE, ad.getDate());
        args.putBoolean(FORM_ALARM_ENABLED,ad.isEnabled());
        fragment.setArguments(args);
        Log.d("Setting","args"+args.getLong(ALEAM_ID)+""+args.getString(FORM_ALARM_NAME)+""+args.getSerializable(FORM_ALARM_DATE)+""+args.getBoolean(FORM_ALARM_ENABLED));
        return fragment;
    }

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //アクションバーメニュー非表示
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // メニューをクリア
        menu.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_setting, container, false);
        OnClickListener submitOnClick = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Long id = getArguments().getLong(ALEAM_ID);
                String name = ((EditText)view.findViewById(R.id.form_alarm_name)).getText().toString();
                DatePicker datePicker = (DatePicker)view.findViewById(R.id.form_alarm_date);
                boolean enabled = ((Switch)view.findViewById(R.id.form_alarm_enabled)).isChecked();
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                TimePicker timePicker = (TimePicker)view.findViewById(R.id.form_alarm_time);
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                Calendar cal = Calendar.getInstance();
                cal.set(year,month,day,hour,minute,0);
                Date date = cal.getTime();
                AlarmData ad = new AlarmData(id,name,date,enabled);
                //入力チェック
                if(!checkAlarmData(ad)) return ;
                Alarm alarm = Alarm.getInstance(getActivity());
                long l = alarm.updateAlarmData(ad);
                Log.d("UPDATE","result"+l+""+id);
                int toastMessage;
                if(enabled) {
                    toastMessage =R.string.toast_update_on;
                }else{
                    toastMessage =R.string.toast_update_off;
                }
                Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_SHORT).show();
                mListener.moveListFragment();
            }
        };
        OnClickListener deleteOnClick = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Long alarmId = getArguments().getLong(ALEAM_ID);
                Alarm alarm = Alarm.getInstance(getActivity());
                long l = alarm.deleteAlarmData(alarmId);
                Log.d("DELETE","result"+l+""+alarmId);
                Toast.makeText(getActivity(), R.string.toast_delete, Toast.LENGTH_SHORT).show();
                mListener.moveListFragment();
            }
        };
        view.findViewById(R.id.form_alarm_submit).setOnClickListener(submitOnClick);
        view.findViewById(R.id.form_alarm_delete).setOnClickListener(deleteOnClick);
        return view;
    }

    public boolean checkAlarmData(AlarmData ad) {
        if (ad.getName().equals("")) {
            Toast.makeText(getActivity(), R.string.toast_check_blank_name, Toast.LENGTH_SHORT).show();
            return false;
        }
        //設定した日付が、現在よりも前ならエラー
        if (ad.getDate().before(new Date())) {
            Toast.makeText(getActivity(), R.string.toast_check_date, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        TextView name = (TextView) view.findViewById(R.id.form_alarm_name);
        DatePicker date = (DatePicker) view.findViewById(R.id.form_alarm_date);
        TimePicker time = (TimePicker) view.findViewById(R.id.form_alarm_time);
        Switch enabled = ((Switch)view.findViewById(R.id.form_alarm_enabled));
        Bundle args = getArguments();
        if (args != null) {
            name.setText(args.getString(FORM_ALARM_NAME));
            Date d = (Date)args.getSerializable(FORM_ALARM_DATE);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            date.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            time.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
            time.setCurrentMinute(cal.get(Calendar.MINUTE));
            enabled.setChecked(args.getBoolean(FORM_ALARM_ENABLED));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (SettingFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface SettingFragmentListener {
        public void moveListFragment();
    }

}
