package com.drinkssu.yourvoicealarm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JinGyu on 2014-11-17.
 */
public class AlarmListDialog extends DialogFragment{
    ArrayList<String> arrayList = new ArrayList<String>();
    ListView alarmList;
    ArrayAdapter<String> arrayAdapter;
    static int check=0;
    static String fileName="";
    static int state = 0;
  MediaPlayer mp= new MediaPlayer();




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if(check == 1)
        {
            Toast.makeText(getActivity(), "\""+fileName +"\" 등록완료",Toast.LENGTH_SHORT).show();
            check=0;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.alarm_list_dialog, null);
        alarmList = (ListView)rootView.findViewById(R.id.listAlarmList);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayList);

        //Todo : 파일의 이름들을 반복문을 이용해서 ArrayList에 순차적으로 넣기.
        //ex : arrayList.add("첫번째 파일이름")
        //ex : arrayList.add("두번째 파일이름")

        File files[] = getFileNames();

        for (int i=0; i<files.length;i++){
            arrayList.add(files[i].getName().toString().substring(0,files[i].getName().toString().indexOf(".mp4")));
        }
        alarmList.setAdapter(arrayAdapter);
        alarmList.setOnItemClickListener(mItemClickListener);
        alarmList.setOnItemLongClickListener(mItemLongClickListener);


        builder.setView(rootView)
                .setIcon(R.drawable.btn_3)
                .setTitle("알람 목록")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });


        return builder.create();
    }

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent,final View view,final int position,final long id) {
            String fileName = (String)parent.getAdapter().getItem(position);

            //Todo 여기다가 재생하는거 코드

                try
                {

                    if(mp.isPlaying())
                    {

                        mp.stop();
                        mp.reset();
                    }

                    mp.setDataSource(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.alarm_user/"+ fileName + ".mp4"); // mp3파일 경로
                    mp.prepare();      // 준비
                    mp.setLooping(false);    // 반복재생 false
                    mp.start();      // 시작
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.stop();
                            mp.reset();
                        }
                    });


                }
                catch(IOException e)
                {

                }

        }
    };

    private AdapterView.OnItemLongClickListener mItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent,final View view,final int position,final long id) {
           final String fileName = (String)parent.getAdapter().getItem(position);
            //Todo 삭제하는 코드

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

            alertDialogBuilder.setTitle("경고");

            alertDialogBuilder.setMessage("\" "+fileName+" \" 삭제하시겠습니까?");

            // set positive button: Yes message

            alertDialogBuilder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int id) {
                        state = 1;
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.alarm_user",fileName+".mp4");
                    File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.alarm_txt",fileName+".txt");
                   if( file.delete())
                   {
                        file2.delete();
                       Toast.makeText(getActivity(),"삭제완료",Toast.LENGTH_SHORT).show();
                       dismiss();

                   }
                    else
                   {
                       Toast.makeText(getActivity(),"삭제실패",Toast.LENGTH_SHORT).show();
                   }

                }

            });

            // set negative button: No message

            alertDialogBuilder.setNegativeButton("취소",new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog,int id) {
                    state = 1;
                    // cancel the alert box and put a Toast to the user

                    dialog.cancel();

                }

            });

            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();

            return true;
        }
    };

    private File[] getFileNames() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.alarm_user";
        Log.d("Files", "Path: " + path);
        File f = new File(path);
        File file[] = f.listFiles();
        Log.d("Files", "Size: "+ file.length);
        return file;
    }


}