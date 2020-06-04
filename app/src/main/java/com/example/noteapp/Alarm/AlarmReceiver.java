package com.example.noteapp.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String nhan = intent.getExtras().getString("stop");
        Log.i("Crdo", "Alarm " +nhan);

        Intent intent1 = new Intent(context, Music.class);
        intent1.putExtra("stop", nhan);
        context.startService(intent1);
    }
}
