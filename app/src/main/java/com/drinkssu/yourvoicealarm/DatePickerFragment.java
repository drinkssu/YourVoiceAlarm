package com.drinkssu.yourvoicealarm;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Handler;
import android.app.Dialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Message;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    final Calendar cal = new GregorianCalendar();
    Handler mHandler ;
    int myear;
    int mmonth;
    int mday;
    FragmentManager fm = getFragmentManager();

    public DatePickerFragment(Handler h){
        /** Getting the reference to the message handler instantiated in MainActivity class */
        mHandler = h;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker

        myear = cal.get(Calendar.YEAR);
        mmonth = cal.get(Calendar.MONTH);
        mday = cal.get(Calendar.DAY_OF_MONTH);

        Bundle b = getArguments();

        myear = b.getInt("set_year");

        mmonth = b.getInt("set_month");

        mday = b.getInt("set_day");

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, myear, mmonth, mday);
    }

    @Override
    public void onDateSet(DatePicker view, int myear, int mmonth, int mday) {
        updateDate(myear, mmonth, mday);
        {
            // TODO Auto-generated method stub
            Bundle b = new Bundle();

            b.putInt("set_year", myear);

            b.putInt("set_month", mmonth);

            b.putInt("set_day", mday);
            /** Creating an instance of Message */
            Message m = new Message();

            /** Setting bundle object on the message object m */
            m.setData(b);

            /** Message m is sending using the message handler instantiated in MainActivity class */
            mHandler.sendMessage(m);

        }
    }

    public void updateDate(int myear, int mmonth, int mday) {
        this.myear = myear;
        this.mmonth = mmonth;
        this.mday = mday;
        cal.set(myear, mmonth, mday);
    }

}
