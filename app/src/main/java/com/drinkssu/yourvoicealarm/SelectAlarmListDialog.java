package com.drinkssu.yourvoicealarm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by JinGyu on 2014-12-05.
 */
public class SelectAlarmListDialog extends DialogFragment {
    ArrayList<String> arrayList = new ArrayList<String>();
    ListView alarmList;
    ArrayAdapter<String> arrayAdapter;
    static int check=0;
    static String fileName="";
    static int state = 0;
    MediaPlayer mp= new MediaPlayer();
    TextView alarmName;
    static int selPosition =0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if(check == 1)
        {
            Toast.makeText(getActivity(), "\"" + fileName + "\" 등록완료", Toast.LENGTH_SHORT).show();
            check=0;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.select_alarm_list_dialog, null);

        alarmList = (ListView)rootView.findViewById(R.id.listAlarmList);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_single_choice, arrayList);

        //Todo : 파일의 이름들을 반복문을 이용해서 ArrayList에 순차적으로 넣기.
        //ex : arrayList.add("첫번째 파일이름")
        //ex : arrayList.add("두번째 파일이름")
        File basefiles[] = getFileNames("/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.alarm_base_v");
        File userfiles[] = getFileNames("/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.alarm_user_v");

        arrayList.add("너의 목소리가 들려 (서버랜덤알람)");
        for (int i=0; i<basefiles.length;i++){
            arrayList.add(basefiles[i].getName().toString().substring(0,basefiles[i].getName().toString().indexOf(".mp4")));
        }
        for (int i=0; i<userfiles.length;i++){
            arrayList.add(userfiles[i].getName().toString().substring(0,userfiles[i].getName().toString().indexOf(".mp4")));
        }
        alarmList.setAdapter(arrayAdapter);
        alarmList.setOnItemClickListener(mItemClickListener);
        alarmList.setOnItemLongClickListener(mItemLongClickListener);
        alarmList.setItemChecked(selPosition, true);

        builder.setView(rootView)
                .setIcon(R.drawable.btn_3)
                .setTitle("알람 목록")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        selPosition = alarmList.getCheckedItemPosition();
                        String name =  alarmList.getItemAtPosition(alarmList.getCheckedItemPosition()).toString();
                        alarmName = (TextView)getActivity().findViewById(R.id.selectRingtone);
                        alarmName.setText(name);
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
                if(fileName.equals("너의 목소리가 들려"))
                    return;
                mp.setDataSource(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.alarm_user_v/"+ fileName + ".mp4"); // mp3파일 경로
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
            if(fileName.equals("너의 목소리가 들려")) {
                Toast.makeText(getActivity(), "서버통신 알람입니다. (삭제불가)", Toast.LENGTH_SHORT).show();
                return true;
            }
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

            alertDialogBuilder.setTitle("경고");

            alertDialogBuilder.setMessage("\" " + fileName + " \" 삭제하시겠습니까?");

            // set positive button: Yes message

            alertDialogBuilder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int id) {
                    state = 1;
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.alarm_user_v", fileName + ".mp4");
                    File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.alarm_user_t", fileName + ".txt");
                    if (file.delete()) {
                        file2.delete();
                        Toast.makeText(getActivity(), "삭제완료", Toast.LENGTH_SHORT).show();
                        dismiss();

                    } else {
                        Toast.makeText(getActivity(), "삭제실패", Toast.LENGTH_SHORT).show();
                    }

                }

            });


            // set negative button: No message

            alertDialogBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int id) {
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

    private File[] getFileNames(String dataPath) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+dataPath;
        Log.d("Files", "Path: " + path);
        File f = new File(path);
        File file[] = f.listFiles();
        Log.d("Files", "Size: "+ file.length);
        return file;
    }


}