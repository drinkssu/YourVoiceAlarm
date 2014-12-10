package com.drinkssu.yourvoicealarm.AlarmRank;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.drinkssu.yourvoicealarm.R;
import com.drinkssu.yourvoicealarm.Util.ReturnResponse;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class AlarmRankFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    Button btn_category0;
    Button btn_category1;
    Button btn_category2;
    Button btn_category3;
    Button btn_category4;
    Button btn_category5;
    Button btn_category6;
    Button btn_category7;

    int category;
    SwipeRefreshLayout swipeLayout;
    RecyclerView recyclerView;
    RecyclerAdapter mAdapter;
    ArrayList<RankInfoModel> itemsData = new ArrayList<RankInfoModel>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_alarm_ranking, container, false);

        initView(rootView);

        getNewAlarmList(0);

        return rootView;
    }

    private void getNewAlarmList(int category) {

        String endcodeArgument = null;
        String encodeArgument2 = null;

        String url = "http://growingdever.cafe24.com:8080/api/v1.0/upload_voice?q=";
        String argument = "{\"filters\":[{\"name\":\"category\",\"op\":\"==\",\"val\":\""+category+"\"}],";
        String argument2 = "\"order_by\":[{\"field\":\"lank_point\",\"direction\":\"desc\"}]}";

        try {
            endcodeArgument = URLEncoder.encode(argument, "UTF-8");
            encodeArgument2 = URLEncoder.encode(argument2, "UTF-8");
            Log.d("Tag", endcodeArgument);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        url+=endcodeArgument+encodeArgument2;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(getActivity(), url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                if (statusCode == 200) {
                    itemsData.clear();

                    Gson gson = new Gson();
                    ReturnResponse returnResponse = gson.fromJson(response.toString(), ReturnResponse.class);

                    int num = 1;

                    for (RankInfoModel infoModel : returnResponse.getObjects()) {
                        infoModel.setRankNum(num);
                        itemsData.add(infoModel);
                        num++;
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void initView(View rootView) {
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.BLACK);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 3. create an adapter
        mAdapter = new RecyclerAdapter(itemsData);
        // 4. set adapter
        recyclerView.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        btn_category0 = (Button) rootView.findViewById(R.id.btn_category0);
        btn_category0.setOnClickListener(this);

        btn_category1 = (Button) rootView.findViewById(R.id.btn_category1);
        btn_category1.setOnClickListener(this);

        btn_category2 = (Button) rootView.findViewById(R.id.btn_category2);
        btn_category2.setOnClickListener(this);

        btn_category3 = (Button) rootView.findViewById(R.id.btn_category3);
        btn_category3.setOnClickListener(this);

        btn_category4 = (Button) rootView.findViewById(R.id.btn_category4);
        btn_category4.setOnClickListener(this);

        btn_category5 = (Button) rootView.findViewById(R.id.btn_category5);
        btn_category5.setOnClickListener(this);

        btn_category6 = (Button) rootView.findViewById(R.id.btn_category6);
        btn_category6.setOnClickListener(this);

        btn_category7 = (Button) rootView.findViewById(R.id.btn_category7);
        btn_category7.setOnClickListener(this);
    }

    @Override
    public void onRefresh() {
        getNewAlarmList(category);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_category0:
                category = 0;

                break;

            case R.id.btn_category1:
                category = 1;

                break;

            case R.id.btn_category2:
                category = 2;

                break;

            case R.id.btn_category3:
                category = 3;

                break;

            case R.id.btn_category4:
                category = 4;

                break;

            case R.id.btn_category5:
                category = 5;

                break;

            case R.id.btn_category6:
                category = 6;

                break;

            case R.id.btn_category7:
                category = 7;

                break;
        }
        getNewAlarmList(category);
    }
}
