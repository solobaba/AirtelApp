//package com.example.mighty.airtelapp.SMSservice;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Spinner;
//
//import com.example.mighty.airtelapp.CreateUser;
//import com.example.mighty.airtelapp.R;
//import com.example.mighty.airtelapp.data.DataContract;
//import com.example.mighty.airtelapp.data.DataContract.DataEntry;
//
//import static android.provider.Settings.Global.getString;
//
//public class NotificationBroadCast extends BroadcastReceiver {
//
//    Spinner spinnerValueRow;
//    public String mDataBundleValue = DataEntry.REQUEST_VALUE_UNKNOWN;
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//
//        ArrayAdapter dataBundleValue = ArrayAdapter.createFromResource(context,
//                R.array.array_data_bundle_value, android.R.layout.simple_spinner_item);
//        dataBundleValue.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        spinnerValueRow.setAdapter(dataBundleValue);
//        spinnerValueRow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener () {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selection = (String) parent.getItemAtPosition(position);
//                if(!TextUtils.isEmpty(selection)){
//                    if (selection.equals(getString(R.string.bundlevalue1))){
//                        mDataBundleValue = DataEntry.ONE_FIVE_GB;
//                    }else if (selection.equals(getString(R.string.bundlevalue2))){
//                        mDataBundleValue = DataEntry.THREE_FIVE_GB;
//                    }else if (selection.equals(getString(R.string.bundlevalue3))){
//                        mDataBundleValue = DataEntry.FIVE_GB;
//                    }else {
//                        mDataBundleValue = DataEntry.REQUEST_VALUE_UNKNOWN;
//                    }
//                }
//            }
//
//            private String getString(int bundlevalue1) {
//                return null;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                mDataBundleValue = DataEntry.REQUEST_VALUE_UNKNOWN;
//            }
//        });
//
//    }
//}
