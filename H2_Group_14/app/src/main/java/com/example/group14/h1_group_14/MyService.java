package com.example.group14.h1_group_14;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        String phrase;
        Intent in= new Intent(getBaseContext(),SecondActivity.class);
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        // We get the data
        phrase = retrive_data(intent);
        Toast.makeText(this, phrase, Toast.LENGTH_LONG).show();

        // TODO : FICAR QUE LES DADES VAGIN A UN INTENT
        // Preparing to start activity two
        in.putExtra("phrase",phrase);
        //Start Acitvity two
        startActivity(in);
        // Stop Service dfdfdfd
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

        // Make sleep
        try {
            Thread.sleep(time*1000);
        } catch (InterruptedException e) {
            Log.v("Service","Error when trying to sleep");
            e.printStackTrace();
        }
        // Done
        Log.v("Service", "Done");
        return phrase;
    }
}
