package com.drinkssu.yourvoicealarm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class InputAlarmNameDialog extends DialogFragment {

    TextView eInputName;
    View rootView;
    LayoutInflater inflater;
    public static String data = null;
    MediaRecorder recorder = new MediaRecorder();         // 음성 녹음을 위한 MediaRecord 선언

    private File RECORED_FILE = Environment.getExternalStorageDirectory();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.activity_input_alarm_name_dialog, null);


        File path = null;
        path = new File(RECORED_FILE.getAbsolutePath()+"/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.alarm_user"); // 디렉토리 만들어쥬는부분!!!
        if ( !path.exists() )                   // 디렉토리 없으면 만들어요!!
        {
            // 디렉토리가 존재하지 않으면 디렉토리 생성
            path.mkdirs();
        }


        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(RECORED_FILE.getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.alarm_user/yourAlarm.mp4");
        recorder.setMaxDuration(3000);


        eInputName = (TextView)rootView.findViewById(R.id.eInputAlarmName);
        eInputName.setText(data);
        builder.setView(rootView)

                .setTitle("알람 이름")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "취소", Toast.LENGTH_SHORT).show();


                    }
                })
                .setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            Toast.makeText(getActivity(), "3초간 녹음이 시작됩니다", Toast.LENGTH_SHORT).show();

                            recorder.prepare();
                            recorder.start();///Android/data/com.drinkssu.yourvoicealarm/


                            File filePre = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.alarm_user", "yourAlarm.mp4");
                            File fileNow = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.alarm_user", data + ".mp4");

                            filePre.renameTo(fileNow);
                            Toast.makeText(getActivity(), "등록완료", Toast.LENGTH_SHORT).show();


                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    recorder.stop();
                                    recorder.release();
                                    recorder = null;
                                }


                            }, 3000);


                        } catch (Exception e) {
                        }


                    }
                });


        return builder.create();
    }
}