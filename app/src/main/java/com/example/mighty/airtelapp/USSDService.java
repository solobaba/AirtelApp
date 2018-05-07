//package com.example.mighty.airtelapp;
//
//import android.accessibilityservice.AccessibilityService;
//import android.accessibilityservice.AccessibilityServiceInfo;
//import android.os.Build;
//import android.support.annotation.RequiresApi;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.accessibility.AccessibilityEvent;
//import android.view.accessibility.AccessibilityNodeInfo;
//import android.widget.Toast;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class USSDService extends AccessibilityService {
//
////import android.os.Build;
////import android.support.annotation.RequiresApi;
//
//    public static String TAG = USSDService.class.getSimpleName();
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    @Override
//    public void onAccessibilityEvent(AccessibilityEvent event) {
//        Log.d(TAG, "onAccessibilityEvent");
//
//        AccessibilityNodeInfo source = event.getSource();
//        /* if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && !event.getClassName().equals("android.app.AlertDialog")) { // android.app.AlertDialog is the standard but not for all phones  */
//        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && !String.valueOf(event.getClassName()).contains("AlertDialog")) {
//            return;
//        }
//        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED && (source == null || !source.getClassName().equals("android.widget.TextView"))) {
//            return;
//        }
//        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED && TextUtils.isEmpty(source.getText())) {
//            return;
//        }
//
//        List<CharSequence> eventText;
//
//        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
//            eventText = event.getText();
//        } else {
//            eventText = Collections.singletonList(source.getText());
//        }
//
//        String text = processUSSDText(eventText);
//
//        if (TextUtils.isEmpty(text)) return;
//
//        // Close dialog
//        performGlobalAction(GLOBAL_ACTION_BACK); // This works on 4.1+ only
//
//        Log.d(TAG, text);
//        // Handle USSD response here
//
//        String balance = text;
//        Pattern p = Pattern.compile(":(.*?)G");
//
//        Matcher m = p.matcher(balance);
//
//        while (m.find()) {
//            Toast.makeText(this, "Your last balance is " + m.group(1), Toast.LENGTH_SHORT).show();
//            Log.i("bal :", m.group(1));
//        }
//    }
//
//    private String processUSSDText(List<CharSequence> eventText) {
//        for (CharSequence s : eventText) {
//            String text = String.valueOf(s);
//            // Return text if text is the expected ussd response
//            if (true) {
//                return text;
//            }
//
//        }
//        return null;
//    }
//
//    @Override
//    public void onInterrupt() {
//    }
//
//    @Override
//    protected void onServiceConnected() {
//        super.onServiceConnected();
//        Log.d(TAG, "onServiceConnected");
//        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
//        info.flags = AccessibilityServiceInfo.DEFAULT;
//        info.packageNames = new String[]{"com.android.phone"};
//        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED | AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
//        info.feedbackType = AccessibilityServiceInfo.CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT;
//        setServiceInfo(info);
//    }
//
//}