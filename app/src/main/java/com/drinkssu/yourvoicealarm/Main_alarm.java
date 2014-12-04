package com.drinkssu.yourvoicealarm;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by KSM on 2014-11-17.
 */
public class Main_alarm extends Activity {

    ImageButton main_btn1;
    ImageButton main_btn2;
    ImageButton main_btn3;
    Animation animation;
    View select_effect1;
    View select_effect2;
    View select_effect3;
    int select_position=1;
    Fragment frag;
    FragmentManager fragM;
    FragmentTransaction fragT;

    int mCurrentFragmentIndex;
    public final static int FRAGMENT_ONE = 0;
    public final static int FRAGMENT_TWO = 1;
    public final static int FRAGMENT_THREE = 2;
    private File mk_user_info = Environment.getExternalStorageDirectory();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_alarm_activity);
        mk_dir();
        frag = new Main_alarm_list();
        view_frag();
        AnimationSet set = new AnimationSet(true);
        set.setInterpolator(new AccelerateInterpolator());
        select_effect1 = (View)findViewById(R.id.select_effect1);
        select_effect2 = (View)findViewById(R.id.select_effect2);
        select_effect3 = (View)findViewById(R.id.select_effect3);

        main_btn1 = (ImageButton)findViewById(R.id.imageButton1);
        main_btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                frag = new Main_alarm_list();
                view_frag();
                if(select_position==1) {
                    ;
                }
                else if(select_position==2) {
                    select_position=1;
                    animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0, 0, 0, 0, 0);
                    animation.setDuration(420);
                    animation.setFillAfter(true);
                    select_effect1.startAnimation(animation);
                }
                else if(select_position==3) {
                    select_position=1;
                    animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 2, Animation.RELATIVE_TO_SELF, 0, 0, 0, 0, 0);
                    animation.setDuration(420);
                    animation.setFillAfter(true);
                    select_effect1.startAnimation(animation);
                }
            }
        });
        main_btn2 = (ImageButton)findViewById(R.id.imageButton2);
        main_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //frag = new RecordVoice();
                frag = new Ranking();
                view_frag();
                if(select_position==1) {
                    select_position=2;
                    animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1, 0, 0, 0, 0);
                    animation.setDuration(420);
                    animation.setFillAfter(true);
                    select_effect1.startAnimation(animation);
                    ;
                }
                else if(select_position==2) {
                    ;
                }
                else if(select_position==3) {
                    select_position=2;
                    animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 2, Animation.RELATIVE_TO_SELF, 1, 0, 0, 0, 0);
                    animation.setDuration(420);
                    animation.setFillAfter(true);
                    select_effect1.startAnimation(animation);
                }
            }
        });
        main_btn3 = (ImageButton)findViewById(R.id.imageButton3);
        main_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag = new RecordVoice();
                view_frag();
                if(select_position==1) {
                    select_position=3;
                    animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 2, 0, 0, 0, 0);
                    animation.setDuration(420);
                    animation.setFillAfter(true);
                    select_effect1.startAnimation(animation);
                    ;
                }
                else if(select_position==2) {
                    select_position=3;
                    animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 2, 0, 0, 0, 0);
                    animation.setDuration(420);
                    animation.setFillAfter(true);
                    select_effect1.startAnimation(animation);
                }
                else if(select_position==3) {
                    ;
                }
            }
        });

        set_user_info();

    }

    private void mk_dir()
    {
        File path = null;
        path = new File(mk_user_info.getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/"); // 디렉토리 만들어쥬는부분!!!!
        if ( !path.exists() )                   // 디렉토리 없으면 만들어요!!
        {
            // 디렉토리가 존재하지 않으면 디렉토리 생성
            path.mkdirs();
        }
    }

    private void set_user_info() {
        File path = null;
        path = new File(mk_user_info.getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/user_info.txt"); // 디렉토리 만들어쥬는부분!!!
        if (!path.exists())                   // 디렉토리 없으면 만들어요!!
        {
            // 디렉토리가 존재하지 않으면 디렉토리 생성
            try {
                String settingStr = "0";
                FileOutputStream fos = new FileOutputStream(path);
                fos.write(settingStr.getBytes());
                fos.close();
                Intent intent = new Intent(this, user_info.class);
                startActivity(intent);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                FileInputStream fis = new FileInputStream(path);
                BufferedReader bufferReader = new BufferedReader(new InputStreamReader(fis));
                String temp="";
                temp = bufferReader.readLine();
                if(temp.toString().equals("0") ) {
                    Intent intent = new Intent(this, user_info.class);
                    startActivity(intent);
                }
                Log.v(null,""+temp);
            } catch (Exception e) {}
        }
    }
    protected void view_frag()
    {
        fragM = getFragmentManager();
        fragT = fragM.beginTransaction();
        fragT.replace(R.id.fragment_place,frag);
        fragT.commit();
    }
}
