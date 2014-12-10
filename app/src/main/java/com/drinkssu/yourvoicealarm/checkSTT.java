package com.drinkssu.yourvoicealarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class checkSTT extends ActionBarActivity {

    static String keyString;
    static String sttString;

    Button btnReSTT, btnAlarmOff;
    EditText sttData;
    TextView key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_stt);
        //Todo 정답값 확인용

        init();


    }

    private void init() {
        btnAlarmOff = (Button)findViewById(R.id.btnAlarmOff);
        btnReSTT = (Button)findViewById(R.id.btnReSTT);
        sttData = (EditText)findViewById(R.id.stt);
        key = (TextView)findViewById(R.id.key);
        key.setText("key : "+keyString);
        btnAlarmOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo 알람해제버튼 눌렀을때. keyString이랑 비교해서 종료해줄것

                if(sttData.getText().toString().equals(keyString))
                {
                    //Todo 정답일때 -> 알람 종료시켜주기
                    //Todo 이거 result 로 띄울꺼니까 종료시켜줄 코드 만들어서 보내고 종료는 Receiver에서 처리할것
                    Intent i = new Intent();
                    setResult(777, i);
                    finish();

                }
                else
                {
                    //Todo 정답 아닐때 -> 이창 다시 띄워줘야함함 -> 토스트로 해보쟈

                    Toast.makeText(getApplicationContext(),"틀렸습니다. 다시입력해주세요.", Toast.LENGTH_SHORT).show();

                }


            }
        });

        btnReSTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo Stt 입력창 또 띄워주기 값 받아와서 비교하고 다시 부모 액티비티에 전달해주기
                //Todo 이놈도 result 보내서 stt창 또 띄우게!!! 굳굳개이득
                Intent i = new Intent();
                setResult(999, i);
                finish();
            }
        });

        sttData.setText(sttString);

    }
}