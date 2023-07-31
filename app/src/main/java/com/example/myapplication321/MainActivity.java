package com.example.myapplication321;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;



import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MainActivity extends AppCompatActivity {


    Button buttonTriggerNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

        buttonTriggerNotification = (Button) findViewById(R.id.button);

        buttonTriggerNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerNotification();
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(getString(R.string.NEWS_CHANNEL_ID), getString(R.string.CHANNEL_NEWS), NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(getString(R.string.CHANNEL_DESCRIPTION));
            notificationChannel.setShowBadge(true);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void triggerNotification() {
        Intent intent = new Intent(this, NotificationDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.NEWS_CHANNEL_ID))
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("Notification Title")
                .setContentText("This is text, that will be shown as part of notification")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("This is text, that will be shown as part of notification"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setChannelId(getString(R.string.NEWS_CHANNEL_ID))
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManagerCompat.notify(getResources().getInteger(R.integer.notificationId), builder.build());


    }


}