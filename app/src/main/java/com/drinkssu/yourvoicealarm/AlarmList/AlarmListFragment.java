package com.drinkssu.yourvoicealarm.AlarmList;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.drinkssu.yourvoicealarm.R;
import com.drinkssu.yourvoicealarm.create_alarm_activity;

/**
 * Created by KSM on 2014-11-18.
 */
public class AlarmListFragment extends Fragment
{
    Button add_alarm;
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState)
    {
        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.main_alarm_list_activity, container, false);

    }
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        add_alarm = (Button)getActivity().findViewById(R.id.add_alarm);
        add_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), create_alarm_activity.class);
                startActivity(intent);
            }
        });
    }
}
