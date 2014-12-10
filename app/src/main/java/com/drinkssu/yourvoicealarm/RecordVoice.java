package com.drinkssu.yourvoicealarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class RecordVoice  extends Fragment{
    FragmentManager fm;
    private final int GOOGLE_STT = 1000, MY_UI=1001;
    private ArrayList<String> mResult;
    private  String  mOneResult;

    ProgressBar prograssbar;
    private File RECORED_FILE = Environment.getExternalStorageDirectory();






    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState)
    {

        return inflater.inflate(R.layout.activity_record_voice, container, false);
    }
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {

        prograssbar = (ProgressBar)getActivity().findViewById(R.id.progressBar);
        fm = getFragmentManager();


        Button btnRecord = (Button)getActivity().findViewById(R.id.bRecordVoice);


        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), AlarmSetting.class);
                startActivityForResult(i, 8);


            }
        });

        ImageView equl = (ImageView)getActivity().findViewById(R.id.equlizer);

        Glide.with(this)
                .load(R.drawable.equalizer)
                .into(equl);

        Button btnAlarmList = (Button)getActivity().findViewById(R.id.bAlarmList);
        btnAlarmList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmListDialog AlarmList = new AlarmListDialog();

                AlarmList.show(fm, "Alarm List Dialog");

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == -1  && (requestCode == GOOGLE_STT || requestCode == MY_UI) ){
            showSelectDialog(requestCode, data);
            //todo setting 파일 여기서 저장해야할듯






        }
        else if(resultCode == -1 && requestCode == 8)
        {
            startActivityForResult(new Intent(getActivity(), CustomUIActivity.class) , MY_UI);

        }


    }
    private void showSelectDialog(int requestCode, Intent data) {
        String key = "";
        if(requestCode == GOOGLE_STT)					//���������ν��̸�
            key = RecognizerIntent.EXTRA_RESULTS;	//Ű�� ����
        else if(requestCode == MY_UI)					//���� ���� activity �̸�
            key = SpeechRecognizer.RESULTS_RECOGNITION;	//Ű�� ����

        mResult = data.getStringArrayListExtra(key);		//�νĵ� ������ list �޾ƿ�.
        String[] result = new String[mResult.size()];			//�迭��. ���̾�α׿��� ����ϱ� ����
        mResult.toArray(result);
        mOneResult = mResult.get(0);            // 많은 예시값 중에서 첫번째거 -> 정확도 높은거 가져오기


        InputAlarmNameDialog dia = new InputAlarmNameDialog();
        dia.data = mOneResult;
        dia.show(fm, "input alarm name");


    }



}