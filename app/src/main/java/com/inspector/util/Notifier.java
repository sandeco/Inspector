package com.inspector.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.inspector.R;

public class Notifier {

    private static int NOTIFICATION_ID = 120;

    public static void show(String title, String message) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(App.getContext())
                        .setSmallIcon(android.R.drawable.stat_notify_sync)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setLargeIcon(BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.ic_launcher))
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setVibrate(new long[]{0, 500})
                        .setLights(Color.WHITE, 1000, 2000);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            builder.setVisibility(Notification.VISIBILITY_PUBLIC);


        NotificationManager notifyManager =
                (NotificationManager) App.getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        notifyManager.notify(NOTIFICATION_ID, builder.build());
    }
}
