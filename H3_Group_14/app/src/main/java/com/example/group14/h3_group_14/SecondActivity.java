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
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    private EditText edNote;
    Button btOk;
    Button btCancel;
    private String note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        edNote = (EditText) findViewById(R.id.edText);

        // When cancel button is pressed, second activity is finished without sending anything; Code similar from Hand In 1
        btCancel = (Button) findViewById(R.id.btCancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent0 = new Intent();
                SecondActivity.this.setResult(0, intent0);
                SecondActivity.this.finish();

            }
        });
        btOk = (Button) findViewById(R.id.btOk);

        //When OK button is pressed, it sends the value of the note to the MainActivity
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                if  (edNote.getText()==null) note = "" ;
                else note = edNote.getText().toString();
                intent1.putExtra("note", note);
                setResult(0, intent1);
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
                    btOk.setEnabled(false);
                } else {
                    btOk.setEnabled(true);
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
        Intent intent = new Intent(SecondActivity.this, AboutActivity.class);
        startActivity(intent);
    }


}
