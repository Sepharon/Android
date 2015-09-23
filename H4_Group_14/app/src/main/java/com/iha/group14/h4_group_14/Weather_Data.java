/*
REFERENCE http://developer.android.com/guide/components/bound-services.html
 */

package com.iha.group14.h4_group_14;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;

public class Weather_Data extends Service {

    static final int MSG_GET_DATA = 1;
    static final String url = "http://api.openweathermap.org";
    static final String request = "/data/2.5/weather?";

    private final IBinder mBinder = new LocalBinder();
    private Messenger msg = new Messenger(new IncomingHandler());
    String result;

    public class LocalBinder extends Binder {
        Weather_Data getService() {
            // Return this instance of LocalService so clients can call public methods
            return Weather_Data.this;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.v("Service:" , "Binding");
        return msg.getBinder();
    }

    @Override
    public void onCreate (){
        super.onCreate();
        // f(total_countdown_time,tick_time)
        // 5 minutes, ticks every second
        Log.v("Service: ", "Started countdown");
        new CountDownTimer(300000,1000){
            public void onTick (long millisUntilFinished){

            }
            public void onFinish(){
                //Write function to be called
                get_weather_data();
                start();
            }
        }.start();
    }

    public void get_weather_data(){
        // TODO : DB WITH ID ?

        String full_url = url+request+"q="+result+",dk";
        Log.v("Service:" , "full url = "+full_url);

    }

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            // Here write code for messages from activity (eg: city to get data from,
            // units desired celius or farenheid)
            switch (msg.what) {
                case MSG_GET_DATA:
                    Toast.makeText(getApplicationContext(), "hello!", Toast.LENGTH_SHORT).show();
                    Log.v("Service:", "Got data");
                    result = "Aarhus";
                    break;
                default:
                    super.handleMessage(msg);
            }

        }

    }

}
