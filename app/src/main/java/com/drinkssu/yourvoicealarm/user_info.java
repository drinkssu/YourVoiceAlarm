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
import java.io.InputStream;
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
    int sex=0;

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
            strBuf+="\n"+sex;
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
                sex = 0;
                break;
            case R.id.w_radio:
                sex = 1;
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
