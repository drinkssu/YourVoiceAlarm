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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class RecordVoice  extends Fragment {

    private final int GOOGLE_STT = 1000, MY_UI=1001;				//requestCode. ���������ν�, ���� ���� Activity
    private ArrayList<String> mResult;
    private  String  mOneResult;
    private String mSelectedString;										//��� list �� ����ڰ� ������ �ؽ�Ʈ
    private TextView mResultTextView;
    int count;
    FragmentManager fm;
    MediaPlayer player;             // 음성 재생을 위한 MediaPlayer 선언
    MediaRecorder recorder;
    private File RECORED_FILE = Environment.getExternalStorageDirectory();

    ProgressBar prograssbar;



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

                    startActivityForResult(new Intent(getActivity(), CustomUIActivity.class), MY_UI);
                    final Handler barHandler=new Handler();

                    new Thread(){
                        public void run() {
                            count=0;
                            while ( count < 4 ) {
                                try {Thread.sleep(1000);}catch(Exception e){}
                                barHandler.post(new Runnable() {
                                    public void run() {
                                        prograssbar.setProgress(count);
                                    }
                                });
                                count++;

                            }
                        }
                    }.start();

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
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if( resultCode == -1  && (requestCode == GOOGLE_STT || requestCode == MY_UI) ){		//��� ������
            showSelectDialog(requestCode, data);				//��� ���̾�α׷� ���.
        }
        else{															//��� ������ ���� �޽��� ���
            String msg = null;

            //���� ���� activity���� �Ѿ���� ���� �ڵ带 �з�
            switch(resultCode){
                case SpeechRecognizer.ERROR_AUDIO:
                    msg = "오디오 에러.";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    msg = "클라이언트 에러.";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    msg = "퍼미션 에러.";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    msg = "네트워크 에러.";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    msg = "인식 불가.";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    msg = "인식 바쁨.";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    msg = "서버 에러.";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    msg = "스피치 타임아웃";
                    break;
            }

            if(msg != null)		//���� �޽����� null�� �ƴϸ� �޽��� ���
                Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    //��� list ����ϴ� ���̾�α� ��
    private void showSelectDialog(int requestCode, Intent data){



        String key = "";
        if(requestCode == GOOGLE_STT)					//���������ν��̸�
            key = RecognizerIntent.EXTRA_RESULTS;	//Ű�� ����
        else if(requestCode == MY_UI)					//���� ���� activity �̸�
            key = SpeechRecognizer.RESULTS_RECOGNITION;	//Ű�� ����

        mResult = data.getStringArrayListExtra(key);		//�νĵ� ������ list �޾ƿ�.
        String[] result = new String[mResult.size()];			//�迭��. ���̾�α׿��� ����ϱ� ����
        mResult.toArray(result);
        mOneResult = mResult.get(0);            // 많은 예시값 중에서 첫번째거 -> 정확도 높은거 가져오기


Toast.makeText(getActivity().getApplicationContext(),mOneResult,Toast.LENGTH_LONG).show();
        InputAlarmNameDialog dia = new InputAlarmNameDialog();
        dia.data = mOneResult;
        dia.show(fm, "input alarm name");



}}