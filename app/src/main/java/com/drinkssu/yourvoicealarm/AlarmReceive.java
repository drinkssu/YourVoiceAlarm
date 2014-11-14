package com.drinkssu.yourvoicealarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.widget.Toast;

import com.drinkssu.yourvoicealarm.R;

/**
 * Created by KSM on 2014-11-10.
 */
public class AlarmReceive extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Toast.makeText(context, "Alarm Received!", Toast.LENGTH_LONG).show();

        NotificationManager notifier = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent2 = new Intent(context.getApplicationContext(), OffAlarm.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent2);
        PendingIntent intent3 = PendingIntent.getActivity(
                context, 0,
                new Intent(context, OffAlarm.class), 0);

        Notification mNoti = new Notification.Builder(context.getApplicationContext())
                .setContentTitle("알람")
                .setContentText("알람을 해제해주세요")
                .setSmallIcon(R.drawable.ic_launcher)
                .setTicker("띠리리링~!!")
                .setAutoCancel(false)
                .setContentIntent(intent3)
                .build();

        mNoti.flags |= Notification.FLAG_INSISTENT;
        mNoti.flags |= Notification.FLAG_NO_CLEAR;
        mNoti.defaults |= Notification.DEFAULT_ALL;


        notifier.notify(3191, mNoti);

    }

}