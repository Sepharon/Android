/*
REFERENCES: http://www.techotopia.com/index.php/Android_Local_Bound_Services_%E2%80%93_A_Worked_Example
http://developer.android.com/guide/components/bound-services.html
 */

package com.iha.group14.h4_group_14;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // call functions from service usuing data.function_name()
    //Weather_Data data;
    Messenger mService = null;
    boolean is_bound = false;

    EditText data;

    CheckBox fahrenheit;
    CheckBox celsius;
    String temperature;

    Spinner spinner;

    TextView Temperature;
    TextView WindSpeed;
    TextView Pressure;
    TextView Humidity;
    TextView Temp_min;
    TextView Temp_max;

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (is_bound) {
            unbindService(mConnection);
            is_bound = false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, Weather_Data.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        IntentFilter filter = new IntentFilter("miss_temps");
        this.registerReceiver(new MyReceiver(), filter);

        data = (EditText)findViewById(R.id.data_field);
        Temperature = (TextView)findViewById(R.id.textView3);
        WindSpeed = (TextView)findViewById(R.id.textView4);
        Pressure = (TextView)findViewById(R.id.textView6);
        Humidity = (TextView)findViewById(R.id.textView8);
        Temp_min = (TextView)findViewById(R.id.textView10);
        Temp_max = (TextView)findViewById(R.id.textView12);

        spinner = (Spinner)findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.country_codes, android.R.layout.simple_spinner_item);
         // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        fahrenheit = (CheckBox)findViewById(R.id.checkBox);
        celsius = (CheckBox)findViewById(R.id.checkBox2);
        celsius.setChecked(true);
        fahrenheit.setChecked(false);


        celsius.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (celsius.isChecked()) {
                    fahrenheit.setChecked(false);
                }
            }
        });
        fahrenheit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (fahrenheit.isChecked()){
                    celsius.setChecked(false);
                }
            }
        });

        // Sending data to Service
        Log.v("Activity:", "Sending message");
        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_bound) return;
                // Create and send a message to the service, using a supported 'what' value
                Log.v("Activity:", "Getting ready");
                Message msg = Message.obtain(null, Weather_Data.MSG_GET_DATA);
                Bundle bundle = new Bundle();
                if (celsius.isChecked()){
                    temperature="metric";
                }
                else if (fahrenheit.isChecked()){
                    temperature="imperial";
                }
                bundle.putString("unit", temperature);
                bundle.putString("city", data.getText().toString());
                bundle.putString("country_code", spinner.getSelectedItem().toString());
                msg.setData(bundle);
                try {
                    mService.send(msg);
                    Log.v("Activity:", "Message sent");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                // Done sending data
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_about) {
            openAbout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void openAbout() {
        Intent intent = new Intent(MainActivity.this, About.class);
        startActivity(intent);
    }

    // Binding function
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            Log.v("Main Activity:", "Binding service");
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            //Weather_Data.LocalBinder binder = (Weather_Data.LocalBinder) service;
            //data = binder.getService();
            mService = new Messenger(service);
            is_bound = true;
        }


        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mService = null;
            is_bound = false;
        }
    };

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String result = intent.getStringExtra("result");
            Temperature.setText(result);
            // Put this empty again ,  don't think is needed tho
            Log.v("Activity One result", result);
        }
    }
}
