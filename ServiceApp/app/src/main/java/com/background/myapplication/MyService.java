package com.background.myapplication;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import static com.background.myapplication.ApplicationClass.CHANNEL_ID;

public class MyService extends Service {


    @Override
    public void onCreate() { //first create //only once in lifecycle
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {  //starting service //everytime startservice is called

        //runs on the ui thread , if you want to avoid this use intent service

        //persistence notification
        String input = intent.getStringExtra("inputExtra");
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,   //on notification click
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Example Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);

        //do heavy work on a background thread

        //stopSelf(); stops the service
        return START_NOT_STICKY;

        //foreground service is not likely to be killed as background service.

        //what happens when the service is killed

        //not sticky : service will not start again
        //sticky : service will restart the service asap but intent will be null
        //redeliver intent : started again with the previous intent
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
