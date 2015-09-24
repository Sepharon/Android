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
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    // call functions from service usuing data.function_name()
    //Weather_Data data;
    Messenger mService = null;
    boolean is_bound = false;
    SQL_database db;

    AutoCompleteTextView data;
    ArrayAdapter<String> myAdapter;
    ContentValues values;


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
        db = new SQL_database();

        IntentFilter filter = new IntentFilter("miss_temps");
        this.registerReceiver(new MyReceiver(), filter);

        data = (AutoCompleteTextView)findViewById(R.id.data_field);
        values = new ContentValues();

        try {
            String[] ips = getAllEntries();
            for (int i = 0; i < ips.length; i++) {
                Log.i(this.toString(), ips[i]);
            }
            // set our adapter
            myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, ips);
            data.setAdapter(myAdapter);
        }
        catch (NullPointerException es){
            es.printStackTrace();
        }
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
                bundle.putString("city", "Aarhus");
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
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.ListCity){
            listCity();
        }

        return super.onOptionsItemSelected(item);
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
            // Put this empty again ,  don't think is needed tho
            Log.v("Activity One result", result);
            if (result.equals("alive")) {}


        }
    }

    public void listCity(){
        Intent intent = new Intent(this, List_City.class);
        startActivity(intent);
    }

    public String[] getAllEntries(){
        String URL = "content://com.example.group14.provider.Weather/db";
        Uri notesText = Uri.parse(URL);
        Cursor c = managedQuery(notesText, null, null, null, null);
        if (c.getCount() > 0){
            String[] ips = new String[c.getCount()];
            int i = 0;
            while (c.moveToNext()){
                ips[i] = c.getString(c.getColumnIndexOrThrow(SQL_database.CITY));
                i++;
            }
            c.moveToFirst();
            return ips;
        }
        else {
            c.moveToFirst();
            return new String[] {};
        }
    }
}
