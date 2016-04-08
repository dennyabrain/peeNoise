package com.example.abrain.peenoise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.CountDownTimer;

import android.media.MediaPlayer;

import android.util.Log;

import android.content.Intent;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MainActivity extends AppCompatActivity {
    Button b1;
    Button b2;
    SeekBar sb;
    TextView tv;
    private PeeTimer peeTimer;

    private MediaPlayer mediaPlayer;
    int progressChanged;

    Animation movement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.pee);
        progressChanged = 0;

        b1 = (Button) findViewById(R.id.peeButton);
        b2 = (Button) findViewById(R.id.timerButton);
        sb = (SeekBar) findViewById(R.id.seek1);
        tv = (TextView) findViewById(R.id.textView);

        movement = AnimationUtils.loadAnimation(this, R.anim.movement);

        //peeTimer = new PeeTimer(5000,1000);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Denny : ", "peeing");
                mediaPlayer.start();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Denny : ", "timer");
                peeTimer.start();
            }
        });

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                /*Toast.makeText(MainActivity.this,"seek bar progress:"+progressChanged,
                        Toast.LENGTH_SHORT).show();*/
                peeTimer = new PeeTimer(progressChanged*100,100);
                peeTimer.start();
                sb.startAnimation(movement);
            }
        });
    }

    public void startService(View view){
        startService(new Intent(getBaseContext(), TickerService.class));
    }

    public void stopService(View view){
        stopService(new Intent(getBaseContext(), TickerService.class));
    }

    public class PeeTimer extends CountDownTimer
    {
        public PeeTimer(long startTime, long endTime){
            super(startTime, endTime);
        }

        @Override
        public void onFinish(){
            //Toast.makeText(MainActivity.this, "timer done", Toast.LENGTH_SHORT).show();
            tv.setText("i hope you are peeing by now");
            mediaPlayer.start();
        }

        @Override
        public void onTick(long millisUntilFinished){
            //Toast.makeText(MainActivity.this, "timer remaining"+millisUntilFinished, Toast.LENGTH_SHORT).show();
            tv.setText(millisUntilFinished/100+" s");
        }
    }

}
