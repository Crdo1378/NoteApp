package com.example.noteapp.Alarm;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.noteapp.R;

public class Music extends Service {

    public static final String ACTION_PLAY = "action_play";
    public static final String ACTION_PAUSE = "action_pause";
    public static final String ACTION_REWIND = "action_rewind";
    public static final String ACTION_FAST_FORWARD = "action_fforward";
    public static final String ACTION_NEXT = "action_next";
    public static final String ACTION_PREVIOUS = "action_previous";
    public static final String ACTION_STOP = "action_stop";

    private MediaPlayer mediaPlayer;
    private MediaSession mediaSession;
    private MediaSessionManager mediaSessionManager;
    private MediaController mediaController;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String nhan = intent.getExtras().getString("stop");
        Log.i("Crdo", "Music " + nhan);
        if(nhan.equals("on")){
            mediaPlayer = MediaPlayer.create(this, R.raw.test);
            mediaPlayer.start();
            Toast.makeText(this, "Music Playing", Toast.LENGTH_SHORT).show();
            nhan.equals("off");
        }
        if(nhan.equals("off")){
            mediaPlayer.stop();
            nhan.equals("stop");
        }
        return START_NOT_STICKY;
    }

    private void handleIntent(Intent intent){
        if(intent == null || intent.getAction() == null){
            return;
        }
    }
}
