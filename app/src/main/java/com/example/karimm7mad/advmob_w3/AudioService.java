package com.example.karimm7mad.advmob_w3;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.media.MediaPlayer;
import android.util.Log;

public class AudioService extends Service {

    public MediaPlayer dogAudioPlayer;
    public MediaPlayer lionAudioPlayer;
    public MediaPlayer owlAudioPlayer;
    public MediaPlayer eagleAudioPlayer;


    @Override
    public void onCreate() {
        super.onCreate();
        dogAudioPlayer = MediaPlayer.create(this, R.raw.dogaud);
        dogAudioPlayer.setLooping(false);
        lionAudioPlayer = MediaPlayer.create(this, R.raw.lionaud);
        lionAudioPlayer.setLooping(false);
        owlAudioPlayer = MediaPlayer.create(this, R.raw.owlaud);
        owlAudioPlayer.setLooping(false);
        eagleAudioPlayer = MediaPlayer.create(this, R.raw.eagleaud);
        eagleAudioPlayer.setLooping(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dogAudioPlayer.stop();
        lionAudioPlayer.stop();
        owlAudioPlayer.stop();
        eagleAudioPlayer.stop();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("aaaaa", "onStartCommand: xx");
        int x = intent.getExtras().getInt("voiceName");
        if (x == R.raw.lionaud)
            lionAudioPlayer.start();
        else if (x == R.raw.eagleaud)
            eagleAudioPlayer.start();
        else if (x == R.raw.owlaud)
            owlAudioPlayer.start();
        else if (x == R.raw.dogaud)
            dogAudioPlayer.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}