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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.drinkssu.yourvoicealarm.AlarmRank.RankInfoModel;
import com.drinkssu.yourvoicealarm.Util.ReturnResponse;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;

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
    int returnId = 0;;


    // 파일에 저장할거 변수들
    static String alarmName="";        //InputAlarmNameDialog 에서 지정 <- 성민이가만든거 파일입력으로 받아올것
    static int checkSelf=1;           // 무조건 1로 지정
    static int checkSex=0;                // 이거 처음에 입력한값으로 저장
    static int category;                // AlarmSetting 에서 지정


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final AlertDialog.Builder selSaveLoc = new AlertDialog.Builder(getActivity());

        selSaveLoc.setTitle("알람 등록 완료");

        selSaveLoc.setMessage("서버에도 업로드 하시겠습니까?");

        // set positive button: Yes message

        selSaveLoc.setPositiveButton("확인", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                //Todo 서버에 저장하는 코드 추가할 것

                chageFileName(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.alarm_user_v", eInputName.getText().toString() + ".mp4"));


                AlarmListDialog kkkk = new AlarmListDialog();
                kkkk.check = 1;
                kkkk.fileName = eInputName.getText().toString();
                kkkk.show(fm, "showshowshow");



            }
        });

        selSaveLoc.setNegativeButton("취소",new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int id) {

                //Todo 로컬에만 저장


                AlarmListDialog kkkk = new AlarmListDialog();
                kkkk.check = 1;
                kkkk.fileName = eInputName.getText().toString();
                kkkk.show(fm, "showshowshow");
            }

        });

        try {
            FileReader fileReader = new FileReader(RECORED_FILE.getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/user_info.txt");
            int data = 0;
            while(true)
            {
                int dataTmp = data;
                data= fileReader.read();

                if(data<0)
                {
                    checkSex = dataTmp-48;
                    break;
                }
            }
            fileReader.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    recorder.stop();
                                    recorder.release();
                                    recorder = null;

                                    File filePre = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.tmp", "yourAlarm.mp4");
                                    File fileNow = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.alarm_user_v", eInputName.getText().toString() + ".mp4");



                                    filePre.renameTo(fileNow);
                                    try {


                                        out.write(eInputName.getText().toString()+"\n");
                                        out.write(checkSelf+"\n");
                                        out.write(checkSex+"\n");
                                        out.write(category+"\n");
                                        out.close();

                                        File settingFilePre = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.tmp", "tmpSetting.txt");
                                        File settingFileNow = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.alarm_user_t", data + ".txt");

                                        settingFilePre.renameTo(settingFileNow);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }




                                    AlertDialog alertDialog = selSaveLoc.create();


                                    alertDialog.show();


                                }


                            }, 4020);





                        } catch (Exception e) {
                        }


                    }
                });


        return builder.create();
    }
    private void sendFileToServer(File settingFileNow) {

        String url = "http://growingdever.cafe24.com:8080/api/v1.0/upload_voice_file";

        RequestParams requestParams = new RequestParams();
        try {
            requestParams.put("files", settingFileNow);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void chageFileName(final File settingFileNow) {

        String endcodeArgument = null;
        String url = "http://growingdever.cafe24.com:8080/api/v1.0/upload_voice?q=";
        String argument = "{\"order_by\":[{\"field\":\"id\",\"direction\":\"desc\"}]}";

        try {
            endcodeArgument = URLEncoder.encode(argument, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        url += endcodeArgument;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(getActivity(), url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                if (statusCode == 200) {

                    Gson gson = new Gson();
                    ReturnResponse returnResponse = gson.fromJson(response.toString(), ReturnResponse.class);

                    RankInfoModel infoModel = returnResponse.getObjects().get(0);
                    returnId = infoModel.getId();
                    returnId++;



                    File sendfile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.drinkssu.yourvoicealarm/YourVoiceAlarm/.alarm_user_v", returnId + ".mp4");
                    settingFileNow.renameTo(sendfile);


                    sendFileToServer(sendfile);

                    sendfile.renameTo(settingFileNow);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

}