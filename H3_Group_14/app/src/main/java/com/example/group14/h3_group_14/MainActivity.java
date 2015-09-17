package com.example.group14.h3_group_14;

import android.content.ContentValues;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btNote = (Button)findViewById(R.id.btNote);
        Button btShow = (Button)findViewById(R.id.btDatabase);

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
                startActivityForResult(intent3, 1);
            }
        });
    }


    // Receive the data from second activity when this is finished; Code similar from Hand In 1
    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent receive) {
        if (reqCode == 0) {
            TextView txtNote = (TextView) findViewById(R.id.txtNote);
            txtNote.setText(receive.getCharSequenceExtra("note"));
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
    public void btSave(View view){
        ContentValues values = new ContentValues();
        TextView txtNote = (TextView) findViewById(R.id.txtNote);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss:ms");
        String ts = sdf.format(new Date());
        String val = txtNote.getText().toString();

        values.put(SQLDataBase.NOTE, val);
        values.put(SQLDataBase.DATETIME, ts);

        Uri uri = getContentResolver().insert(SQLDataBase.CONTENT_URI, values);
        Toast.makeText(getBaseContext(), "Saved", Toast.LENGTH_LONG).show();
    }

}
