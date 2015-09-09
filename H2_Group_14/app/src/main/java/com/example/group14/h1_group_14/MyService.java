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
        String phrase = intent.getStringExtra("phrase");
        int time = Integer.parseInt(intent.getStringExtra("time"));
        // Let it continue running until it is stopped.
        if (DEV_MODE) Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        // We get the data
        retrive_data(time,phrase);

        if (DEV_MODE) Toast.makeText(this, phrase, Toast.LENGTH_LONG).show();
        // Preparing to start activity two


        // Stop Service
        stopSelf();
        return START_STICKY;
    }

    private void retrive_data(final int time,final String phrase){
        Log.v("Service","Getting data");
        // Get the string
        // Make sleep
        // Create for that iterates for time
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // Get time
                Intent broadcast = new Intent();
                broadcast.setAction("miss_temps");
                sendBroadcast(broadcast);
                Log.v("Service","Going to sleep");
                for (int i=1;i<=time;i++) {
                    try {
                        // Sleep for one second
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // Tell second
                    Log.v("Service", "Time = " + i);


                    broadcast.putExtra("temps", i);
                    sendBroadcast(broadcast);
                }
                Intent in= new Intent(getBaseContext(),SecondActivity.class);
                in.putExtra("phrase", phrase);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //Start Acitvity two
                startActivity(in);
            }
        });
        t.start();
        // Done
        //while (t.isAlive());
        Log.v("Service", "Done");
    }
}
