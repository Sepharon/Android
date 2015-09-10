package com.example.group14.h1_group_14;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;

import android.os.Bundle;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    Button StartService;
    Button Clean;
    EditText phrase;
    EditText time;
    TextView countdown;

    ProgressBar mProgress;
    private int mProgressStatus = 0;
    private int max_time;
    private Handler mHandler = new Handler();
    float finalValue;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter("miss_temps");
        this.registerReceiver(new MyReceiver(), filter);
/*

        mProgress = (ProgressBar) findViewById(R.id.progress_bar);
        new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus < 100) {
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            Log.v("Progress", ""+mProgressStatus);
                            mProgress.setProgress(mProgressStatus);
                        }
                    });
                }
            }
        }).start();
*/
        StartService = (Button) findViewById(R.id.button);
        Clean = (Button) findViewById(R.id.button2);
        phrase = (EditText) findViewById(R.id.textView1);
        time = (EditText) findViewById(R.id.textView2);

        StartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String delay = time.getText().toString();
                max_time = Integer.parseInt(delay);
                String phr = phrase.getText().toString();
                if (delay.matches("") || phr.matches("")) {
                    Toast.makeText(MainActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(delay) < 5 || Integer.parseInt(delay) > 60) {
                    Toast.makeText(MainActivity.this, "Delay must be minimum of 5 sec. to maximum of 60 sec.", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(delay) >= 5 && Integer.parseInt(delay) <= 60) {
                    startService(v);
                }
            }
        });

        Clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phrase.setText("");
                time.setText("");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

    // Method to start the service
    public void startService(View view) {
        // TEST
        String delay = time.getText().toString();
        String phr = phrase.getText().toString();
        Log.v("Activity One", "Starting Service");
        Intent intent = new Intent(getBaseContext(), MyService.class);
        intent.putExtra("phrase", phr);
        intent.putExtra("time", delay);
        startService(intent);
    }

    public void openAbout() {
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
    }

    public void onBackPressed() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle(getApplicationContext().getResources().getString(R.string.dialogQuestion));
        alertDialog.setNegativeButton(getApplicationContext().getResources().getString(R.string.dialogNegative), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alertDialog.setPositiveButton(getApplicationContext().getResources().getString(R.string.dialogPositive), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        alertDialog.show();
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mProgressStatus = intent.getIntExtra("temps", max_time);
            countdown = (TextView) findViewById(R.id.count_down);
            Log.v("Activitat1", "" + mProgressStatus);
            if (mProgressStatus != 0) countdown.setText("" + mProgressStatus);

            else countdown.setText(" ");
/*
            //You do here like usual using intent
            time = (EditText) findViewById(R.id.textView2);
            String delay = time.getText().toString();
            finalValue=(float)Integer.parseInt(delay);

            Log.v("Activitat1", "" + mProgressStatus);
            mProgressStatus=(int)((float)((mProgressStatus/finalValue)*100.0));
*/

        }
    }
}