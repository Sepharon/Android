package com.iha.group14.h4_group_14;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Weather extends AppCompatActivity {
    TextView Temperature;
    TextView WindSpeed;
    TextView Pressure;
    TextView Humidity;
    TextView Temp_min;
    TextView Temp_max;
    TextView City;
    TextView Country;


    String temp="";
    String temp_min="";
    String temp_max="";
    String pressure="";
    String humidity="";
    String windspeed="";
    String unit_wind="";
    String unit_temp="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);


        City = (TextView)findViewById(R.id.city);
        Country = (TextView)findViewById(R.id.country);
        Temperature = (TextView)findViewById(R.id.temp2);
        WindSpeed = (TextView)findViewById(R.id.wind);
        Pressure = (TextView)findViewById(R.id.pressure);
        Humidity = (TextView)findViewById(R.id.humidity);
        Temp_min = (TextView)findViewById(R.id.temp_min2);
        Temp_max = (TextView)findViewById(R.id.temp_max2);

        Intent intent = getIntent();
        City.setText(intent.getStringExtra("city"));
        Country.setText(intent.getStringExtra("country"));
        temp = intent.getStringExtra("temp");
        temp_min = intent.getStringExtra("temp_min");
        temp_max = intent.getStringExtra("temp_max");
        pressure = intent.getStringExtra("pressure");
        humidity = intent.getStringExtra("humidity");
        windspeed = intent.getStringExtra("windspeed");
        unit_temp = intent.getStringExtra("unit_temp");
        unit_wind = intent.getStringExtra("unit_wind");
        Temperature.setText(temp+"º"+unit_temp);
        Temp_max.setText(temp_max+"º"+unit_temp);
        Temp_min.setText(temp_min+"º"+unit_temp);
        Pressure.setText(pressure+"hpa");
        Humidity.setText(humidity+"%");
        WindSpeed.setText(windspeed+unit_wind);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
