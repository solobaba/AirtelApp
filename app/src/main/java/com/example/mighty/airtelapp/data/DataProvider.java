package com.example.mighty.airtelapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.mighty.airtelapp.data.DataContract.DataEntry;

public class DataProvider extends ContentProvider{

    public static final String LOG_TAG = DataProvider.class.getSimpleName();

    //Uri matcher code content of URI for the data table
    private static final int LOGS = 100;

    //Uri matcher code content of URI for a single data in the data table
    private static final int LOG_ID = 101;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        //Uri matcher for logs
        uriMatcher.addURI(DataContract.CONTENT_AUTHORITY, DataContract.PATH_LOG, LOGS);
        uriMatcher.addURI(DataContract.CONTENT_AUTHORITY, DataContract.PATH_LOG + "/#", LOG_ID);
    }

    //Database helper object
    private DataDbHelper mDbHelpher;

    @Override
    public boolean onCreate() {
        mDbHelpher = new DataDbHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection,String selection,String[] selectionArgs, String sortOrder) {

        //Get readable database
        SQLiteDatabase db = mDbHelpher.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        //Figure out if the URI matcher can match the URI to a specific code
        int match = uriMatcher.match(uri);
        switch (match){
            case LOGS:
                // For the LOGS code, query the logs table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the log table.
                cursor = db.query(DataEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case LOG_ID:
                // For the LOG_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.pets/pets/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.

                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = DataEntry._ID + "=?";
                selectionArgs = new String[]{
                        String.valueOf(ContentUris.parseId(uri))};

                // This will perform a query on the logs table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = db.query(DataEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
                default:
                    throw new IllegalArgumentException ("Cannot query unknown URI " + uri);
        }

        //Set notification URI on cursor
        //If data at the URI changes, then we need to know to update the cursor
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        //Return the cursor
        return cursor;
    }

    //Returns the MIME type of data for the content URI.
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri,ContentValues values) {
        final int match = uriMatcher.match (uri);

        switch (match){
            case LOGS:
                    return insertLogs(uri, values);
                    default:
                        throw new IllegalArgumentException ("Insertion is not supported for " + uri);
        }
        //return null;
    }

    private Uri insertLogs(Uri uri, ContentValues values) {
        SQLiteDatabase db = mDbHelpher.getWritableDatabase ();
        long data = db.insert(DataEntry.TABLE_NAME, null, values);
        Log.i("getId", String.valueOf(data));

        if (data == -1){
            Log.e(LOG_TAG, "Failed to insert new row for " + uri);
            return null;
        }

        //Notify all listener that the data content URI has changed
        getContext().getContentResolver().notifyChange(uri, null);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        // Toast.makeText(getContext(), "line " + id + " added ", Toast.LENGTH_LONG).show();

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId (uri, data);
    }

    //Delete the data at the given selection and selection arguments.
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    //Updates the data at the given selection and selection arguments, with the new ContentValues.
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
