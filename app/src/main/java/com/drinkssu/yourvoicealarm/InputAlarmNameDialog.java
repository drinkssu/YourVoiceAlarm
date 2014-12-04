package com.drinkssu.yourvoicealarm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class InputAlarmNameDialog extends DialogFragment {

    EditText eInputName;
    View rootView;
    LayoutInflater inflater;
    public static String data = null;
    MediaRecorder recorder = new MediaRecorder();         // 음성 녹음을 위한 MediaRecord 선언
    FragmentManager fm;
    File settingFile;
    private File RECORED_FILE = Environment.getExternalStorageDirectory();
    FileOutputStream fos;
    Writer out;


    // 파일에 저장할거 변수들
    static String alarmName="";        //InputAlarmNameDialog 에서 지정 <- 성민이가만든거 파일입력으로 받아올것
    static int checkSelf=1;           // 무조건 1로 지정
    static int checkSex=0;                // 이거 처음에 입력한값으로 저장
    static int category;                // AlarmSetting 에서 지정


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        settingFile = new File(RECORED_FILE.getAbsolutePath()+"/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.tmp/tmpSetting.txt");
        try {
            // java.io.FileOutputStream.FileOutputStream(File file, boolean append)
            fos = new FileOutputStream(settingFile,true); //true 계속 추가 여부
            out = new OutputStreamWriter(fos,"UTF-8");


        } catch (FileNotFoundException e) {
            Toast.makeText(getActivity(), "파일을 찾을 수 없습니다", Toast.LENGTH_SHORT).show();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.activity_input_alarm_name_dialog, null);

        fm = getFragmentManager();


        File path = null;
        File path2 = null;
        File path3 = null;
        path = new File(RECORED_FILE.getAbsolutePath()+"/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.alarm_user"); // 디렉토리 만들어쥬는부분!!!
        if ( !path.exists() )                   // 디렉토리 없으면 만들어요!!
        {
            // 디렉토리가 존재하지 않으면 디렉토리 생성
            path.mkdirs();
        }
        path2 = new File(RECORED_FILE.getAbsolutePath()+"/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.tmp"); // 디렉토리 만들어쥬는부분!!!
        if ( !path2.exists() )                   // 디렉토리 없으면 만들어요!!
        {
            // 디렉토리가 존재하지 않으면 디렉토리 생성
            path2.mkdirs();
        }
        path3 = new File(RECORED_FILE.getAbsolutePath()+"/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.alarm_txt"); // 디렉토리 만들어쥬는부분!!!
        if ( !path3.exists() )                   // 디렉토리 없으면 만들어요!!
        {
            // 디렉토리가 존재하지 않으면 디렉토리 생성
            path3.mkdirs();
        }


        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(RECORED_FILE.getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.tmp/yourAlarm.mp4");
        recorder.setMaxDuration(4020);


        eInputName = (EditText)rootView.findViewById(R.id.eInputAlarmName);
        eInputName.setText(data);
        builder.setView(rootView)

                .setTitle("알람 이름")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "취소", Toast.LENGTH_SHORT).show();


                    }
                })
                .setPositiveButton("녹음", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            Toast.makeText(getActivity(), "4초간 녹음이 시작됩니다", Toast.LENGTH_SHORT).show();


                            recorder.prepare();
                            recorder.start();///Android/data/com.drinkssu.yourvoicealarm/

Toast.makeText(getActivity(), data+"  "+String.valueOf(checkSelf)+"  "+String.valueOf(checkSex)+"  "+category,Toast.LENGTH_LONG).show();
                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    recorder.stop();
                                    recorder.release();
                                    recorder = null;

                                    File filePre = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.tmp", "yourAlarm.mp4");
                                    File fileNow = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.alarm_user", data + ".mp4");

                                    filePre.renameTo(fileNow);
                                    try {


                                        out.write(data+"\n");
                                        out.write(checkSelf+"\n");
                                        out.write(checkSex+"\n");
                                        out.write(category+"\n");
                                        out.close();

                                        File settingFilePre = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.tmp", "tmpSetting.txt");
                                        File settingFileNow = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.alarm_txt", data + ".txt");

                                        settingFilePre.renameTo(settingFileNow);



                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                    AlarmListDialog kkkk = new AlarmListDialog();
                                    kkkk.check = 1;
                                    kkkk.fileName = data;
                                    kkkk.show(fm, "showshowshow");


                                }


                            }, 4020);



                        } catch (Exception e) {
                        }


                    }
                });


        return builder.create();
    }
}