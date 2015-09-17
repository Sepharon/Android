package com.example.group14.h3_group_14;


import android.app.ListActivity;
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
        Log.v("List", ""+list);

        // use your custom layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.activity_third, R.id.listView, list);
        setListAdapter(adapter);


    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
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
        Log.v("List1", ""+list);
        return list;
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
