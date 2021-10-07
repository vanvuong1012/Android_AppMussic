package com.example.android_appmusic_vuong_test;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService extends Service {

    private MyPlayer myPlayer;
    private IBinder binder;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("ServiceDemo", "called onCreate()");

        myPlayer = new MyPlayer(this);
        binder = new MyBinder(); //  MyBinder extends Binder
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("ServiceDemo", "Đã gọi onBind()");
        myPlayer.play();

        // return binder object for ActivityMain
        return binder;

    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("ServiceDemo", "Đã gọi onBind()");
        myPlayer.stop();
        return super.onUnbind(intent);
    }

    public void fastForward(){
        myPlayer.fastForward(6000); // seek to ...
    }

    public void fastStart(){
        myPlayer.fastStart();
    }

    public class MyBinder extends Binder {
        // return MyService  object
        public MyService getService() {

            return MyService.this;
        }
    }

}


// create object to play music
class MyPlayer {

    private MediaPlayer mediaPlayer;

    public MyPlayer(Context context) {
        // add song to ...
        mediaPlayer = MediaPlayer.create(
                context, R.raw.lactroi);
        // set mode loop
        mediaPlayer.setLooping(true);
    }

    public void fastForward(int pos){
        //mediaPlayer.seekTo(pos);
        mediaPlayer.pause();

    }
    public void fastStart(){
        mediaPlayer.start();
    }

    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
