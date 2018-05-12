package com.example.mighty.airtelapp;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mighty.airtelapp.data.DataContract.DataEntry;
import com.example.mighty.airtelapp.data.DataDbHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CreateUser extends AppCompatActivity {

    public static final String AIRTEL_CODE = "123";
    public static final String CONTACT_NETWORK = "1";

    EditText recipientNumber, dataBundleName, dataBundleValue, dataBundleCost;
    Spinner spinnerRow;
    Button button;

    int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, delilveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;

    DataDbHelper mDbHelper;
    DateFormat dateFormat;
    Date date;

    private String mRequestSource = DataEntry.REQUEST_SOURCE_UNKNOWN;
    private boolean mRequestSourceHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);
        Intent intent = new Intent(this, QueryService.class);
        startService(intent);

        mDbHelper = new DataDbHelper(this);
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        date = new Date();

        recipientNumber = findViewById(R.id.recipient_number);
        dataBundleName = findViewById(R.id.data_bundle_name);
        dataBundleValue = findViewById(R.id.data_bundle_value);
        dataBundleCost = findViewById(R.id.data_bundle_cost);
        spinnerRow = findViewById(R.id.resource_spinner);
        button = findViewById(R.id.send_data_button);

        sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        delilveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

        setupSpinner();

        spinnerRow.setOnTouchListener(mTouchListener);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Saved to database
                sendData();
                //Exit activity
                finish();
            }
        });

    }

    // Setup the dropdown spinner
    private void setupSpinner(){
        ArrayAdapter requestSourceAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_request_source, android.R.layout.simple_spinner_item);
        requestSourceAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerRow.setAdapter(requestSourceAdapter);
        spinnerRow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.spinnertype1))) {
                        mRequestSource = DataEntry.REQUEST_SOURCE_AIRTIME;
                    } else if (selection.equals(getString(R.string.spinnertype2))) {
                        mRequestSource = DataEntry.REQUEST_SOURCE_CASH;
                    } else if (selection.equals(getString(R.string.spinnertype3))) {
                        mRequestSource = DataEntry.REQUEST_SOURCE_AGENT;
                    } else if (selection.equals(getString(R.string.spinnertype4))) {
                        mRequestSource = DataEntry.REQUEST_SOURCE_SALES_REP;
                    } else if (selection.equals(getString(R.string.spinnertype5))) {
                        mRequestSource = DataEntry.REQUEST_SOURCE_WEB;
                    } else if (selection.equals(getString(R.string.spinnertype6))) {
                        mRequestSource = DataEntry.REQUEST_SOURCE_API;
                    } else {
                        mRequestSource = DataEntry.REQUEST_SOURCE_UNKNOWN;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mRequestSource = DataEntry.REQUEST_SOURCE_UNKNOWN;
            }
        });
    }

    //Insert data into the database
    public void sendData() {
        //Read from input fields and use trim to eliminate leading or
        //trailing white space
        String recNum = recipientNumber.getText().toString().trim();
        String dataName = dataBundleName.getText().toString().trim();
        String dataValue = dataBundleValue.getText().toString().trim();
        String dataCost = dataBundleCost.getText().toString().trim();
        String mRequestSource = spinnerRow.getSelectedItem().toString();

        // Create an object of database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //Create a ContentValues object where column names are the keys,
        //and data attributes are the values
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataEntry.COLUMN_RECIPIENT_NUMBER, recNum);
        contentValues.put(DataEntry.COLUMN_DATA_BUNDLE_NAME, dataName);
        contentValues.put(DataEntry.COLUMN_DATA_BUNDLE_VALUE, dataValue);
        contentValues.put(DataEntry.COLUMN_DATA_BUNDLE_COST, dataCost);
        contentValues.put(DataEntry.COLUMN_TIME_RECEIVED, dateFormat.format(date));
        contentValues.put(DataEntry.COLUMN_SPINNER_ROW, mRequestSource);
        contentValues.put(DataEntry.COLUMN_STATUS, "Data saved successfully");
        contentValues.put(DataEntry.COLUMN_TIME_DONE, dateFormat.format(date));

        long newRowId = db.insert(DataEntry.TABLE_NAME, null, contentValues);
        // Log.v("MainActivity", "New row ID " + newRowId);
        // Log.i("info", "newRow" + newRowId);

        if (newRowId == -1) {
            // Log.i("Info :", "Error getting data..");
            Toast.makeText(this, "Error getting data.. ", Toast.LENGTH_SHORT).show();
        } else {
            // Log.i("Info :", "Data Saved into database... ");
            Toast.makeText(this, "Data sent with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }

        showNotification();
        sendSMS();
    }

    private void sendSMS() {
        String recNum = recipientNumber.getText().toString().trim();
        String dataName = dataBundleName.getText().toString().trim();
        String dataValue = dataBundleValue.getText().toString().trim();
        String dataCost = dataBundleCost.getText().toString().trim();
        String mRequestSource = spinnerRow.getSelectedItem().toString();
        String message = recNum + dataName + dataValue + dataCost;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            }ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            } else{
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(message, null, mRequestSource, sentPI, delilveredPI);
        }

        }

    @Override
    protected void onResume() {
        super.onResume();

        smsSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "SMS sent!", Toast.LENGTH_LONG).show();
                        break;

                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(context, "Generic failure!", Toast.LENGTH_LONG).show();
                        break;

                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(context, "No service!", Toast.LENGTH_LONG).show();
                        break;

                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(context, "Null PDU!", Toast.LENGTH_LONG).show();
                        break;

                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            Toast.makeText(context, "Radio off!", Toast.LENGTH_LONG).show();
                            break;
                }
            }
        };

        smsDeliveredReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "SMS delivered!", Toast.LENGTH_LONG).show();
                        break;

                    case Activity.RESULT_CANCELED:
                        Toast.makeText(context, "SMS not delivered!", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        registerReceiver(smsSentReceiver, new IntentFilter(SENT));
        registerReceiver(smsDeliveredReceiver, new IntentFilter(DELIVERED));
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(smsDeliveredReceiver);
        unregisterReceiver(smsSentReceiver);
    }

    public void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.message);
        builder.setContentTitle("Mighty notifiction");
        builder.setContentText("Mighty data notification ....");
        Intent intent = new Intent(this, NotificationClass.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationClass.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0, builder.build());
    }

    //    Check balance
    public void checkBal() {
        String ussdCode = "*" + AIRTEL_CODE + Uri.encode("#");
        startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + ussdCode)));
    }

    //Contact Network
    public void contactNetwork() {
        String ussdCode = "1" + CONTACT_NETWORK + Uri.encode("1");
        startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + ussdCode)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_data:
//                Toast.makeText(this, "Checking log", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.check_balance_request:
                checkBal();
                Toast.makeText(this, "Checking balance", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.contact_network:
                contactNetwork();
                Toast.makeText(this, "Contacting network", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

