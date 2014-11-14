package com.drinkssu.yourvoicealarm;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import java.util.GregorianCalendar;

/**
 * Created by KSM on 2014-11-14.
 */
public class OffAlarm extends Activity{
    Ringtone r;
    Vibrator Vi;
    long[] pattern = {1000, 1000};

    Button btn1;
    Button btn2;
    Button Abtn;
    NotificationManager nm;
    TextView mtextView;
    EditText answer;
    int rnd_num1, rnd_num2;
    int condi;
    Random rnd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        r = RingtoneManager.getRingtone(this, Settings.System.DEFAULT_RINGTONE_URI);
        r.play();
        Vi = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
        Vi.vibrate(pattern, 0);                                         // 패턴을 지정하고 반복횟수를 지정

        GregorianCalendar today = new GregorianCalendar ( );

        rnd = new Random();

        int myear = today.get(today.YEAR);
        int mmonth = today.get(today.MONTH);
        int mday = today.get(today.DAY_OF_MONTH);
        int mhh = today.get (today.HOUR_OF_DAY);
        int mmm = today.get (today.MINUTE);
        condi=0;
        // notification 매니저 생성
        nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        answer = (EditText)findViewById(R.id.Answer);

        mtextView = (TextView)findViewById(R.id.mtextView);
        mtextView.setText(""+ myear +"년 "+ (mmonth+1) +"월 "+ mday +"일 "+mhh+"시 "+mmm+"분 입니다.\n알람을 해제해주세요.");
        mtextView.setTextColor(Color.parseColor("#FF000000"));
        btn1 = (Button)findViewById(R.id.set_Alarm);
        btn1.setText("Turn_Off_by_math");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                condi=0;
                rnd_num1 = rnd.nextInt(100);
                rnd_num2 = rnd.nextInt(100);

                mtextView.setText(rnd_num1+" + "+rnd_num2+" = ?");
                answer.setHint("Write Here~!");
                Abtn.setVisibility(View.VISIBLE);
                answer.setVisibility(View.VISIBLE);
                btn1.setVisibility(View.INVISIBLE);
                btn2.setVisibility(View.INVISIBLE);
            }
        });

        btn2 = (Button)findViewById(R.id.release_Alarm);
        btn2.setText("Turn_Off_by_IQtest");
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                condi=1;
                rnd_num1 = rnd.nextInt(4);
                answer.setHint("choose : red , green , blue , yellow");
                if(rnd_num1==0) mtextView.setTextColor(Color.parseColor("#FFFF0000")); //빨강
                else if(rnd_num1==1) mtextView.setTextColor(Color.parseColor("#FF00FF00")); //초록
                else if(rnd_num1==2) mtextView.setTextColor(Color.parseColor("#FF0000FF")); //파랑
                else if(rnd_num1==3) mtextView.setTextColor(Color.parseColor("#FFFFFF00")); //노랑
                mtextView.setText("What is Text Color ?");
                Abtn.setVisibility(View.VISIBLE);
                answer.setVisibility(View.VISIBLE);
                btn1.setVisibility(View.INVISIBLE);
                btn2.setVisibility(View.INVISIBLE);
            }
        });

        Abtn = (Button)findViewById(R.id.Abtn);
        Abtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(condi==0 && answer.getText().toString().equals(String.valueOf(rnd_num1+rnd_num2))) {
                    nm.cancel(3191);
                    r.stop();
                    Vi.cancel();
                    finish();
                }
                else if(condi==1) {
                    if (rnd_num1==0 && answer.getText().toString().equals("red")) {
                        nm.cancel(3191);
                        r.stop();
                        Vi.cancel();
                        finish();
                    }
                    else if (rnd_num1==1 && answer.getText().toString().equals("green")){
                        nm.cancel(3191);
                        r.stop();
                        Vi.cancel();
                        finish();
                    }
                    else if (rnd_num1==2 && answer.getText().toString().equals("blue")){
                        nm.cancel(3191);
                        r.stop();
                        Vi.cancel();
                        finish();
                    }
                    else if (rnd_num1==3 && answer.getText().toString().equals("yellow")){
                        nm.cancel(3191);
                        r.stop();
                        Vi.cancel();
                        finish();
                    }
                    else
                    {
                        mtextView.setTextColor(Color.parseColor("#FF000000"));
                        mtextView.setText("Wrong !!!");
                        answer.setText("");
                        Abtn.setVisibility(View.INVISIBLE);
                        answer.setVisibility(View.INVISIBLE);
                        btn1.setVisibility(View.VISIBLE);
                        btn2.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    mtextView.setText("Wrong !!!");
                    answer.setText("");
                    Abtn.setVisibility(View.INVISIBLE);
                    answer.setVisibility(View.INVISIBLE);
                    btn1.setVisibility(View.VISIBLE);
                    btn2.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void onUserLeaveHint(){
        super.onUserLeaveHint();
    }
}