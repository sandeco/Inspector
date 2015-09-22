package com.inspector.util;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.inspector.R;

public class Notifier {

    private static final int NOTIFICATION_ID = 1;

    public static void show(String title, String message) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(App.getContext())
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(message);


        NotificationManager notifyManager =
                (NotificationManager) App.getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        notifyManager.notify(NOTIFICATION_ID, builder.build());
    }
}
