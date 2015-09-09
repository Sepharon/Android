package com.example.group14.h1_group_14;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


public class MyService extends Service {

    public static final boolean DEV_MODE = true;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        String phrase;
        Intent in= new Intent(getBaseContext(),SecondActivity.class);
        // Let it continue running until it is stopped.
        if (DEV_MODE) Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        // We get the data
        phrase = retrive_data(intent);
        if (DEV_MODE) Toast.makeText(this, phrase, Toast.LENGTH_LONG).show();
        // Preparing to start activity two
        in.putExtra("phrase",phrase);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Start Acitvity two
        startActivity(in);
        // Stop Service
        stopSelf();
        return START_STICKY;
    }

    private String retrive_data(final Intent intent){
        Log.v("Service","Getting data");
        // Get the string
        String phrase = intent.getStringExtra("phrase");
        // Get time
        int time = Integer.parseInt(intent.getStringExtra("time"));
        Log.v("Service","Going to sleep");
        Log.v("Service","Time = " + time);
        // Make sleep
        // Create for that iterates for time
        for (int i=0;i<time;i++) {
            try {
                Intent broadcast = new Intent();
                // Sleep for one second
                Thread.sleep(1000);
                // Tell second
                broadcast.putExtra("time",i);
                sendBroadcast(broadcast);

            } catch (InterruptedException e) {
                Log.v("Service", "Error when trying to sleep");
                e.printStackTrace();
            }
        }
        // Done
        Log.v("Service", "Done");
        return phrase;
    }
}
