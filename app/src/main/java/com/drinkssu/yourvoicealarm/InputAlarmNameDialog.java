package com.drinkssu.yourvoicealarm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class InputAlarmNameDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.activity_input_alarm_name_dialog, null);

        final EditText eInputName = (EditText)rootView.findViewById(R.id.eInputAlarmName);

        builder.setView(rootView)

                .setTitle("알람 이름")

                .setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                File filePre = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/YourVoiceAlarm/alarm_user", "yourAlarm.mp4");
                File fileNow = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/YourVoiceAlarm/alarm_user", eInputName.getText().toString()+".mp4");

                    filePre.renameTo(fileNow);
                        Toast.makeText(getActivity(), "등록완료", Toast.LENGTH_SHORT).show();
                    }
                });


        return builder.create();
    }
}