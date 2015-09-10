package com.example.group14.h1_group_14;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


public class MyService extends Service {

    public static final boolean DEV_MODE = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        // Retrieve phrase and time
        String phrase = intent.getStringExtra("phrase");
        int time = Integer.parseInt(intent.getStringExtra("time"));

        if (DEV_MODE) Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        // Make time wait
        wait_time(time,phrase);

        if (DEV_MODE) Toast.makeText(this, "Service Stopping", Toast.LENGTH_LONG).show();

        // Stop Service
        stopSelf();
        return START_STICKY;
    }

    private void wait_time(final int time,final String phrase){
        Log.v("Service","Getting data");
        // Make sleep
        // Create for that iterates for time times
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // Create Broadcast
                Intent broadcast = new Intent();
                broadcast.setAction("miss_temps");
                sendBroadcast(broadcast);
                Log.v("Service","Going to sleep");
                for (int i=1;i<=time;i++) {
                    try {
                        // Sleep for one second
                        Thread.sleep(1000);
                        // Tell second
                        Log.v("Service", "Time = " + i);
                        broadcast.putExtra("temps", i);
                        sendBroadcast(broadcast);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // Once time has passed, start Activity Two
                Intent in= new Intent(getBaseContext(),SecondActivity.class);
                in.putExtra("phrase", phrase);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //Start Acitvity two
                startActivity(in);
            }
        });
        t.start();
        // Done
        Log.v("Service", "Done");
    }
}
