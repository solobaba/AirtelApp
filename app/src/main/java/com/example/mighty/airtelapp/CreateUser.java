package com.example.mighty.airtelapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mighty.airtelapp.Service.EmailMessage;
import com.example.mighty.airtelapp.Service.NotificationClass;
import com.example.mighty.airtelapp.Service.QueryService;
import com.example.mighty.airtelapp.data.DataContract.DataEntry;
import com.example.mighty.airtelapp.data.DataDbHelper;
import com.example.mighty.airtelapp.data.RequestHistory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateUser extends AppCompatActivity {

    Toolbar mtoolbar;

    public static final String AIRTEL_CODE = "123";
    public static final String CONTACT_NETWORK = "1";

    EditText recipientNumber, dataBundleName, dataBundleCost;
    Spinner spinnerRow, spinnerValueRow;
    Button button, dataButton;

    //Airtel data codes
    public static final String ONE_FIVE_GB = "1.5GB";
    public static final String THREE_FIVE_GB = "3.5GB";
    public static final String FIVE_GB = "5GB";
    public static final String CODE_ONE_FIVE_GB = "*141**5*2*1*5*1";
    public static final String CODE_THREE_FIVE_GB = "*141**5*2*1*4*1";
    public static final String CODE_FIVE_GB = "*141**5*2*1*3*1";

    String recNum, dataName, dataCost;
    String mDataBalance;

    int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, delilveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;

    DataDbHelper mDbHelper;
    DateFormat dateFormat;
    Date date;

    private Receiver receiver;
    private TextView dataBalance, dataTime;
    String mResult, currentTime, CURRENT_BALANCE, CURRENT_TIME;

    private String mRequestSource = DataEntry.REQUEST_SOURCE_UNKNOWN;
    public String mDataBundleValue = DataEntry.REQUEST_VALUE_UNKNOWN;
    private boolean mRequestSourceHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);

        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        mtoolbar.setTitle("AirtelApp");
//        mtoolbar.setTitle(getString(R.string.app_name));
        mtoolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mtoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain ();
            }
        });

        Intent intent = new Intent (this, QueryService.class);
        startService(intent);

        IntentFilter filter = new IntentFilter (Receiver.ACTION_RESPONSE);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new Receiver();
        registerReceiver(receiver, filter);

        mDbHelper = new DataDbHelper (this);
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        date = new Date();

        recipientNumber = findViewById(R.id.recipient_number);
        dataBundleName = findViewById(R.id.data_bundle_name);
        spinnerValueRow = findViewById(R.id.data_bundle_value);
        dataBundleCost = findViewById(R.id.data_bundle_cost);
        spinnerRow = findViewById(R.id.resource_spinner);
        button = findViewById(R.id.send_data_button);

        recNum = recipientNumber.getText().toString().trim();
        dataName = dataBundleName.getText().toString().trim();
//        mDataBundleValue = spinnerValueRow.getSelectedItem().toString();
        dataCost = dataBundleCost.getText().toString().trim();
//        mRequestSource = spinnerRow.getSelectedItem().toString();

        sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        delilveredPI = PendingIntent.getBroadcast (this, 0, new Intent(DELIVERED), 0);

        //Data balance
        dataBalance = (TextView) findViewById (R.id.balance_airtime);
//        dataTime = (TextView) findViewById(R.id.balance_airtime);
        dataButton = findViewById (R.id.data_balance);

        //Save data balance received from onReceiver
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.mighty.airtelapp", Context.MODE_PRIVATE);
        CURRENT_BALANCE = sharedPreferences.getString ("dataBalance", "");
//        CURRENT_TIME = sharedPreferences.getString("currentTime", "");
        dataBalance.setText (CURRENT_BALANCE);
//        dataTime.setText(CURRENT_TIME);

        setupSpinner();
        setupDataValue();

        spinnerValueRow.setOnTouchListener(mTouchListener);
        spinnerRow.setOnTouchListener(mTouchListener);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Saved to database
                sendData();
                //Exit activity
                finish();
            }
        });

        //Data balance button method
        dataButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkBal();
                trackBalance();
            }
        });

