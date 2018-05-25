package com.example.mighty.airtelapp.JobService;

import android.app.NotificationManager;
import android.content.Context;

class NotificationUtils {

    public static void remindUser(Context context){
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
