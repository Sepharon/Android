package com.example.group14.h3_group_14;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final EditText edNote = (EditText) findViewById(R.id.edText);

        // When cancel button is pressed, second activity is finished without sending anything; Code similar from Hand In 1
        Button btCancel = (Button)findViewById(R.id.btCancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent0 = new Intent();
                SecondActivity.this.setResult(0, intent0);
                SecondActivity.this.finish();

            }
        });


        final Button btOk = (Button)findViewById(R.id.btOk);

        // When text from the Edit Text changes and it isn't null, Button OK is enabled.

        edNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(edNote.getText().toString().equals("")) {
                    btOk.setEnabled(false);
                }
                else {
                    btOk.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        // When OK button is pressed, second activity is finished sending the text from the Edit Text to main activity; Code similar from Hand In 1
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String note;
                if  (edNote.getText()==null) note = "" ;
                else note = edNote.getText().toString();
                Intent intent1 = new Intent();
                intent1.putExtra("note", note);
                SecondActivity.this.setResult(0, intent1);
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
