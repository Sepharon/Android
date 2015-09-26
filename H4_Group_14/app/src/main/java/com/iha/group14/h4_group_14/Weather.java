package com.iha.group14.h4_group_14;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

public class Weather extends AppCompatActivity {
    TextView Temperature;
    TextView WindSpeed;
    TextView Pressure;
    TextView Humidity;
    TextView Temp_min;
    TextView Temp_max;
    TextView City;
    TextView Country;
    TextView Temperature_d;
    TextView WindSpeed_d;
    TextView Pressure_d;
    TextView Humidity_d;
    TextView Temp_min_d;
    TextView Temp_max_d;
    TextView City_d;
    TextView Country_d;
    RelativeLayout layout;


    String temp="";
    String temp_min="";
    String temp_max="";
    String pressure="";
    String humidity="";
    String windspeed="";
    String unit_wind="";
    String unit_temp="";

    Calendar c;

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
        City_d = (TextView)findViewById(R.id.city_Detail);
        Country_d = (TextView)findViewById(R.id.country_Detail);
        Temperature_d = (TextView)findViewById(R.id.temp_Detail);
        WindSpeed_d = (TextView)findViewById(R.id.wind_Detail);
        Pressure_d = (TextView)findViewById(R.id.press_Detail);
        Humidity_d = (TextView)findViewById(R.id.humit_Detail);
        Temp_min_d = (TextView)findViewById(R.id.temp_Detail2);
        Temp_max_d = (TextView)findViewById(R.id.temp_Detail3);
        layout = (RelativeLayout)findViewById(R.id.layout);


        //Get data from MainActivity
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


        change();

    }

    /*@Override
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
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        change();
    }

    //It changes the background image and text color depending on the HOUR.
    public void change(){
        c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        Log.v("hoour", "" + hour);
        if (hour>=6 && hour<19) {
            Log.v("time", "day");
            layout.setBackgroundResource(R.drawable.bg1);
            Temperature.setTextColor(Color.parseColor("#000000"));
            Temp_max.setTextColor(Color.parseColor("#000000"));
            Temp_min.setTextColor(Color.parseColor("#000000"));
            Humidity.setTextColor(Color.parseColor("#000000"));
            Pressure.setTextColor(Color.parseColor("#000000"));
            WindSpeed.setTextColor(Color.parseColor("#000000"));
            City.setTextColor(Color.parseColor("#000000"));
            Country.setTextColor(Color.parseColor("#000000"));
            Temperature_d.setTextColor(Color.parseColor("#000000"));
            Temp_max_d.setTextColor(Color.parseColor("#000000"));
            Temp_min_d.setTextColor(Color.parseColor("#000000"));
            Humidity_d.setTextColor(Color.parseColor("#000000"));
            Pressure_d.setTextColor(Color.parseColor("#000000"));
            WindSpeed_d.setTextColor(Color.parseColor("#000000"));
            City_d.setTextColor(Color.parseColor("#000000"));
            Country_d.setTextColor(Color.parseColor("#000000"));


        }
        else if (hour>=19 && hour<=23 ){
            Log.v("time", "night");
            layout.setBackgroundResource(R.drawable.bg2);
            Temperature.setTextColor(Color.parseColor("#FFFFFF"));
            Temp_max.setTextColor(Color.parseColor("#FFFFFF"));
            Temp_min.setTextColor(Color.parseColor("#FFFFFF"));
            Humidity.setTextColor(Color.parseColor("#FFFFFF"));
            Pressure.setTextColor(Color.parseColor("#FFFFFF"));
            WindSpeed.setTextColor(Color.parseColor("#FFFFFF"));
            City.setTextColor(Color.parseColor("#FFFFFF"));
            Country.setTextColor(Color.parseColor("#FFFFFF"));
            Temperature_d.setTextColor(Color.parseColor("#FFFFFF"));
            Temp_max_d.setTextColor(Color.parseColor("#FFFFFF"));
            Temp_min_d.setTextColor(Color.parseColor("#FFFFFF"));
            Humidity_d.setTextColor(Color.parseColor("#FFFFFF"));
            Pressure_d.setTextColor(Color.parseColor("#FFFFFF"));
            WindSpeed_d.setTextColor(Color.parseColor("#FFFFFF"));
            City_d.setTextColor(Color.parseColor("#FFFFFF"));
            Country_d.setTextColor(Color.parseColor("#FFFFFF"));
        }
        else if (hour>=0 && hour<6 ){
            Log.v("time", "night");
            layout.setBackgroundResource(R.drawable.bg2);
            Temperature.setTextColor(Color.parseColor("#FFFFFF"));
            Temp_max.setTextColor(Color.parseColor("#FFFFFF"));
            Temp_min.setTextColor(Color.parseColor("#FFFFFF"));
            Humidity.setTextColor(Color.parseColor("#FFFFFF"));
            Pressure.setTextColor(Color.parseColor("#FFFFFF"));
            WindSpeed.setTextColor(Color.parseColor("#FFFFFF"));
            City.setTextColor(Color.parseColor("#FFFFFF"));
            Country.setTextColor(Color.parseColor("#FFFFFF"));
            Temperature_d.setTextColor(Color.parseColor("#FFFFFF"));
            Temp_max_d.setTextColor(Color.parseColor("#FFFFFF"));
            Temp_min_d.setTextColor(Color.parseColor("#FFFFFF"));
            Humidity_d.setTextColor(Color.parseColor("#FFFFFF"));
            Pressure_d.setTextColor(Color.parseColor("#FFFFFF"));
            WindSpeed_d.setTextColor(Color.parseColor("#FFFFFF"));
            City_d.setTextColor(Color.parseColor("#FFFFFF"));
            Country_d.setTextColor(Color.parseColor("#FFFFFF"));
        }
    }
}
