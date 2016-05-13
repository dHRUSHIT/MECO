package com.example.android.finalapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

/**
 * Created by dhrushit.s on 2/17/2016.
 */
@SuppressLint("OverrideAbstract")
public class NotificationMan extends NotificationListenerService {
    public NotificationMan(){};
    private static String ret = "";

    public String fetchNotifications(String contents, Context context) {
        StatusBarNotification[] statusBarNotifications = this.getActiveNotifications();
        if(statusBarNotifications != null) {
            for (StatusBarNotification statusBarNotification : statusBarNotifications) {
                ret = String.valueOf(statusBarNotification.getPackageName()) + ":" + String.valueOf(statusBarNotification.getNotification()) + "\n";

            }
            return ret;
        }
        else {
            return "no notifications!";
        }
    }

}
