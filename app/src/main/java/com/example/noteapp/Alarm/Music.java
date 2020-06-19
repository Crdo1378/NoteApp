package com.example.noteapp.Alarm;

import android.app.AlarmManager;
import android.app.NotificationManager;
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

    private MediaPlayer mediaPlayer;

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
            mediaPlayer = MediaPlayer.create(this, R.raw.wavinflag);
            mediaPlayer.start();
            Toast.makeText(this, "Music Playing", Toast.LENGTH_SHORT).show();
            nhan.equals("off");
        }
        if(nhan.equals("off")){
            mediaPlayer.stop();
            nhan.equals("stop");
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.cancel(AlarmReceiver.NOTIFICATION_ID);
        }
        return START_NOT_STICKY;
    }

    private void handleIntent(Intent intent){
        if(intent == null || intent.getAction() == null){
            return;
        }
    }
}
