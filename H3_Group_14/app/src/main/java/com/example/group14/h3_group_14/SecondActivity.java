package com.example.group14.h3_group_14;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final EditText edNote = (EditText) findViewById(R.id.edText);

        Button btCancel = (Button)findViewById(R.id.btCancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent();
                SecondActivity.this.setResult(0, intent1);
                SecondActivity.this.finish();

            }
        });

        Button btOk = (Button)findViewById(R.id.btOk);

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String note = edNote.getText().toString();
                Intent intent2 = new Intent();
                intent2.putExtra("text", note);
                SecondActivity.this.setResult(0, intent2);
                SecondActivity.this.finish();


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
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
