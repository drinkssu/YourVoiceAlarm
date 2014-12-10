package com.drinkssu.yourvoicealarm;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Created by KSM on 2014-12-04.
 */
public class user_info extends Activity implements RadioGroup.OnCheckedChangeListener {
    Button go;
    RadioButton m_radio, w_radio;
    EditText mnickname;
    RadioGroup g_radio;
    int gender=0;

    private File mk_user_info = Environment.getExternalStorageDirectory();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);

        go = (Button)findViewById(R.id.go);
        m_radio = (RadioButton)findViewById(R.id.m_radio);
        w_radio = (RadioButton)findViewById(R.id.m_radio);
        mnickname = (EditText)findViewById(R.id.mnickname);
        g_radio = (RadioGroup)findViewById(R.id.g_radio);
        g_radio.setOnCheckedChangeListener(this);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mnickname.length()>0){
                    WriteTextFile(mnickname.getText().toString());
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(), "유저닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean WriteTextFile(String strBuf) {
        try {

            File path = null;
            path = new File(mk_user_info.getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/user_info.txt"); // 디렉토리 만들어쥬는부분!!!
            FileOutputStream fos = new FileOutputStream(path);
            Writer out = new OutputStreamWriter(fos, "UTF-8");
            strBuf+="\n"+gender;
            out.write(strBuf);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.m_radio:
                gender = 0;
                break;
            case R.id.w_radio:
                gender = 1;
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
/*
check_server=0;
        if(check_server == 1) {
            AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            if (mRingTone.equals("감기 조심하세요")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.a);
            } else if (mRingTone.equals("멍 멍 멍 멍 멍 멍 멍 멍")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.b);
            } else if (mRingTone.equals("밥 밥 밥 밥 밥 밥 먹자")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.c);
            } else if (mRingTone.equals("뿌 르 르 르 르 르 르 드")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.d);
            } else if (mRingTone.equals("수업가야지 일어나")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.e);
            } else if (mRingTone.equals("오늘도 피로한 당신을 응원합니다")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.f);
            } else if (mRingTone.equals("일어나 임마")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.g);
            } else if (mRingTone.equals("좋은아침이에요")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.h);
            } else if (mRingTone.equals("이놈의 간나새끼 빨리 일어나야지")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.i);
            } else if (mRingTone.equals("오빠야 안일나고 모 하는데 빨리 일어나라 빨리빨리")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.j);
            }
            mMediaPlayer.setLooping(true);
            mMediaPlayer.start();
        }
        else {
            Uri strpath = Uri.parse("android.resource://com.drinkssu.yourvoicealarm/" + R.raw.a);
            //Uri strpath=Uri.parse(mRingPath);
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
        }
 */