/*
    Sergi Carol Bosch, Silvia Roses, Roger Prat
    Group 14, Hand_in 2
 */

package com.example.group14.h1_group_14;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


public class MyService extends Service {
    // Change this to true if you want to see debug toast
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
        // Wait time
        wait_time(time,phrase);

        if (DEV_MODE) Toast.makeText(this, "Service Stopping", Toast.LENGTH_LONG).show();

        // Stop Service
        stopSelf();
        return START_STICKY;
    }

    private void wait_time(final int time,final String phrase){
        Log.v("Service","Starting Thread");

        // Creating a new thread for sleeping
        if (DEV_MODE) Toast.makeText(this, "Creating thread", Toast.LENGTH_LONG).show();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // Create Broadcast and set action
                Intent broadcast = new Intent();
                broadcast.setAction("miss_temps");
                sendBroadcast(broadcast);

                // Start iteration
                /*
                The reason why we start at time-1 , where time is the input from the user, is because
                on the main activity the countdown has to start with a given number, thus we use
                the input time from the user, if we started with time in here we would have the following
                5 5 4 3 2 1
                Thus having 6 numbers not 5.
                 */

                Log.v("Service","Going to sleep");
                for (int i=(time-1);i>=0;i--) {
                    try {
                        // Sleep for one second
                        Thread.sleep(1000);
                        // Tell second
                        Log.v("Service", "Time = " + i);
                        broadcast.putExtra("temps", i);
                        sendBroadcast(broadcast);
                    }
                    catch (InterruptedException e) {
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
        // Start thread
        t.start();

        // Done
        if(DEV_MODE) Toast.makeText(this, "Thread started", Toast.LENGTH_LONG).show();
        Log.v("Service", "Done");
    }
}
