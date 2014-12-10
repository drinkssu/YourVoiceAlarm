package com.drinkssu.yourvoicealarm;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;


public class create_alarm_activity extends ActionBarActivity {

    public static DBAdapter mDBManager=null;
    private static PendingIntent sender;
    //Ringtone 변수
    private static String strRingTone="";
    private String strRingPath="";
    //알람 매니저
    public static AlarmManager mManager = null;
    //시각 설정 클래스
    private TimePicker mTime;
    //달력
    private Calendar calendar;
    //진동
    private static int mAlarmHour;
    private static int mAlarmMinute;

    public static long db_id = -1;
    //notification member value(통지 관련 멤버 변수)
    private NotificationManager mNotification;

    private TimePicker timePicker;
    private CheckBox sun, mon, tue, wen, thu, fri, sat;
    //private Switch switchRepeat;
    private TextView selectAlarm;
    private FragmentManager fm;

    static final int alarmSetId = 3191;
    int rnd_num;
    Random rnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_alarm_activity);
        mDBManager = DBAdapter.getInstance(this);


        init();




    }

    private void init() {

        fm = getFragmentManager();
        calendar = Calendar.getInstance();
        //통지 매니저를 흭득
        mNotification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //알람 매니저를 취득
        mManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        timePicker = (TimePicker) findViewById(R.id.timePicker);

        timePicker.setCurrentMinute(timePicker.getCurrentMinute()+1);
        sun = (CheckBox) findViewById(R.id.sun);
        mon = (CheckBox) findViewById(R.id.mon);
        tue = (CheckBox) findViewById(R.id.tue);
        wen = (CheckBox) findViewById(R.id.wen);
        thu = (CheckBox) findViewById(R.id.thu);
        fri = (CheckBox) findViewById(R.id.fri);
        sat = (CheckBox) findViewById(R.id.sat);
       // switchRepeat = (Switch) findViewById(R.id.switchRepeat);
        selectAlarm = (TextView) findViewById(R.id.selectRingtone);


        selectAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectAlarmListDialog showTotalAlarmList = new SelectAlarmListDialog();

                showTotalAlarmList.show(fm, "show total alarm list");


            }


        });

        //진동모드나 사일런트 모드일 때도 벨 울리게
        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT //: 사일런트 모드일 경우(값0)
                || mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE)
        { //: 진동모드일 경우(값1))
            int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
            mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, maxVolume, 0);
        }


        db_id = getIntent().getLongExtra("id", -1);
        if(db_id != -1){
            mAlarmHour = getIntent().getIntExtra("hour", 12);
            mAlarmMinute = getIntent().getIntExtra("min", 33);
            strRingTone = getIntent().getStringExtra("ring");
            strRingPath = getIntent().getStringExtra("ringpath");
            int day = getIntent().getIntExtra("day", 0);
            timePicker.setCurrentHour(mAlarmHour);
            timePicker.setCurrentMinute(mAlarmMinute);

            if (strRingTone != null) {
                selectAlarm.setText(strRingTone);
            }

            // 요일
            if ((day & 0x0001) == 0x0001) ((CheckBox)findViewById(R.id.sun)).setChecked(true);
            if ((day & 0x0002) == 0x0002) ((CheckBox)findViewById(R.id.mon)).setChecked(true);
            if ((day & 0x0004) == 0x0004) ((CheckBox)findViewById(R.id.tue)).setChecked(true);
            if ((day & 0x0008) == 0x0008) ((CheckBox)findViewById(R.id.wen)).setChecked(true);
            if ((day & 0x0010) == 0x0010) ((CheckBox)findViewById(R.id.thu)).setChecked(true);
            if ((day & 0x0020) == 0x0020) ((CheckBox)findViewById(R.id.fri)).setChecked(true);
            if ((day & 0x0040) == 0x0040) ((CheckBox)findViewById(R.id.sat)).setChecked(true);

            //if (day != 0) { button.setEnabled(true); }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_alarm_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Save) {
            setalarm();
            return true;
        }
        else if (id == R.id.action_Cancel) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setalarm() {
        //TimePicker h, m 가져오기
        mAlarmHour = timePicker.getCurrentHour();
        mAlarmMinute = timePicker.getCurrentMinute();
        rnd = new Random();
        //요일 반복 체크
        int apday = 0;
        if(sun.isChecked())apday|=0x0001;
        if(mon.isChecked())apday|=0x0002;
        if(tue.isChecked())apday|=0x0004;
        if(wen.isChecked())apday|=0x0008;
        if(thu.isChecked())apday|=0x00010;
        if(fri.isChecked())apday|=0x00020;
        if(sat.isChecked())apday|=0x00040;
        int check_server=0;
        if(selectAlarm.getText().toString().equals("너의 목소리가 들려 (서버랜덤알람)")) {
            Toast.makeText(getApplicationContext(), "서버통신중", 4000).show();
            rnd_num = rnd.nextInt(10) %10 ;
            if (rnd_num == 0) {
                strRingTone = "감기 조심하세요";
            } else if (rnd_num == 1){
                strRingTone = "멍 멍 멍 멍 멍 멍 멍 멍";
            }else if(rnd_num == 2) {
                strRingTone = "밥 밥 밥 밥 밥 밥 먹자";
            }else if(rnd_num == 3) {
                strRingTone = "뿌 르 르 르 르 르 르 드";
            }else if(rnd_num == 4) {
                strRingTone = "수업가야지 일어나";
            }else if(rnd_num == 5){
                strRingTone = "오늘도 피로한 당신을 응원합니다";
            }else if(rnd_num == 6){
                strRingTone = "일어나 임마";
            }else if(rnd_num == 7){
                strRingTone = "좋은아침이에요";
            }else if(rnd_num == 8){
                strRingTone = "이놈의 간나새끼 빨리 일어나야지";
            }else if(rnd_num == 9){
                strRingTone = "오빠야 안일나고 모 하는데 빨리 일어나라 빨리빨리";
            }
            check_server=rnd_num+1;

         }
        else
        {
            strRingPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.alarm_user_v/"+selectAlarm.getText().toString()+".mp4";
            strRingTone =selectAlarm.getText().toString();
        }


        if (apday == 0)
        {
            Toast.makeText(getApplicationContext(), "요일을 선택하세요", 7000).show();
            return;
        }
        if (selectAlarm.getText().toString().equals("  알람음을 선택해주세요"))
        {
            Toast.makeText(getApplicationContext(), "알람음을 선택해주세요", 7000).show();
            return;
        }

        ContentValues addRowValue = new ContentValues();
        addRowValue.put("onoff", 1);
        addRowValue.put("apday", apday);
        addRowValue.put("hour", mAlarmHour);
        addRowValue.put("minute", mAlarmMinute);
        addRowValue.put("vibrate", 1);
        addRowValue.put("ring", strRingTone);
        addRowValue.put("ringpath", strRingPath);


        Toast.makeText(getApplicationContext(), "알람이 설정되었습니다.", Toast.LENGTH_SHORT).show();

        if (db_id == -1) {
            mDBManager.insert(addRowValue);
        } else {
            mDBManager.modifyAlarm(db_id, 1, apday, mAlarmHour, mAlarmMinute, 1, strRingTone);
        }


        startFirstAlarm(this, check_server);
        finish();

///YourVioceAlarm/user_alarm_v/TextView.getText().toString()+"mp4"  메모


    }

    public static void startFirstAlarm(Context context, int check_server) {
        int apday;
        int onoff;
        int day;
        int hour;
        int min;
        int vib;
        String ring;
        String ringpath;
        Calendar calendar = Calendar.getInstance();
        int c_day = calendar.get(Calendar.DAY_OF_WEEK);
        int c_hour = calendar.get(Calendar.HOUR_OF_DAY);
        int c_min = calendar.get(Calendar.MINUTE);
        //minimum  가장 가가운 id를 찾는 변수
        int m_day = 100;
        int m_hour = 100;
        int m_min = 100;
        int m_vib = 1;
        String m_ring = null;
        String m_ringpath= null;
        long m_id;
        // 오늘의 년-월-일
        // 시간이 경과한 알람을 DB에서 제거



        Cursor c = mDBManager.query(null, null, null, null, null, mDBManager.ALARM_HOUR + " asc, " + mDBManager.ALARM_MINUTE + " asc");

        if (c.moveToFirst()) {	// 첫번째로 이동
            do {
                onoff =  c.getInt(c.getColumnIndex(DBAdapter.ALARM_ON));
                if (onoff == 1) {
                    day =  c.getInt(c.getColumnIndex(DBAdapter.ALARM_APDAY));
                    hour = c.getInt(c.getColumnIndex(DBAdapter.ALARM_HOUR));
                    min = c.getInt(c.getColumnIndex(DBAdapter.ALARM_MINUTE));
                    vib = c.getInt(c.getColumnIndex(DBAdapter.ALARM_VIBRATE));
                    ring = c.getString(c.getColumnIndex(DBAdapter.ALARM_RINGTONE));
                    ringpath = c.getString(c.getColumnIndex(DBAdapter.ALARM_RINGPATH));

                    //
                    for (int i = 0; i < 7; i++) {
                        if ((day & 0x01) == 0x01) {
                            apday = i+1;
                            if ((apday < c_day)
                                    || ((apday == c_day) && (hour < c_hour))
                                    || ((apday == c_day) && (hour == c_hour) && (min <= c_min))){
                                apday += 7;
                            }

                            if (m_day > apday){
                                m_day = apday;
                                m_hour = hour;
                                m_min = min;
                                m_id = c.getLong(c.getColumnIndex("_id"));
                                m_vib = vib;
                                m_ring = ring;
                                m_ringpath = ringpath;
                            } else if ((m_day == apday ) && (m_hour > hour)){
                                m_hour = hour;
                                m_min = min;
                                m_id = c.getLong(c.getColumnIndex("_id"));
                                m_vib = vib;
                                m_ring = ring;
                                m_ringpath = ringpath;
                            } else if ((m_day == apday) && (m_hour == hour) && (m_min > min)){
                                m_min = min;
                                m_id = c.getLong(c.getColumnIndex("_id"));
                                m_vib = vib;
                                m_ring = ring;
                                m_ringpath = ringpath;
                            }
                        }
                        day = day >> 1;
                    }
                }
            } while (c.moveToNext());
        }
        if( m_day != 100){
            Intent intent = new Intent(context, alarmReceiver.class);
            intent.putExtra("ringtone", m_ring);
            intent.putExtra("vibrate", m_vib);
            intent.putExtra("ringpath", m_ringpath);
            intent.putExtra("check_server", check_server);
            //PendingIntent sender = PendingIntent.getBroadcast(afternoonAlarm.this, 0, intent, 0);
            calendar.add(Calendar.DAY_OF_MONTH, m_day - c_day);
            calendar.set(Calendar.HOUR_OF_DAY, m_hour);
            calendar.set(Calendar.MINUTE, m_min);
            calendar.set(Calendar.SECOND, 0);

            sender = PendingIntent.getActivity(context, alarmSetId, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            mManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, sender);

        }

    }
    public static void cancelAlarm(Context context) {
        mManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        mManager.cancel(sender);
        //Toast.makeText(context, "알람이 해제됐습니다.", Toast.LENGTH_SHORT).show();
    }
}
