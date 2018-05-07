package com.example.mighty.airtelapp.data;

import android.provider.BaseColumns;

public class DataContract {

    private DataContract(){
    }

    public static final class DataEntry implements BaseColumns {

        // Table and column names as constraints
        public final static String TABLE_NAME = "airdat";

        public final static String COLUMN_NAME_ID = BaseColumns._ID;
        public final static String COLUMN_RECIPIENT_NUMBER = "recNum";
        public final static String COLUMN_DATA_BUNDLE_NAME = "dataName";
        public final static String COLUMN_DATA_BUNDLE_VALUE = "dataValue";
        public final static String COLUMN_DATA_BUNDLE_COST = "dataCost";
        public final static String COLUMN_SPINNER_ROW = "spinRow";
        public final static String COLUMN_TIME_RECEIVED = "timeReceived";
        public final static String COLUMN_STATUS = "status";
        public final static String COLUMN_TIME_DONE = "timeDone";

        public static final String REQUEST_SOURCE_UNKNOWN = "Unknown";
        public static final String REQUEST_SOURCE_AIRTIME = "Airtime";
        public static final String REQUEST_SOURCE_CASH = "Cash";
        public static final String REQUEST_SOURCE_AGENT = "Agent";
        public static final String REQUEST_SOURCE_SALES_REP = "Sales Rep";
        public static final String REQUEST_SOURCE_WEB = "Web";
        public static final String REQUEST_SOURCE_API = "API";
    }

}
