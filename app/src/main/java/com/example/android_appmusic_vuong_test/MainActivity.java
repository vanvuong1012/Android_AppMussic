package com.example.android_appmusic_vuong_test;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {
    private CircleImageView circleImageView;
    private MyService myService;
    private boolean isBound = false;
    private ServiceConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleImageView = findViewById(R.id.img_poster);
        final Button btOn = (Button) findViewById(R.id.btOn);
        final Button btOff = (Button) findViewById(R.id.btOff);
        final Button btFast = (Button) findViewById(R.id.btTua);

        connection = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {

                isBound = false;
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyService.MyBinder binder = (MyService.MyBinder) service;
                myService = binder.getService();
                isBound = true;
            }
        };


        final Intent intent =
                new Intent(MainActivity.this,
                        MyService.class);

        btOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
                bindService(intent, connection, Context.BIND_AUTO_CREATE);

            }
        });

        btOff.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                stopAnimation();
                if(isBound){
                    unbindService(connection);
                    isBound = false;
                }
            }
        });

        btFast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stopAnimation();
                if(isBound){
                    myService.fastForward();
                }else{
                    Toast.makeText(MainActivity.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation();
                if(isBound){
                    // tua bài hát
                    myService.fastStart();
                }else{
                    Toast.makeText(MainActivity.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startAnimation() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                circleImageView.animate().rotationBy(360).withEndAction(this).setDuration(9000)
                        .setInterpolator(new LinearInterpolator()).start();
            }
        };
        circleImageView.animate().rotationBy(360).withEndAction(runnable).setDuration(9000)
                .setInterpolator(new LinearInterpolator()).start();
    }

    private void stopAnimation() {
        circleImageView.animate().cancel();
    }

}

