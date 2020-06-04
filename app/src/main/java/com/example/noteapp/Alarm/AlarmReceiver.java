package com.example.noteapp.Alarm;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.noteapp.DetailActivity;
import com.example.noteapp.R;

public class AlarmReceiver extends BroadcastReceiver {
    PendingIntent pendingIntent;
    public static  final int NOTIFICATION_ID = 200;

    @Override
    public void onReceive(Context context, Intent intent) {
        String nhan = intent.getExtras().getString("stop");
        Log.i("Crdo", "Alarm " +nhan);

        Intent intent1 = new Intent(context, Music.class);
        intent1.putExtra("stop", nhan);
        context.startService(intent1);

        Intent intent2 = new Intent(context, Music.class);
        intent2.putExtra("stop", "off");
        pendingIntent = PendingIntent.getService(context,
                0, intent2, pendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifor8")
                .setSmallIcon(R.drawable.ic_add_alarm_black_24dp)
                .setContentTitle("Alarm")
                .setContentText("Alarm note")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOngoing(true)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .addAction(R.mipmap.ic_launcher, "Turn off", pendingIntent);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(NOTIFICATION_ID, builder.build());
    }
}
