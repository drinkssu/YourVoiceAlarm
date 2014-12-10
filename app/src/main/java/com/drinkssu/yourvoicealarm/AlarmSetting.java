package com.drinkssu.yourvoicealarm;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by JinGyu on 2014-12-04.
 */
public class AlarmSetting extends Activity {


    Button btnCancel, btnOk;
    RadioGroup rg;
    int sel;
    FragmentManager fm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setting);



        fm= getFragmentManager();
        rg = (RadioGroup)findViewById(R.id.rgCategory);
        rg.setOnCheckedChangeListener(mRadioCheck);
        btnCancel = (Button)findViewById(R.id.bCancel);
        btnOk = (Button)findViewById(R.id.bOk);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //todo 파일저장 속성 해주기

                //todo 리졸트값 전송해주기
                Intent i = new Intent();			//��� ��ȯ�� intent
                setResult(-1, i);		//��� ����
                finish();							//�� ����
            }
        });





    }

    RadioGroup.OnCheckedChangeListener mRadioCheck = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if(rg.getId()==R.id.rgCategory)
            {

                switch (checkedId)
                {
                    case R.id.r0:
                        sel = 0;
                        InputAlarmNameDialog.category=0;
                        break;

                    case R.id.r1:
                        sel = 1;
                        InputAlarmNameDialog.category=1;
                        break;

                    case R.id.r2:
                        sel = 2;
                        InputAlarmNameDialog.category=2;
                        break;

                    case R.id.r3:
                        sel = 3;
                        InputAlarmNameDialog.category=3;
                        break;

                    case R.id.r4:
                        sel = 4;
                        InputAlarmNameDialog.category=4;
                        break;

                    case R.id.r5:
                        sel = 5;
                        InputAlarmNameDialog.category=5;
                        break;

                    case R.id.r6:
                        sel = 6;
                        InputAlarmNameDialog.category=6;
                        break;

                    case R.id.r7:
                        sel = 7;
                        InputAlarmNameDialog.category=7;
                        break;

                }
        }
    };


};}
