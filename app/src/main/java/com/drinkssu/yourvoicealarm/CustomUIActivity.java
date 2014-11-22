package com.drinkssu.yourvoicealarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by JinGyu on 2014-11-22.
 */
public class CustomUIActivity extends Activity {
    private ProgressBar mProgress;								//���α׷�����
    private ImageView mLeftVolume[], mRightVolume[];		//���� �̹���
    private SpeechRecognizer mRecognizer;					//�����ν� ��ü

    private final int READY = 0, END=1, FINISH=2;			//�ڵ鷯 �޽���. �����ν� �غ�, ��, �� ����
    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what) {
                case READY:
                    mProgress.setVisibility(View.INVISIBLE);				//�غ�Ǿ����� ���α׷����� ����
                    findViewById(R.id.stt_ui).setVisibility(View.VISIBLE);	//����ũ �̹��� ����.
                    break;
                case END:
                    mProgress.setVisibility(View.VISIBLE);						//���� �������� ���α׷����� ���(�����ν� ��)
                    findViewById(R.id.stt_ui).setVisibility(View.INVISIBLE);	//����ũ �̹��� ����
                    sendEmptyMessageDelayed(FINISH, 5000);				//�ν� �ð� 5�ʷ� ����. 5�� ������ �Ű�Ⱦ�.
                    break;
                case FINISH:
                    finish();														//�� ����
                    break;
            }
        }
    };
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_ui);

        mProgress = (ProgressBar)findViewById(R.id.progress);						//���α׷�����

        mLeftVolume = new ImageView[3];				//���� ����
        mRightVolume = new ImageView[3];				//������ ����
        for(int i=0; i<3; i++){
            mLeftVolume[i] = (ImageView)findViewById(R.id.volume_left_1+i);
            mRightVolume[i] = (ImageView)findViewById(R.id.volume_right_1+i);
        }


        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);			//�����ν� intent��
        i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());	//������ ����
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");							//�����ν� ��� ����

        mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);				//�����ν� ��ü
        mRecognizer.setRecognitionListener(listener);										//�����ν� ������ ���
        mRecognizer.startListening(i);															//�����ν� ����
    }

    //������� �Է� �Ҹ� ũ�⿡ ��� ���� �̹��� ����. 3�ܰ�
    private void setVolumeImg(int step){
        for(int i=0; i<3; i++){
            if(i<step)
            {
                mLeftVolume[0].setVisibility(View.VISIBLE);
                mRightVolume[0].setVisibility(View.VISIBLE);
            }
            else{
                mLeftVolume[0].setVisibility(View.INVISIBLE);
                mRightVolume[0].setVisibility(View.INVISIBLE);
            }
        }
    }

    //�����ν� ������
    public RecognitionListener listener = new RecognitionListener() {
        //�Է� �Ҹ� ���� ��
        @Override public void onRmsChanged(float rmsdB) {
            int step = (int)(rmsdB/7);		//�Ҹ� ũ�⿡ ��� step�� ����.
            setVolumeImg(step);			//�� 4�ܰ� �̹��� ����. ����. 1�ܰ�, 2�ܰ�, 3�ܰ�
        }

        //���� �ν� ��� ����
        @Override public void onResults(Bundle results) {
            mHandler.removeMessages(END);			//�ڵ鷯�� ���� �޽��� ����

            Intent i = new Intent();			//��� ��ȯ�� intent
            i.putExtras(results);				//��� ���
            setResult(RESULT_OK, i);		//��� ����

            finish();							//�� ����
        }

        //���� �ν� �غ� �Ǿ�����
        @Override public void onReadyForSpeech(Bundle params) {
            mHandler.sendEmptyMessage(READY);		//�ڵ鷯�� �޽��� ����
        }

        //���� �Է��� ��������
        @Override public void onEndOfSpeech() {
            mHandler.sendEmptyMessage(END);		//�ڵ鷯�� �޽��� ����
        }

        //������ �߻��ϸ�
        @Override public void onError(int error) {
            setResult(error);		//�� activity�� �����ڵ� ���
        }

        @Override public void onBeginningOfSpeech() {}							//�Է��� ���۵Ǹ�
        @Override public void onPartialResults(Bundle partialResults) {}		//�ν� ����� �Ϻΰ� ��ȿ�� ��
        @Override public void onEvent(int eventType, Bundle params) {}		//�̷��� �̺�Ʈ�� �߰��ϱ� ���� �̸� ����Ǿ��� �Լ�
        @Override public void onBufferReceived(byte[] buffer) {}				//�� ���� �Ҹ��� ���� ��
    };

    @Override
    public void finish(){
        if(mRecognizer!= null) mRecognizer.stopListening();						//�����ν� ����
        mHandler.removeMessages(READY);			//�޽��� ����
        mHandler.removeMessages(END);			//�޽��� ����
        mHandler.removeMessages(FINISH);			//�޽��� ����
        super.finish();
    }
}