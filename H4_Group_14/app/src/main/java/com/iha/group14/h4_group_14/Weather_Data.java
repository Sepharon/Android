/*
REFERENCE http://developer.android.com/guide/components/bound-services.html
 */

package com.iha.group14.h4_group_14;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class Weather_Data extends Service {

    static final int MSG_GET_DATA = 1;
    static final String url = "http://api.openweathermap.org";
    static final String request = "/data/2.5/weather?";

    private final IBinder mBinder = new LocalBinder();
    private Messenger msg = new Messenger(new IncomingHandler());
    String result="IT DOES NOT EXIST DON'T LIE TO ME";
    JSONObject weather_data;
    public class LocalBinder extends Binder {
        Weather_Data getService() {
            // Return this instance of LocalService so clients can call public methods
            return Weather_Data.this;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.v("Service:", "Binding");
        return msg.getBinder();
    }

    @Override
    public void onCreate (){
        super.onCreate();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        // f(total_countdown_time,tick_time)
        // 5 minutes, ticks every second
        if (!is_network_aviable()) {
            Toast.makeText(getBaseContext(), "No network aviable", Toast.LENGTH_LONG).show();
        }
        else {
            Log.v("Service: ", "Started countdown");
            new CountDownTimer(20000, 1000) {
                public void onTick(long millisUntilFinished) {}
                public void onFinish() {
                    //Write function to be called
                    get_weather_data();
                    start();
                }
            }.start();
        }
    }

    public void get_weather_data(){
        // TODO : DB WITH ID ?

        int responseCode;

        String full_url = url+request+"q="+result+",dk"+"&APPID=3e16d61afeec2d2b55c477eaf523cb20";
        Log.v("Service:", "full url = " + full_url);

        try{
            Log.v("Service:" , "Creating connection");
            URL link_url = new URL(full_url);
            URLConnection connection = link_url.openConnection();
            Log.v("Service:" , "Trying connection");
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK){
                httpConnection.connect();
                Log.v("Service:", "Connection ok, getting input");
                InputStream stream = httpConnection.getInputStream();
                Log.v("Service:" , "Reading stream");
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                Log.v("Service: ", reader.readLine());
                weather_data = new JSONObject(reader.readLine());
            }
            else {
                Log.v("Service: ", "city does not exist");
                Toast.makeText(getBaseContext(),"The city does not exist",Toast.LENGTH_LONG).show();
            }
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public boolean is_network_aviable(){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            // Here write code for messages from activity (eg: city to get data from,
            // units desired celius or farenheid)
            switch (msg.what) {
                case MSG_GET_DATA:

                    Log.v("Service:", "Got data");
                    result = msg.getData().getString("city");
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }

        }

    }

}
