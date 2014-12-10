package com.drinkssu.yourvoicealarm;

/**
 * Created by KSM on 2014-12-06.
 */

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class alarmReceiver extends Activity {
    private ArrayList<String> mResult;
    private  String  mOneResult;
    private final int GOOGLE_STT = 1000, MY_UI=1001;
    long[] pattern = {1000, 1000};
    String mRingTone ="";
    String mRingPath ="";
    public static MediaPlayer mMediaPlayer;
    public static AudioManager audioManager;
    Vibrator vibe = null;
    PowerManager.WakeLock wl = null;
    PowerManager pm;
    Calendar calendar;
    // Notification Manager 얻기
    GestureDetector mGestures = null;
    NotificationManager nm = null;
    Notification mNoti;
    public static DBAdapter mDBManager=null;
    boolean startFlag = false;

    //알람 매니저
    public static AlarmManager mManager = null;
    //TextView debug1;
    Button btn_alarm_off, btn_alarm_off2;
    ImageView img;

    private TimerTask mTask;
    private Timer mTimer;
    static int condi=0;
    int check_server;
    Uri strpath;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_receiver);
        mMediaPlayer = new MediaPlayer();
        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        //debug1 = (TextView) findViewById(R.id.debug1);
        img = (ImageView)findViewById(R.id.img);
        btn_alarm_off2 = (Button)findViewById(R.id.btn_alarm_off2);
        btn_alarm_off2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(condi==1) {
                    clearAlarm();
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(), "잠시후 활성화 됩니다.", 7000).show();

            }
        });
        btn_alarm_off = (Button) findViewById(R.id.btn_alarm_off);

        btn_alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Todo 여기다가 stt창 띄워서 입력받기
                nm.cancel(3191);
                startActivityForResult(new Intent(getBaseContext(), CustomUIActivity.class) , MY_UI);




                //Todo 기존에 있던거
                //   Toast.makeText(getApplicationContext(), "알람이 해제되었습니다.", Toast.LENGTH_SHORT).show();
                // clearAlarm();

            }
        });
        pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        if (!pm.isScreenOn()) { // 스크린이 켜져 있지 않으면 켠다
            wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "너의 목소리가 들려");
            wl.acquire();
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    //                  WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        }   mDBManager = DBAdapter.getInstance(this);
        mRingTone = getIntent().getStringExtra("ringtone");
        mRingPath = getIntent().getStringExtra("ringpath");
        Cursor c = mDBManager.query(null, null, null, null, null, mDBManager.ALARM_HOUR + " asc, " + mDBManager.ALARM_MINUTE + " asc");
        c.moveToFirst();
        //mRingTone = c.getString(c.getColumnIndex(DBAdapter.ALARM_RINGTONE));
        //mRingPath = c.getString(c.getColumnIndex(DBAdapter.ALARM_RINGPATH));
        check_server = getIntent().getIntExtra("check_server",0);
        if(mRingTone.equals("오늘도 피로한 당신을 응원합니다"))
            img.setImageResource(R.drawable.ggg);

        int vibrate = getIntent().getIntExtra("vibrate", 1);
        PendingIntent intent3 = PendingIntent.getActivity(
                this, 0,
                new Intent(this, alarmReceiver.class), 0);
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNoti = new Notification.Builder(getApplicationContext())
                .setContentTitle("알람")
                .setContentText("알람을 해제해주세요")
                .setSmallIcon(R.drawable.ic_launcher)
                .setTicker("띠리리링~!!")
                .setAutoCancel(false)
                .setContentIntent(intent3)
                .build();

        mNoti.flags |= Notification.FLAG_INSISTENT;
        mNoti.flags |= Notification.FLAG_NO_CLEAR;
        mNoti.defaults |= Notification.DEFAULT_ALL;
        nm.notify(3191, mNoti);

        if(check_server ==1)
            strpath = Uri.parse("android.resource://com.drinkssu.yourvoicealarm/"+R.raw.a);
        else if(check_server ==2)
            strpath = Uri.parse("android.resource://com.drinkssu.yourvoicealarm/"+R.raw.b);
        else if(check_server ==3)
            strpath = Uri.parse("android.resource://com.drinkssu.yourvoicealarm/"+R.raw.c);
        else if(check_server ==4)
            strpath = Uri.parse("android.resource://com.drinkssu.yourvoicealarm/"+R.raw.d);
        else if(check_server ==5)
            strpath = Uri.parse("android.resource://com.drinkssu.yourvoicealarm/"+R.raw.e);
        else if(check_server ==6)
            strpath = Uri.parse("android.resource://com.drinkssu.yourvoicealarm/"+R.raw.f);
        else if(check_server ==7)
            strpath = Uri.parse("android.resource://com.drinkssu.yourvoicealarm/"+R.raw.g);
        else if(check_server ==8)
            strpath = Uri.parse("android.resource://com.drinkssu.yourvoicealarm/"+R.raw.h);
        else if(check_server ==9)
            strpath = Uri.parse("android.resource://com.drinkssu.yourvoicealarm/"+R.raw.i);
        else if(check_server ==10)
            strpath = Uri.parse("android.resource://com.drinkssu.yourvoicealarm/"+R.raw.j);
        else
            strpath=Uri.parse(mRingPath);
            audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
            mMediaPlayer.setAudioStreamType(audioManager.STREAM_ALARM);
            try {
                mMediaPlayer.setDataSource(this, strpath);
                if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                    mMediaPlayer.prepare();
                }
            } catch (IOException e) {
            }
            mMediaPlayer.setLooping(true);
            mMediaPlayer.start();

        vibe = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
        vibe.vibrate(pattern, 0);

        mTask = new TimerTask() {
            @Override
            public void run() {
                condi=1;
            }
        };

        mTimer = new Timer();

        mTimer.schedule(mTask, 10000);
    }


    private void clearAlarm() {
        if (mMediaPlayer != null) mMediaPlayer.stop();
        mMediaPlayer = null;
        if (wl != null) wl.release();
        vibe.cancel();

        finish();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        nm.cancelAll();
        create_alarm_activity.startFirstAlarm(this, check_server);
    }

    protected void onUserLeaveHint(){
        super.onUserLeaveHint();
        Toast.makeText(getApplicationContext(), "HomeKey를 누르지 마세요!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == -1  && (requestCode == GOOGLE_STT || requestCode == MY_UI) ){
            showSelectDialog(requestCode, data);
            //todo setting 파일 여기서 저장해야할듯

        }
        else if(resultCode == 777 && requestCode == 888)
        {
            //Todo 정답확인 액티비티에서 정답 맞췄을때
            clearAlarm();

        }
        else if(resultCode == 999 && requestCode == 888)
        {
            //Todo 정답확인 액티비티에서 재녹음 눌렀을때
            startActivityForResult(new Intent(getBaseContext(), CustomUIActivity.class) , MY_UI);


        }


    }
    private void showSelectDialog(int requestCode, Intent data) {
        String key = "";
        if(requestCode == GOOGLE_STT)               //���������ν��̸�
            key = RecognizerIntent.EXTRA_RESULTS;   //Ű�� ����
        else if(requestCode == MY_UI)               //���� ���� activity �̸�
            key = SpeechRecognizer.RESULTS_RECOGNITION;   //Ű�� ����

        mResult = data.getStringArrayListExtra(key);      //�νĵ� ������ list �޾ƿ�.
        String[] result = new String[mResult.size()];         //�迭��. ���̾�α׿��� ����ϱ� ����
        mResult.toArray(result);
        mOneResult = mResult.get(0);            // 많은 예시값 중에서 첫번째거 -> 정확도 높은거 가져오기

        Toast.makeText(getApplicationContext(), mOneResult, Toast.LENGTH_SHORT).show();

        if(mOneResult.equals(mRingTone))
        {
            //Todo 한방에 맞을때 종료시켜주기
            clearAlarm();
        }
        else
        {
            //Todo 병신같이 한번에 못맞추면 확인액티비티 띄워주기
            checkSTT.keyString = mRingTone;
            checkSTT.sttString = mOneResult;
            Intent intent = new Intent(getBaseContext(), checkSTT.class);
            startActivityForResult(intent, 888);

        }

    }

}
