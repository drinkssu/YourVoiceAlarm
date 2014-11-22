package com.drinkssu.yourvoicealarm;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by KSM on 2014-11-23.
 */
public class Ranking extends Fragment
{
    Button btn_category0;
    Button btn_category1;
    Button btn_category2;
    Button btn_category3;
    Button btn_category4;
    Button btn_category5;
    Button btn_category6;
    Button btn_category7;

    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState)
    {
        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_ranking, container, false);

    }
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        btn_category0 = (Button)getActivity().findViewById(R.id.btn_category0);
        btn_category0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_category1 = (Button)getActivity().findViewById(R.id.btn_category1);
        btn_category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_category2 = (Button)getActivity().findViewById(R.id.btn_category2);
        btn_category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_category3 = (Button)getActivity().findViewById(R.id.btn_category3);
        btn_category3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_category4 = (Button)getActivity().findViewById(R.id.btn_category4);
        btn_category4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_category5 = (Button)getActivity().findViewById(R.id.btn_category5);
        btn_category5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_category6 = (Button)getActivity().findViewById(R.id.btn_category6);
        btn_category6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_category7 = (Button)getActivity().findViewById(R.id.btn_category7);
        btn_category7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}