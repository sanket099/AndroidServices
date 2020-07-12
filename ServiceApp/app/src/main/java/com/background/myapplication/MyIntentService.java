package com.background.myapplication;
import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.background.myapplication.ApplicationClass.CHANNEL_ID;
//makes background threading easier
//handles work sequentially
//auto stops
//doesn't get interrupted
//but is in foreground

//alternative job intent service

public class MyIntentService extends IntentService {
    private static final String TAG = "ExampleIntentService";
    private PowerManager.WakeLock wakeLock;
    public MyIntentService() {
        super("ExampleIntentService");
        setIntentRedelivery(true);
        //what happens after system kills service
        //true : redeliver Intent
        //false : not sticky
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, //wake lock: will keep cpu running on phone lock
                "ExampleApp:Wakelock");
        wakeLock.acquire(600000000); //will keep cpu on //wake lock drains battery if timeout not specified
        Log.d(TAG, "Wakelock acquired");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            //persistence notification

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Example IntentService")
                    .setContentText("Running...")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .build();
            startForeground(1, notification);
        }
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent");
        String input = intent.getStringExtra("inputExtra");
        for (int i = 0; i < 10; i++) {
            Log.d(TAG, input + " - " + i);
            SystemClock.sleep(1000);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        wakeLock.release();
        Log.d(TAG, "Wakelock released");
    }
}