package com.example.group14.h3_group_14;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.HashMap;


public class SQLDataBase extends ContentProvider {
    // Creating Uri
    static final String PROVIDER_NAME = "com.example.group14.provider";
    static final String BASE = "db";
    static final String URL = "content://" + PROVIDER_NAME + "/" +BASE;
    static final Uri CONTENT_URI = Uri.parse("content://" + URL);
    static final int uriCode = 1;
    static final UriMatcher uriMatcher;


    private static HashMap<String, String> values;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME,BASE,uriCode);
        uriMatcher.addURI(PROVIDER_NAME,BASE+"/*",uriCode);
    }


    // Creating DB
    static final String id = "id";
    static final String NoteText = "NoteText";
    static final String DateTime = "DateTime";

    private static class  DatabaseHelper extends SQLiteOpenHelper{

        private SQLiteDatabase db;
        static final String DATABASE_NAME = "db";
        static final String TABLE_NAME = "notes";
        static final int DATABASE_VERSION = 1;
        static final String CREATE_DB_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + id +" INTEGER PRIMARY KEY, "+ NoteText + " TEXT NOT NULL, "
+ DateTime + " TEXT NOT NULL);";

        DatabaseHelper (Context context) {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void OnCreate(SQLDataBase){
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade ()
    }

    public SQLDataBase() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
