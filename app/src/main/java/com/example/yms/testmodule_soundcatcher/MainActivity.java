package com.example.yms.testmodule_soundcatcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText inputIp;

    private Button connBtn, disconnBtn, playBtn, pauseBtn, recordBtn, saveBtn;



    private UdpNetwork udpNetwork;

    private AudioHandler audioHandler;

    private AudioRecord audioRecord;

    private Main main;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        inputIp = (EditText)findViewById(R.id.editIP);

        connBtn = (Button)findViewById(R.id.connBtn);

        disconnBtn = (Button)findViewById(R.id.disconnBtn);

        playBtn = (Button) findViewById(R.id.playBtn);

        pauseBtn = (Button) findViewById(R.id.pauseBtn);

        recordBtn = (Button) findViewById(R.id.recordBtn);

        saveBtn = (Button) findViewById(R.id.saveBtn);







    }



    public void onClick(View view){

        switch (view.getId()){

            case R.id.connBtn :

                Log.d("MYLOG", inputIp.getText().toString());

                udpNetwork = new UdpNetwork(inputIp.getText().toString());

                audioHandler = new AudioHandler();

                audioRecord = new AudioRecord();



                main = new Main(udpNetwork, audioHandler, audioRecord);

                main.start();



                Log.d("MYLOG", "main Thread create");

                connBtn.setClickable(false);

                disconnBtn.setClickable(true);

                playBtn.setClickable(true);

                break;



            case R.id.disconnBtn:

                main.setAliveFlag(false);



                Log.d("MYLOG", "main Thread Terminated");

                disconnBtn.setClickable(false);

                playBtn.setClickable(false);

                pauseBtn.setClickable(false);

                recordBtn.setClickable(false);

                saveBtn.setClickable(false);

                connBtn.setClickable(true);

                break;



            case R.id.playBtn :

                audioHandler.play();

                playBtn.setClickable(false);

                pauseBtn.setClickable(true);

                recordBtn.setClickable(true);

                break;



            case R.id.pauseBtn:

                audioHandler.pause();

                playBtn.setClickable(true);

                pauseBtn.setClickable(false);

                recordBtn.setClickable(false);

                break;



            case R.id.recordBtn :

                audioRecord.recordStart();

                main.setRecordFlag(true);

                recordBtn.setClickable(false);

                pauseBtn.setClickable(false);

                disconnBtn.setClickable(false);

                break;



            case R.id.saveBtn:

                Toast.makeText(MainActivity.this, "파일을 저장하고 있습니다.", Toast.LENGTH_LONG).show();

                main.setRecordFlag(false);

                audioRecord.recordStop();

                Toast.makeText(MainActivity.this, "파일을 저장 완료 했습니다.", Toast.LENGTH_LONG).show();

                recordBtn.setClickable(true);

                pauseBtn.setClickable(true);

                disconnBtn.setClickable(true);

                break;

        }

    }

}