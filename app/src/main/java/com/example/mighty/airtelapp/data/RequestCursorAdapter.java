package com.example.mighty.airtelapp.data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.mighty.airtelapp.R;
import com.example.mighty.airtelapp.data.DataContract.DataEntry;

class RequestCursorAdapter extends CursorAdapter {
    public RequestCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.request_list_activity, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //Extract views to modify
        TextView recipientNumberTextView = (TextView) view.findViewById(R.id.recipient_number);
        TextView dataBundleNameTextView = (TextView) view.findViewById(R.id.data_bundle_name);
        TextView dataBundleValueTextView = (TextView) view.findViewById(R.id.data_bundle_value);
        TextView dataBundleCostTextView = (TextView) view.findViewById(R.id.data_bundle_cost);
        TextView spinnerRowTextView = (TextView) view.findViewById(R.id.resource_spinner);
        TextView timeReceivedTextView = (TextView) view.findViewById(R.id.time_received);
        TextView timeDoneTextView = (TextView) view.findViewById(R.id.time_done);

        int recipientColumnIndex = cursor.getColumnIndex(DataEntry.COLUMN_RECIPIENT_NUMBER);
        int dataBundleNameColumnIndex = cursor.getColumnIndex(DataEntry.COLUMN_DATA_BUNDLE_NAME);
        int dataBundleValueColumnIndex = cursor.getColumnIndex(DataEntry.COLUMN_DATA_BUNDLE_VALUE);
        int dataBundleCostColumnIndex = cursor.getColumnIndex(DataEntry.COLUMN_DATA_BUNDLE_COST);
        int spinnerRowColumnIndex = cursor.getColumnIndex(DataEntry.COLUMN_SPINNER_ROW);
        int timeReceivedColumnIndex = cursor.getColumnIndex(DataEntry.COLUMN_TIME_RECEIVED);
        int timeDoneColumnIndex = cursor.getColumnIndex(DataEntry.COLUMN_TIME_RECEIVED);

        String recipientNumber = cursor.getString(recipientColumnIndex);
        String dataBundleName = cursor.getString(dataBundleNameColumnIndex);
        String dataBundleValue = cursor.getString(dataBundleValueColumnIndex);
        String dataBundleCost = cursor.getString(dataBundleCostColumnIndex);
        String spinnerRow = cursor.getString(spinnerRowColumnIndex);
        String timeReceived = cursor.getString(timeReceivedColumnIndex);
        String timeDone = cursor.getString(timeDoneColumnIndex);

        recipientNumberTextView.setText(recipientNumber);
        dataBundleNameTextView.setText(dataBundleName);
        dataBundleValueTextView.setText(dataBundleValue);
        dataBundleCostTextView.setText(dataBundleCost);
        spinnerRowTextView.setText(spinnerRow);
        timeReceivedTextView.setText(timeReceived);
        timeDoneTextView.setText(timeDone);
    }
}
