package com.drinkssu.yourvoicealarm;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by KSM on 2014-11-18.
 */

public class Main_alarm_list extends Fragment {
    private MyCursorAdapter adapter;
    private ListView list;
    public static DBAdapter mDBManager=null;
    private Cursor currentCursor;
    //
    private static int colID;
    private static int colONOFF;
    private static int colHOUR;
    private static int colMINUTE;
    private static int colDAY;
    private static int colRING;
    private static int colVIB;
    private static int colRINGPATH;
    Button add_alarm;
    public AdapterView.OnItemClickListener itemClickListener;
    public View.OnTouchListener TouchListener;

    int QuickMenuEvent = 0;
    float CheckedColumn_x = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.main_alarm_list_activity, container, false);

    }

    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();// column index

        mDBManager = DBAdapter.getInstance(getActivity());
        //
        currentCursor = mDBManager.fetchAllAlarm();
        colID = currentCursor.getColumnIndex("_id");
        colONOFF = currentCursor.getColumnIndex(DBAdapter.ALARM_ON);
        colDAY = currentCursor.getColumnIndex(DBAdapter.ALARM_APDAY);
        colHOUR = currentCursor.getColumnIndex(DBAdapter.ALARM_HOUR);
        colMINUTE = currentCursor.getColumnIndex(DBAdapter.ALARM_MINUTE);
        colRING = currentCursor.getColumnIndex(DBAdapter.ALARM_RINGTONE);
        colVIB = currentCursor.getColumnIndex(DBAdapter.ALARM_VIBRATE);
        colRINGPATH= currentCursor.getColumnIndex(DBAdapter.ALARM_RINGPATH);
        //
        add_alarm = (Button) getActivity().findViewById(R.id.add_alarm);
        add_alarm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), create_alarm_activity.class);
                startActivity(intent);
            }
        });

        //
        list = (ListView)getActivity().findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentCursor.moveToPosition(position);
                ImageView icon_view = (ImageView)view.findViewById(R.id.toggleButton1);
                ImageView delete_view = (ImageView)view.findViewById(R.id.alarm_delete);


                //
                if (QuickMenuEvent == MotionEvent.ACTION_UP) {
                    if (icon_view.getLeft() < CheckedColumn_x && CheckedColumn_x < icon_view.getRight()) {
                        long db_id = currentCursor.getLong(colID);
                        int on = currentCursor.getInt(colONOFF);
                        //
                        if (on == 0) on = 1;
                        else on = 0;
                        //
                        mDBManager.modifyAlarmOn(db_id, on);
                        currentCursor = mDBManager.fetchAllAlarm();
                        adapter.notifyDataSetChanged();
                        //
                        //	calendar = Calendar.getInstance();
                        if(on == 1){
                            icon_view.setImageResource(R.drawable.clock_on);
                            Toast.makeText(getActivity(), "알람이 설정 되었습니다. "
                                    ,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            icon_view.setImageResource(R.drawable.clock_off);
                            Toast.makeText(getActivity(), "알람이 해제됐습니다.", Toast.LENGTH_SHORT).show();
                        }

                    } else if (delete_view.getLeft() < CheckedColumn_x && CheckedColumn_x < delete_view.getRight()) {
                        new AlertDialog.Builder(getActivity())
                                .setMessage("삭제하시겠습니까?")
                                .setCancelable(false)
                                .setPositiveButton("예",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                long db_id = currentCursor.getLong(colID);
                                                mDBManager.delAlarm("" + db_id);
                                                currentCursor = mDBManager.fetchAllAlarm();
                                                adapter.notifyDataSetChanged();
                                                create_alarm_activity.cancelAlarm(getActivity());
                                                //create_alarm_activity.startFirstAlarm(getActivity());
                                            }
                                        })
                                .setNegativeButton("아니요", null)
                                .show();
                    } else{
                        long db_id = currentCursor.getLong(colID);

                        // 최고 우선 순위 알람에만 적용됨 수정할 부분
                        Intent intent = new Intent(getActivity(), create_alarm_activity.class);
                        intent.putExtra("id", db_id);
                        intent.putExtra("day", currentCursor.getInt(colDAY));
                        intent.putExtra("hour", currentCursor.getInt(colHOUR));
                        intent.putExtra("min", currentCursor.getInt(colMINUTE));
                        intent.putExtra("ring", currentCursor.getString(colRING));
                        intent.putExtra("vib", currentCursor.getInt(colVIB));
                        intent.putExtra("ringpath", currentCursor.getInt(colRINGPATH));

                        startActivity(intent);
                    }

                }
            }
        });
        list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 여기서 view 는 ListItem 이 아닌  리스트 자체임
                CheckedColumn_x = event.getX();
                //CheckedColumn_y = event.getY();
                QuickMenuEvent = event.getAction();

                return false;
            }
        });
        String[] from = new String[] {DBAdapter.ALARM_HOUR, DBAdapter.ALARM_MINUTE};
        int[] to = new int[] {R.id.alarm_row_time, R.id.alarm_row_day};
        //
        adapter = new MyCursorAdapter(list.getContext(), R.layout.alarm_list_row, currentCursor, from, to);
        list.setAdapter(adapter);

        //////////
    }

   /* @Override
    public void onResume() {
        super.onResume();
        //
       // currentCursor = mDBManager.fetchAllAlarm();

        adapter.notifyDataSetChanged();
    }
*/
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class MyCursorAdapter extends SimpleCursorAdapter {
        Context my_context;
        private int mRowLayout;

        MyCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
            super(context, layout, c, from, to);
            my_context = context;
            mRowLayout = layout;
        }
        /*
    public View.OnTouchListener TouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch (View view, MotionEvent event) {
                // 여기서 view 는 ListItem 이 아닌  리스트 자체임
                CheckedColumn_x = event.getX();
                //CheckedColumn_y = event.getY();
                QuickMenuEvent = event.getAction();

                return false;
            }
        };

    public AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?>list, View view, int position, long id) {
                currentCursor.moveToPosition(position);
                ImageView icon_view = (ImageView)view.findViewById(R.id.toggleButton1);
                ImageView delete_view = (ImageView)view.findViewById(R.id.alarm_delete);


                //
                if (QuickMenuEvent == MotionEvent.ACTION_UP) {
                    if (icon_view.getLeft() < CheckedColumn_x && CheckedColumn_x < icon_view.getRight()) {
                        long db_id = currentCursor.getLong(colID);
                        int on = currentCursor.getInt(colONOFF);
                        //
                        if (on == 0) on = 1;
                        else on = 0;
                        //
                        mDBManager.modifyAlarmOn(db_id, on);
                        currentCursor = mDBManager.fetchAllAlarm();
                        adapter.notifyDataSetChanged();
                        //
                        //	calendar = Calendar.getInstance();
                        if(on == 1){
                            icon_view.setImageResource(R.drawable.clock_on);
                            Toast.makeText(getActivity(), "알람이 설정 되었습니다. "
                                    ,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            icon_view.setImageResource(R.drawable.clock_off);
                            Toast.makeText(getActivity(), "알람이 해제됐습니다.", Toast.LENGTH_SHORT).show();
                        }

                    } else if (delete_view.getLeft() < CheckedColumn_x && CheckedColumn_x < delete_view.getRight()) {
                        new AlertDialog.Builder(getActivity())
                                .setMessage("삭제하시겠습니까?")
                                .setCancelable(false)
                                .setPositiveButton("예",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                long db_id = currentCursor.getLong(colID);
                                                mDBManager.delAlarm("" + db_id);
                                                currentCursor = mDBManager.fetchAllAlarm();
                                                adapter.notifyDataSetChanged();
                                                create_alarm_activity.cancelAlarm(getActivity());
                                                create_alarm_activity.startFirstAlarm(getActivity());
                                            }
                                        })
                                .setNegativeButton("아니요", null)
                                .show();
                    } else{
                        long db_id = currentCursor.getLong(colID);

                        // 최고 우선 순위 알람에만 적용됨 수정할 부분
                        Intent intent = new Intent(getActivity(), create_alarm_activity.class);
                        intent.putExtra("id", db_id);
                        intent.putExtra("day", currentCursor.getInt(colDAY));
                        intent.putExtra("hour", currentCursor.getInt(colHOUR));
                        intent.putExtra("min", currentCursor.getInt(colMINUTE));
                        intent.putExtra("ring", currentCursor.getString(colRING));
                        intent.putExtra("vib", currentCursor.getInt(colVIB));
                        intent.putExtra("ringpath", currentCursor.getInt(colRINGPATH));

                        startActivity(intent);
                    }

                }
            }
        };
        */
        @Override
        public int getCount() {
            return currentCursor.getCount();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            currentCursor.moveToPosition(position); ///////////////
            ViewHolder viewHolder;

            if (convertView == null) {
                LayoutInflater inflater=(LayoutInflater)my_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(mRowLayout, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.icon = (ImageView)convertView.findViewById(R.id.toggleButton1);
                viewHolder.time = (TextView)convertView.findViewById(R.id.alarm_row_time);
                viewHolder.day = (TextView)convertView.findViewById(R.id.alarm_row_day);
                //
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder)convertView.getTag();
            }
            //
            viewHolder.time.setText(getTimeString(currentCursor.getInt(colHOUR), currentCursor.getInt(colMINUTE)));

            int day = currentCursor.getInt(colDAY);
            String strDay="";
            if((day & 0x01) == 0x01){ strDay = "일"; }
            if((day & 0x02) == 0x02){ strDay += "월"; }
            if((day & 0x04) == 0x04){ strDay += "화"; }
            if((day & 0x08) == 0x08){ strDay += "수"; }
            if((day & 0x10) == 0x10){ strDay += "목"; }
            if((day & 0x20) == 0x20){ strDay += "금"; }
            if((day & 0x40) == 0x40){ strDay += "토"; }
            //
            viewHolder.day.setText(strDay);
            //
            int on = currentCursor.getInt(colONOFF);

            if(on == 1)	viewHolder.icon.setImageResource(R.drawable.clock_on);
            else viewHolder.icon.setImageResource(R.drawable.clock_off);
            //
            return convertView;
        }

        private class ViewHolder {
            ImageView icon;
            TextView time;
            TextView day;
        };


    }
    public String getTimeString(int h, int m) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, h);
        cal.set(Calendar.MINUTE, m);
        SimpleDateFormat dayformat = new SimpleDateFormat("HH:mm");
        dayformat.setCalendar(cal);
        Date date = cal.getTime();
        return dayformat.format(date);
    }
}
