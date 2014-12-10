package com.drinkssu.yourvoicealarm;

import android.app.Activity;
import android.app.NotificationManager;
import android.graphics.Color;
import android.media.Ringtone;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Created by KSM on 2014-11-14.
 */
public class eoff2 extends Activity{
    Ringtone r;
    Vibrator Vi;
    long[] pattern = {1000, 1000};

    Button btn1;
    Button Abtn;
    NotificationManager nm;
    TextView mtextView;
    EditText answer;
    int rnd_num1, rnd_num2;
    int condi;
    Random rnd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eoff);

        GregorianCalendar today = new GregorianCalendar ( );

        rnd = new Random();

        int myear = today.get(today.YEAR);
        int mmonth = today.get(today.MONTH);
        int mday = today.get(today.DAY_OF_MONTH);
        int mhh = today.get (today.HOUR_OF_DAY);
        int mmm = today.get (today.MINUTE);
        condi=0;
        answer = (EditText)findViewById(R.id.Answer);

        mtextView = (TextView)findViewById(R.id.mtextView);
        mtextView.setText(""+ myear +"년 "+ (mmonth+1) +"월 "+ mday +"일 "+mhh+"시 "+mmm+"분 입니다.\n알람이 해제되지않을경우 아래버튼 통해 알람을 해제해주세요.");
        mtextView.setTextColor(Color.parseColor("#FF000000"));
        btn1 = (Button)findViewById(R.id.set_Alarm);
        btn1.setText("수학문제 풀어서 알람 끄기");
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
            }
        });

        Abtn = (Button)findViewById(R.id.Abtn);
        Abtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(condi==0 && answer.getText().toString().equals(String.valueOf(rnd_num1+rnd_num2))) {
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