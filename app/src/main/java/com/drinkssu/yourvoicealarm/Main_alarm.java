package com.drinkssu.yourvoicealarm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_alarm_activity);


        AnimationSet set = new AnimationSet(true);
        set.setInterpolator(new AccelerateInterpolator());
        select_effect1 = (View)findViewById(R.id.select_effect1);
        select_effect2 = (View)findViewById(R.id.select_effect2);
        select_effect3 = (View)findViewById(R.id.select_effect3);

        main_btn1 = (ImageButton)findViewById(R.id.imageButton1);
        main_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(select_position==1) {
                    ;
                }
                else if(select_position==2) {
                    select_position=1;
                    animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0, 0, 0, 0, 0);
                    animation.setDuration(420);
                    animation.setFillAfter(true);
                    select_effect1.startAnimation(animation);
                    ;
                }
                else if(select_position==3) {
                    select_position=1;
                    animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 2, Animation.RELATIVE_TO_SELF, 0, 0, 0, 0, 0);
                    animation.setDuration(420);
                    animation.setFillAfter(true);
                    select_effect1.startAnimation(animation);
                    ;
                }
            }
        });
        main_btn2 = (ImageButton)findViewById(R.id.imageButton2);
        main_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                    ;

                }
            }
        });
        main_btn3 = (ImageButton)findViewById(R.id.imageButton3);
        main_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    }
}
