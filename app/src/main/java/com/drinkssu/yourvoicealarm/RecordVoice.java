package com.drinkssu.yourvoicealarm;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
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
import android.widget.Toast;
import java.io.IOException;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;


public class RecordVoice  extends Fragment {
    int count;
    FragmentManager fm = getFragmentManager();
    MediaPlayer player = new MediaPlayer();             // 음성 재생을 위한 MediaPlayer 선언
    MediaRecorder recorder = new MediaRecorder();         // 음성 녹음을 위한 MediaRecord 선언
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




File path = null;
        path = new File(RECORED_FILE.getAbsolutePath()+"/YourVoiceAlarm/alarm_user"); // 디렉토리 만들어쥬는부분!!!



        if ( !path.exists() )                   // 디렉토리 없으면 만들어요!!
        {
            // 디렉토리가 존재하지 않으면 디렉토리 생성
            path.mkdirs();
        }

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(RECORED_FILE.getAbsolutePath() + "/YourVoiceAlarm/alarm_user/yourAlarm.mp4");
        recorder.setMaxDuration(4000);


        Button btnRecord = (Button)getActivity().findViewById(R.id.bRecordVoice);

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
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
                    Toast.makeText(getActivity().getApplicationContext(), "3초간 녹음이 시작됩니다", Toast.LENGTH_SHORT).show();
                    recorder.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                recorder.start();

                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recorder.stop();
                        recorder.release();
                        recorder = null;
                        Toast.makeText(getActivity().getApplicationContext(), "녹음종료", Toast.LENGTH_SHORT).show();

                     InputAlarmNameDialog dia = new InputAlarmNameDialog();
                    dia.show(fm, "input alarm name");

                    }
                }, 3000);



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
}
