package com.example.gaoch.music_players_8.controller;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.example.gaoch.music_players_8.R;

/**
 * Created by gaoch on 2017-06-04.
 */

public class MusicNotification {
    private Notification notification;
    private RemoteViews remoteViews;
    private Context context;
    private NotificationManager notificationManager;
    public MusicNotification(Context context){
        this.context=context;
    }
    @SuppressLint("NewApi")
    public void Initnotify(){
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        // 此处设置的图标仅用于显示新提醒时候出现在设备的通知栏
        mBuilder.setSmallIcon(R.drawable.small_notify);
       // mBuilder.setLargeIcon(BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.notify_icon));
        // 当用户下来通知栏时候看到的就是RemoteViews中自定义的Notification布局
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
        mBuilder.setContent(remoteViews);
        notification = mBuilder.build();
        notification.flags=Notification.FLAG_ONGOING_EVENT;
        notificationManager.notify(1,notification);
    }
    public void closenotify(){
        notification.flags=Notification.FLAG_AUTO_CANCEL;
    }
}
