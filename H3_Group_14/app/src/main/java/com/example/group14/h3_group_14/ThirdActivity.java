package com.example.group14.h3_group_14;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**Source:
 * http://www.vogella.com/tutorials/AndroidListView/article.html
 */

public class ThirdActivity extends ListActivity {
    private SQLDataBase db;
    List<String> list = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAllEntries();
        Log.v("List", "" + list);

        // use your custom layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.activity_third, R.id.listView, list);
        setListAdapter(adapter);


    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        final String item = (String) getListAdapter().getItem(position);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to delete this note?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                //Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
                String[] parts = item.split("\n");
                String[] note = parts[1].split(": ");
                //Toast.makeText(this, note[1], Toast.LENGTH_LONG).show();
                String[] args = new String[]{note[1]};
                getContentResolver().delete(SQLDataBase.CONTENT_URI, "DateTime=?", args);
                reload();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public void reload(){
        Intent reload = new Intent(ThirdActivity.this, ThirdActivity.class);
        startActivity(reload);
        this.finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_third, menu);
        return true;
    }

    public List getAllEntries () {
        // Retrieve student records
        String URL = "content://com.example.group14.provider.Notes/db";

        Uri notesText = Uri.parse(URL);
        Cursor c = managedQuery(notesText, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                list.add("Content: "+c.getString(c.getColumnIndexOrThrow("NoteText"))+"\nDate: " + c.getString(c.getColumnIndexOrThrow("DateTime")));
            } while (c.moveToNext());
        }
        Log.v("List1", "" + list);
        return list;
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
        Intent intent = new Intent(ThirdActivity.this, AboutActivity.class);
        startActivity(intent);
    }
}
