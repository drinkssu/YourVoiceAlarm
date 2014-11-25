package com.drinkssu.yourvoicealarm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JinGyu on 2014-11-17.
 */
public class AlarmListDialog extends DialogFragment {
    ArrayList<String> arrayList = new ArrayList<String>();
    ListView alarmList;
    ArrayAdapter<String> arrayAdapter;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

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

        builder.setView(rootView)
                .setIcon(R.drawable.ic_launcher)
                .setTitle("알람 목록")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

    private File[] getFileNames() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.alarm_user";
        Log.d("Files", "Path: " + path);
        File f = new File(path);
        File file[] = f.listFiles();
        Log.d("Files", "Size: "+ file.length);
        return file;
    }
}