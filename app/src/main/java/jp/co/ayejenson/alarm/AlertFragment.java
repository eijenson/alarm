package jp.co.ayejenson.alarm;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AnalogClock;
import android.widget.TextClock;
import android.widget.TextView;

import java.io.IOException;
import java.util.Date;

import jp.co.ayejenson.alarm.entity.AlarmData;
import jp.co.ayejenson.alarm.model.Alarm;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlertFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AlertFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlertFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ALARM_ID = "alarmId";
    private static final String ALARM_NAME = "alarmName";
    private static final String ALARM_DATE = "alarmDate";
    private MediaPlayer media;
    private OnFragmentInteractionListener mListener;


    public static AlertFragment newInstance(Long alarmId) {
        AlertFragment fragment = new AlertFragment();
        Alarm alarm = Alarm.getInstance(fragment.getActivity());
        AlarmData ad = alarm.getAlarmData(alarmId);
        Bundle args = new Bundle();
        args.putLong(ALARM_ID, alarmId);
        args.putString(ALARM_NAME,ad.getName());
        args.putSerializable(ALARM_DATE,ad.getDate());
        fragment.setArguments(args);
        return fragment;
    }

    public AlertFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_alert, container, false);
        View.OnClickListener buttonOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long alarmId = getArguments().getLong(ALARM_ID);
                Alarm alarm = Alarm.getInstance(getActivity());
                AlarmData ad = alarm.getAlarmData(alarmId);
                ad.setEnabled(false);
                alarm.updateAlarmData(ad);
                //戻るボタンと同じ処理を行う
                getActivity().onBackPressed();
            }
        };
        v.findViewById(R.id.button_alert_close).setOnClickListener(buttonOnClick);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        TextView textView = (TextView)view.findViewById(R.id.text_alert_name);
        AnalogClock analogClock = (AnalogClock)view.findViewById(R.id.clock_alert_time);
        TextClock textClock = (TextClock)view.findViewById(R.id.text_alert_clock);
        Bundle args = getArguments();
        String name = args.getString(ALARM_NAME);
        Date date = (Date)args.getSerializable(ALARM_DATE);
        textView.setText(name);
        textClock.setText(date.toString());
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        media = new MediaPlayer();
        try {
            media.setDataSource(getActivity(), uri);
            media.setAudioStreamType(AudioManager.STREAM_ALARM);
            media.setLooping(true);
            media.prepare();
            media.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        media.stop();
        mListener = null;
    }

    @Override
    public void onPause(){
        super.onPause();
        media.stop();
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
