package com.example.group14.h3_group_14;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button btNote;
    Button btShow;
    TextView txtNote;
    String txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btNote = (Button)findViewById(R.id.btNote);
        btShow = (Button)findViewById(R.id.btDatabase);
        txtNote = (TextView) findViewById(R.id.txtNote);

        // When the button is pressed, second activity is started; Code similar from Hand In 1
        btNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, SecondActivity.class);
                startActivityForResult(intent2, 0);
            }
        });
        // When the button is pressed, third activity is started; Code similar from Hand In 1
        btShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity.this, ThirdActivity.class);
                startActivity(intent3);
            }
        });
    }


    // Receive the data from second activity when this is finished; Code similar from Hand In 1
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==0) {
            try {
                txtNote.setText(data.getCharSequenceExtra("note"));
                txt = txtNote.toString();

            }
            catch ( java.lang.RuntimeException e){
                e.printStackTrace();
            }

        }
    }

    //In case of changing the orientation
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
            txtNote.setText(txt);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
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
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
    }

    //When the button Save is clicked, it stores in the database the note with the current time
    public void btSave(View view){
        ContentValues values = new ContentValues();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss:ms");
        String ts = sdf.format(new Date());
        String val = txtNote.getText().toString();

        values.put(SQLDataBase.NOTE, val);
        values.put(SQLDataBase.DATETIME, ts);

        getContentResolver().insert(SQLDataBase.CONTENT_URI, values);
        Toast.makeText(getBaseContext(), "Saved", Toast.LENGTH_LONG).show();
    }

}
