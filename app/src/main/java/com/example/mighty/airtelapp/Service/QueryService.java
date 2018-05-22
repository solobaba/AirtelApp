package com.example.mighty.airtelapp.Service;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

public class QueryService extends IntentService {

    public static final String PARAM_IN_MSG = "imsg";
    private static final String TAG = "com.example.mighty.airtelapp";

    //Creates an IntentService.  Invoked by your subclass's constructor.
    //@param QueryService Used to name the worker thread, important only for debugging.
    public QueryService() {
        super("QueryService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //This is what the service does
        Log.i(TAG, "The service has now started");

        String msg = intent.getStringExtra(PARAM_IN_MSG);
        SystemClock.sleep(15000); //sleep for 15 sceonds
        String resultTxt = msg + " "
                + android.text.format.DateFormat.format("MM/dd/yy h:mma", System.currentTimeMillis());

    }
}
