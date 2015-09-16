package com.example.group14.h3_group_14;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        // When OK button is pressed, if there isn't any text a toast appears, if there is, second activity is finished sending the text from the Edit Text to main activity; Code similar from Hand In 1
        Button btOk = (Button)findViewById(R.id.btOk);

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String note = edNote.getText().toString();
                if(note.equals("")){

                    Toast toast = Toast.makeText(SecondActivity.this, R.string.toast  , Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    Intent intent1 = new Intent();
                    intent1.putExtra("note", note);
                    SecondActivity.this.setResult(0, intent1);
                    SecondActivity.this.finish();
                }


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
