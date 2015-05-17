package jp.co.ayejenson.alarm;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import jp.co.ayejenson.alarm.entity.AlarmData;
import jp.co.ayejenson.alarm.model.Alarm;

public class ListFragment extends Fragment implements AbsListView.OnItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private ListFragmentListener mListener;
    private AbsListView mListView;
    private ArrayAdapter mAdapter;
    private ArrayList<AlarmData> alarmList;
    public ListFragment() {
    }

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Alarm alarm =  Alarm.getInstance(getActivity());
        alarmList = (ArrayList)alarm.getAlarmList();
        mAdapter =  new ArrayAdapter<AlarmData>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, alarmList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ListFragmentListener) activity;
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("ALEAM", "position" + position + "id" + id);
        if (null != mListener) {
            mListener.moveSettingFragment(alarmList.get(position).getId());
        }
    }

    public void alarmListReload(){
        Alarm alarm =  Alarm.getInstance(getActivity());
        alarmList = (ArrayList)alarm.getAlarmList();
        mAdapter.clear();
        mAdapter.addAll(alarmList);
    }
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    public interface ListFragmentListener {
        public void moveSettingFragment(Long alarmId);
    }

}
