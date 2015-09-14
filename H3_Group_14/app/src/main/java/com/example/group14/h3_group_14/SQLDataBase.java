package com.example.group14.h3_group_14;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.File;
import java.util.HashMap;


public class SQLDataBase extends ContentProvider {
    // Creating Uri
    static final String PROVIDER_NAME = "com.example.group14.provider";
    static final String BASE = "db";
    static final String URL = "content://" + PROVIDER_NAME + "/" +BASE;
    static final Uri CONTENT_URI = Uri.parse("content://" + URL);

    // IMPORTANT, FIRST ONE SELECTS EVERYTHING, SECOND SELECTS ONLY ONE
    static final int ALL_ROWS = 1;
    static final int SINGLE_ROW = 2;
    static final UriMatcher uriMatcher;


    private static HashMap<String, String> hash_values;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME,BASE,ALL_ROWS);
        uriMatcher.addURI(PROVIDER_NAME,BASE+"/#",SINGLE_ROW);
    }


    // Creating DB
    static final String id = "id";
    static final String NoteText = "NoteText";
    static final String DateTime = "DateTime";

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "db";
    static final String TABLE_NAME = "notes";
    static int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + id +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ NoteText + " TEXT NOT NULL, "
            + DateTime + " TEXT NOT NULL);";

    private static class  DatabaseHelper extends SQLiteOpenHelper{



        DatabaseHelper (Context context) {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.v("CP","Oncreate helper");
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
            Log.v("CP","upgrade");
            if (oldVersion == DATABASE_VERSION) {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
                onCreate(db);
                DATABASE_VERSION = newVersion;
            }
        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int affected_rows;

        switch (uriMatcher.match(uri)){
            case SINGLE_ROW:
                String last_id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) affected_rows = db.delete(TABLE_NAME,id + "=" + last_id,null);
                else affected_rows = db.delete(TABLE_NAME,id + "=" + last_id + " and " + selection,selectionArgs);
                break;
            case ALL_ROWS:
                affected_rows = db.delete(DATABASE_NAME,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return affected_rows;
    }

    @Override
    public String getType(Uri uri) {
        // Crec que aqui no va re....
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long row_id = db.insert(DATABASE_NAME,"",values );
        Log.v("CP","User inserted : " + values);
        if (row_id>0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI,row_id);
            getContext().getContentResolver().notifyChange(_uri, null);
            Log.v("CP", "Done inserting");
            return _uri;
        }
        Log.v("CP","Error inserting");
        throw new SQLException("Failed to add values intro db " + uri);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        // Maybe : DatabaseHelper dbHelper = new DatabaseHelper(context);
        Log.v("CP","Create CP");
        File dbFile = context.getDatabasePath("db");
        Log.v("CP","" + dbFile.exists());
        return dbFile.exists();
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder qdb = new SQLiteQueryBuilder();

        qdb.setTables(DATABASE_NAME);
        Log.v("CP","QUERY");
        switch (uriMatcher.match(uri)){
            // Selecting rows
            case ALL_ROWS:
                Log.v("CP","all_rows");
                qdb.setProjectionMap(hash_values);
                break;
            case SINGLE_ROW:
                Log.v("CP","single_rows");
                qdb.appendWhere( id + "=" +uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        // Sorting by id
        if (sortOrder ==  null || sortOrder.equals("")) sortOrder=id;
        // Starting query
        Cursor c = qdb.query(db,projection,selection,selectionArgs,null,null,sortOrder);
        c.setNotificationUri(getContext().getContentResolver(),uri);

        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int affected_rows;

        switch (uriMatcher.match(uri)){
            case SINGLE_ROW:
                String last_id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) affected_rows = db.update(TABLE_NAME,values,id + "=" + last_id,null);
                else affected_rows = db.update(TABLE_NAME,values,id + "=" + last_id + " and " + selection,selectionArgs);
                break;
            case ALL_ROWS:
                affected_rows = db.update(DATABASE_NAME,values,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return affected_rows;
    }
}
