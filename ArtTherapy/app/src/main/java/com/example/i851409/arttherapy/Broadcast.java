package com.example.i851409.arttherapy;

//Importing the important packages
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

public class Broadcast extends BroadcastReceiver {

    private NotificationManager notification_manager;
    private int NOTIFICATION_ID;

    //Implementing the onReceive()
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.icon_notification)
                        .setContentTitle("ART THERAPY")
                        .setContentText("Please join Art Therapy")
                        .setAutoCancel(true)
                        .setContentInfo("GO TO ART THERAPY?")
                        .setColor(Color.RED);

        //Setting the vibrator
        Vibrator vibrator= (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(50);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);


        //Launching the DrawActivity
        Intent resultIntent = new Intent(context, DrawActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(DrawActivity.class);


        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setFullScreenIntent(resultPendingIntent,false);



        notification_manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification_manager.notify(0,mBuilder.build());


    }


}