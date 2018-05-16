package com.example.mighty.airtelapp.data;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.mighty.airtelapp.R;
import com.example.mighty.airtelapp.data.DataContract.DataEntry;

public class RequestHistory extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int REQUEST_LOADER = 1;
    RequestCursorAdapter requestCursorAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_activity);

        ListView listView = (ListView) findViewById(R.id.request_list);
        requestCursorAdapter = new RequestCursorAdapter(this, null);
        listView.setAdapter(requestCursorAdapter);
        getLoaderManager().initLoader(REQUEST_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        //Define a projection to show relevant columns
        String[] projection = {
                DataEntry._ID,
                DataEntry.COLUMN_RECIPIENT_NUMBER,
                DataEntry.COLUMN_DATA_BUNDLE_NAME,
                DataEntry.COLUMN_DATA_BUNDLE_VALUE,
                DataEntry.COLUMN_DATA_BUNDLE_COST,
                DataEntry.COLUMN_SPINNER_ROW,
                DataEntry.COLUMN_TIME_RECEIVED,
                DataEntry.COLUMN_STATUS,
                DataEntry.COLUMN_TIME_DONE
        };
        return new CursorLoader(this,
                DataEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        requestCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        requestCursorAdapter.swapCursor(null);
    }
}
