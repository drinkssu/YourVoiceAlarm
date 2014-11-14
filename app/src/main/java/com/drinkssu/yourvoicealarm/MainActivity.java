package com.drinkssu.yourvoicealarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class MainActivity extends Activity {

    Button set_Alarm;
    Button release_Alarm;
    TextView mtextView;

    Bundle b_a = new Bundle();
    Bundle b_b = new Bundle();

    GregorianCalendar today = new GregorianCalendar ( );

    int myear = today.get(today.YEAR);
    int mmonth = today.get(today.MONTH);
    int mday = today.get(today.DAY_OF_MONTH);
    int mhh = today.get (today.HOUR_OF_DAY);
    int mmm = today.get (today.MINUTE);

    public static AlarmManager malarm;
    Intent mintent;
    FragmentManager fm = getFragmentManager();
    PendingIntent pender;
    Handler mHandler_b = new Handler(){
        @Override
        public void handleMessage(Message m){
            /** Creating a bundle object to pass currently set Time to the fragment */
            b_b = m.getData();

            mhh = b_b.getInt("set_hour");

            mmm = b_b.getInt("set_minute");
            /** Displaying a short time message containing time set by Timer dialog fragment */
            mtextView.setText(""+ myear +"년 "+ (mmonth+1) +"월 "+ mday +"일 "+mhh+"시 "+mmm+"분");

            Calendar calendar = Calendar.getInstance();
            calendar.set(myear, mmonth , mday, mhh, mmm);

            malarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pender);
            Toast.makeText(getApplicationContext(), "Alram Setting success", Toast.LENGTH_SHORT).show();
        }
    };

    Handler mHandler_a = new Handler(){
        @Override
        public void handleMessage(Message m){
            /** Creating a bundle object to pass currently set Time to the fragment */

            b_a = m.getData();

            myear = b_a.getInt("set_year");

            mmonth = b_a.getInt("set_month");

            mday = b_a.getInt("set_day");

            TimerDialogFragment dTDF = new TimerDialogFragment(mHandler_b);
            dTDF.setArguments(b_b);
            dTDF.show(fm, "Timer Dialog Fragment");
            /** Displaying a short time message containing time set by Timer dialog fragment */
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        set_Alarm = (Button) findViewById(R.id.set_Alarm);
        release_Alarm = (Button) findViewById(R.id.release_Alarm);
        mtextView = (TextView) findViewById(R.id.mtextView);

        mtextView.setText(""+ myear +"년 "+ (mmonth+1) +"월 "+ mday +"일 "+mhh+"시 "+mmm+"분");



        malarm = (AlarmManager) this
                .getSystemService(Context.ALARM_SERVICE);

        mintent = new Intent(this,
                AlarmReceive.class);

        pender = PendingIntent.getBroadcast(
                this, 0, mintent, PendingIntent.FLAG_CANCEL_CURRENT);

        set_Alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b_a.putInt("set_year", myear);

                b_a.putInt("set_month", mmonth);

                b_a.putInt("set_day", mday);

                b_b.putInt("set_hour", mhh);

                b_b.putInt("set_minute", mmm);

                DatePickerFragment dDPF = new DatePickerFragment(mHandler_a);
                dDPF.setArguments(b_a);
                dDPF.show(fm, "Date Picker Fragment");
            }
        });

        release_Alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //malarm=(AlarmManager)this.getSystemService(ALARM_SERVICE);
                malarm.cancel(pender);

                Toast.makeText(getApplicationContext(), "Alram Setting Release", Toast.LENGTH_SHORT).show();
                myear = today.get(today.YEAR);
                mmonth = today.get(today.MONTH);
                mday = today.get(today.DAY_OF_MONTH);
                mhh = today.get (today.HOUR_OF_DAY);
                mmm = today.get (today.MINUTE);
                mtextView.setText(""+ myear +"년 "+ (mmonth+1) +"월 "+ mday +"일 "+mhh+"시 "+mmm+"분");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