//        final Handler handler = new Handler();
//        new Thread (new Runnable(){
//            public void run(){
//                while(true){
//                    try {
//                       handler.post(new Runnable(){
//                           public void run(){
//                               checkBal();
//                               //Top-up data method
//                           }
//                       });
//                        TimeUnit.MINUTES.sleep(2000);
//                    }catch(Exception e){
//                    }
//                }
//            }
//        }).start();
    }

    private void backToMain(){
//        onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }

    //Setup the request source dropdown spinner
    private void setupDataValue() {
        ArrayAdapter dataBundleValue = ArrayAdapter.createFromResource (this, R.array.array_data_bundle_value, android.R.layout.simple_spinner_item);
        dataBundleValue.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerValueRow.setAdapter(dataBundleValue);
        spinnerValueRow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String)parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)){
                    if (selection.equals(getString(R.string.bundlevalue1))){
                        mDataBundleValue = DataEntry.ONE_FIVE_GB;
                    }else if(selection.equals(getString(R.string.bundlevalue2))){
                        mDataBundleValue = DataEntry.THREE_FIVE_GB;
                    }else if (selection.equals(getString(R.string.bundlevalue3))){
                        mDataBundleValue = DataEntry.FIVE_GB;
                    }else{
                        mDataBundleValue = DataEntry.REQUEST_VALUE_UNKNOWN;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){
                mDataBundleValue = DataEntry.REQUEST_VALUE_UNKNOWN;
            }
        });
    }

    //Setup the request source dropdown spinner
    private void setupSpinner(){
        ArrayAdapter requestSourceAdapter = ArrayAdapter.createFromResource(this, R.array.array_request_source, android.R.layout.simple_spinner_item);
        requestSourceAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerRow.setAdapter(requestSourceAdapter);
        spinnerRow.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty (selection)){
                    if(selection.equals (getString(R.string.spinnertype1))){
                        mRequestSource = DataEntry.REQUEST_SOURCE_AIRTIME;
                    }else if(selection.equals(getString(R.string.spinnertype2))){
                        mRequestSource = DataEntry.REQUEST_SOURCE_CASH;
                    }else if(selection.equals (getString(R.string.spinnertype3))){
                        mRequestSource = DataEntry.REQUEST_SOURCE_AGENT;
                    }else if(selection.equals(getString(R.string.spinnertype4))){
                        mRequestSource = DataEntry.REQUEST_SOURCE_SALES_REP;
                    }else if(selection.equals(getString(R.string.spinnertype5))){
                        mRequestSource = DataEntry.REQUEST_SOURCE_WEB;
                    }else if(selection.equals(getString(R.string.spinnertype6))){
                        mRequestSource = DataEntry.REQUEST_SOURCE_API;
                    }else{
                        mRequestSource = DataEntry.REQUEST_SOURCE_UNKNOWN;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){
                mRequestSource = DataEntry.REQUEST_SOURCE_UNKNOWN;
            }
        });
    }

    //Insert data into the database
    public void sendData(){
        //Read from input fields and use trim to eliminate leading or
        //trailing white space
        String recNum = recipientNumber.getText().toString().trim();
        String dataName = dataBundleName.getText().toString().trim();
        String mDataBundleValue = spinnerValueRow.getSelectedItem().toString();
        String dataCost = dataBundleCost.getText().toString().trim();
        String mRequestSource = spinnerRow.getSelectedItem().toString();

        // Create an object of database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //Create a ContentValues object where column names are the keys,
        //and data attributes are the values
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataEntry.COLUMN_RECIPIENT_NUMBER, recNum);
        contentValues.put(DataEntry.COLUMN_DATA_BUNDLE_NAME, dataName);
        contentValues.put(DataEntry.COLUMN_DATA_BUNDLE_VALUE, mDataBundleValue);
        contentValues.put(DataEntry.COLUMN_DATA_BUNDLE_COST, dataCost);
        contentValues.put(DataEntry.COLUMN_TIME_RECEIVED, dateFormat.format(date));
        contentValues.put(DataEntry.COLUMN_SPINNER_ROW, mRequestSource );
        contentValues.put(DataEntry.COLUMN_STATUS, "Data saved successfully");
        contentValues.put(DataEntry.COLUMN_TIME_DONE, dateFormat.format(date));

        Uri newRowId = getContentResolver().insert(DataEntry.CONTENT_URI,contentValues);
//        Uri uri = getContentResolver().insert(DataEntry.CONTENT_URI, contentValues);
        if(newRowId == null){
            Log.i ("Info:", "Error getting data..");
            Toast.makeText (this, "Error getting data.. ", Toast.LENGTH_SHORT).show();
        }else{
            Log.i ("info:", "Data saved into database...");
            Toast.makeText(this, "Data sent successfully", Toast.LENGTH_SHORT ).show();
        }

