package com.example.group14.h3_group_14;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//This Activity is a copy from SecondActivity, but it is used to modify the SQLite database

public class ModifyActivity extends AppCompatActivity {
    private EditText edNote;
    private String args;
    Button modify;
    Button cancel;
    private ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        edNote = (EditText) findViewById(R.id.edText2);

        // When cancel button is pressed, second activity is finished without sending anything; Code similar from Hand In 1
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        modify = (Button) findViewById(R.id.ok);
        values = new ContentValues();

        //It gets the item that has to be update from an intent and update it with the new note written here
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                args = intent.getStringExtra("item");
                String selection[] = {args};
                Log.v("args", "" + args);
                String note = edNote.getText().toString();
                Log.v("nooote", "" + note);
                values.put(SQLDataBase.NOTE, note);
                getContentResolver().update(SQLDataBase.CONTENT_URI, values, "DateTime=?", selection);
                Intent intent2 = new Intent(ModifyActivity.this, ThirdActivity.class);
                startActivity(intent2);
                finish();
            }

        });

        // When text from the Edit Text changes and it isn't null, Button OK is enabled.

        edNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edNote.getText().toString().equals("")) {
                    modify.setEnabled(false);
                } else {
                    modify.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
        if (id == R.id.action_about) {
            openAbout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openAbout() {
        Intent intent = new Intent(ModifyActivity.this, AboutActivity.class);
        startActivity(intent);
    }
}