//        long newRowId = db.insert(DataEntry.TABLE_NAME, null, contentValues);
//        // Log.v("MainActivity", "New row ID " + newRowId);
//        // Log.i("info", "newRow" + newRowId);
//
//        if (newRowId == -1) {
//            // Log.i("Info :", "Error getting data..");
//            Toast.makeText(this, "Error getting data.. ", Toast.LENGTH_SHORT).show();
//        } else {
//            // Log.i("Info :", "Data Saved into database... ");
//            Toast.makeText(this, "Data sent with row id: " + newRowId, Toast.LENGTH_SHORT).show();
//        }

        airtelData();
        String message = "Be Mighty!, You received " + mDataBundleValue + " " + dataName + " from Mighty Interactive Limited. " + "Kindly dial *461*2# to check your balance. Thank you!";
        String phoneNumber = recNum;
        sendSMS (phoneNumber, message);
        emailMessage();
        showNotification();
        dialogBox();
    }

    private void sendSMS(String phoneNumber, String message){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)){
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        }else{
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNumber, null, message, sentPI, delilveredPI);
        }
//        MediaPlayer mySound = MediaPlayer.create(this, R.raw.notification);
//        mySound.start();
    }

    private void emailMessage(){
        String email = "interactivemighty@gmail.com";
        String subject = "Mighty Data Notification";
        String message = "Be Mighty!, You received " + mDataBundleValue + " " + dataName + " from Mighty Interactive Limited. " + "Kindly dial *461*2# to check your balance. Thank you!";

        try{
            EmailMessage emailMsg = new EmailMessage (this, email, subject, message);
            emailMsg.execute();
        }catch(Exception e){
            Log.e ("SendMail", e.getMessage(), e);
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        smsSentReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//
//                switch (getResultCode()){
//                    case Activity.RESULT_OK:
//                        Toast.makeText(context, "SMS sent!", Toast.LENGTH_LONG).show();
//                        break;
//
//                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                        Toast.makeText(context, "Generic failure!", Toast.LENGTH_LONG).show();
//                        break;
//
//                    case SmsManager.RESULT_ERROR_NO_SERVICE:
//                        Toast.makeText(context, "No service!", Toast.LENGTH_LONG).show();
//                        break;
//
//                    case SmsManager.RESULT_ERROR_NULL_PDU:
//                        Toast.makeText(context, "Null PDU!", Toast.LENGTH_LONG).show();
//                        break;
//
//                        case SmsManager.RESULT_ERROR_RADIO_OFF:
//                            Toast.makeText(context, "Radio off!", Toast.LENGTH_LONG).show();
//                            break;
//                }
//            }
//        };
//
//        smsDeliveredReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//
//                switch (getResultCode()){
//                    case Activity.RESULT_OK:
//                        Toast.makeText(context, "SMS delivered!", Toast.LENGTH_LONG).show();
//                        break;
//
//                    case Activity.RESULT_CANCELED:
//                        Toast.makeText(context, "SMS not delivered!", Toast.LENGTH_LONG).show();
//                        break;
//                }
//            }
//        };
//        registerReceiver(smsSentReceiver, new IntentFilter(SENT));
//        registerReceiver(smsDeliveredReceiver, new IntentFilter(DELIVERED));
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        unregisterReceiver(smsDeliveredReceiver);
//        unregisterReceiver(smsSentReceiver);
//    }

    private void airtelData(){
        if(mDataBundleValue.equals(ONE_FIVE_GB)){
            String ussdCode = CODE_ONE_FIVE_GB + recNum + Uri.encode ("#");
            startActivity (new Intent("android.intent.action.CALL", Uri.parse("tel:" + ussdCode)));
        }else if(mDataBundleValue.equals(THREE_FIVE_GB)){
            String ussdCode = CODE_THREE_FIVE_GB + recNum + Uri.encode("#");
            startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + ussdCode)));
        }else if(mDataBundleValue.equals(FIVE_GB)){
            String ussdCode = CODE_FIVE_GB + recNum + Uri.encode ("#");
            startActivity (new Intent("android.intent.action.CALL", Uri.parse("tel:" + ussdCode)));
        }else{
            mDataBundleValue = DataEntry.REQUEST_VALUE_UNKNOWN;
        }
    }

    private void dialogBox(){
        AlertDialog.Builder alertDialogBox = new AlertDialog.Builder (this);
        //Set dialog message
        alertDialogBox.setMessage("Data sent successfully").setNegativeButton ("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                //Close dialog box
                dialog.cancel();
            }
        });
        //Create and display the alert dialog
        AlertDialog alertDialog = alertDialogBox.create();
        alertDialog.show();
    }

    public void showNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder (this);
        builder.setSmallIcon(R.drawable.message);
        builder.setContentTitle("Mighty notifiction");
        builder.setContentText(mResult);
        builder.setAutoCancel(true);
        Intent intent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack (NotificationClass.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent (0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent (pendingIntent);
        NotificationManager nm = (NotificationManager) getSystemService ( Context.NOTIFICATION_SERVICE );
        nm.notify(0, builder.build());

        MediaPlayer mySound = MediaPlayer.create (this, R.raw.notification);
        mySound.start();
    }

    //Accessibility service response
    public class Receiver extends BroadcastReceiver{
        public static final String ACTION_RESPONSE = "com.example.mighty.airtelapp.android.intent.action.CALL";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(CreateUser.Receiver.ACTION_RESPONSE)) {

                //Response from USSD stored in mResult
                mResult = intent.getStringExtra("result");
                currentTime = intent.getStringExtra("current time");

                //Regex method for extracting balance airtime
                String balance = mResult;
                Pattern pattern = Pattern.compile (":(.*?)G");
                Matcher match = pattern.matcher (balance);

                if (match.find()){
                    String mDataBalance = "Your balance is " + match.group(1);
                    Toast.makeText ( context, "Your balance is " + match.group (1), Toast.LENGTH_LONG ).show();

//                    String currentBalSubString = mDataBalance.substring(18,25);
//                    double balanceThreshold = 4000;
//                    double topUp = Double.parseDouble(currentBalSubString);
//                    if (topUp <= balanceThreshold){
//                        Toast.makeText(CreateUser.this, "Top up your data balance please....", Toast.LENGTH_LONG).show();
//                    }
//                    Toast.makeText ( context, "Your balance is " + match.group (1), Toast.LENGTH_LONG ).show();

//                    if(!(match.group(1).equals(mResult))){
//                        Toast.makeText(context, "Top up your data please....", Toast.LENGTH_LONG).show();
//                    }

                    //Saved broadcast response
                    SharedPreferences sharedPreferences = getSharedPreferences("com.example.mighty.airtelapp", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("airtimeBalance", mDataBalance);
                    editor.putString("currentTime", currentTime);
                    editor.apply();
                    dataBalance.setText(mDataBalance);
                    //dataTime.setText(currentTime);



                }else{
                    dataBalance.setText(mResult);
//                    dataTime.setText(currentTime);
                }
            }
        }
    }

    private void trackBalance() {
        double balanceThreshold = 4000;
        String mDataBalance = "MainA/C:N3984.75;8x Voice";
        String currentBalSubString = mDataBalance.substring(9, 16);
        double topUpData = Double.parseDouble(currentBalSubString);
        if (topUpData <= balanceThreshold){
            Toast.makeText (this, "Top up your data balance please....", Toast.LENGTH_LONG).show();
            Log.i ("trackBalance", "Top up your data balance please....");
            //speak("Top up your data please, your account is getting low");
        }
        Log.i ("subString", currentBalSubString);
    }

    //Check balance
    public void checkBal() {
        String ussdCode = "*" + AIRTEL_CODE + Uri.encode ( "#" );
        startActivity(new Intent("android.intent.action.CALL", Uri.parse ("tel:" + ussdCode)));
    }

    //Contact Network
    public void contactNetwork(){
        String ussdCode = "1" + CONTACT_NETWORK + Uri.encode ("1");
        startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + ussdCode)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu options from the res/menu/menu_catalog.xml file
        //This adds menu items to the app bar
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.request_sent:
                startActivity(new Intent(CreateUser.this, RequestHistory.class));
                Toast.makeText(this, "Requesting history", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.check_balance_request:
                checkBal();
                Toast.makeText(this, "Checking balance", Toast.LENGTH_SHORT ).show();
                return true;

            case R.id.contact_network:
                contactNetwork();
                Toast.makeText(this, "Contacting network", Toast.LENGTH_SHORT ).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}