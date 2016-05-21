package com.example.android.finalapp;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;

import java.util.Calendar;

/**
 * Created by dhrushit.s on 2/17/2016.
 */
public class MessagesMan {

    private boolean allowFeature = false;

    public static String fetchNotifications(String contents, Context context) {
        return null;
    }

    public static String getMessages(String content, Context context) {
        SmsManager smsManager = SmsManager.getDefault();
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.DATE,-1);
        String[] args = {String.valueOf(calendar.getTime())};
//        String[] args = {String.valueOf(calendar.getTime()),"0"};
        ContentResolver contentResolver = context.getContentResolver();
        /*please uncomment in the next line for the final app*/
//        Cursor cursor = contentResolver.query(Telephony.Sms.Inbox.CONTENT_URI, null, Telephony.Sms.Inbox.DATE + ">= ?", args, null);
//        Cursor cursor = contentResolver.query(Telephony.Sms.Inbox.CONTENT_URI, null, "date>= ? AND read = ?", args, null);
        Cursor cursor = contentResolver.query(Telephony.Sms.Inbox.CONTENT_URI, null, null, null, null);
        int numberOfMessages = cursor.getCount();
        cursor.moveToFirst();
        String ret="";
        while (cursor.moveToNext()){

            ret = ret + cursor.getString(cursor.getColumnIndex("address")) + ":\n";
            ret = ret + cursor.getString(cursor.getColumnIndex("body")) + "\n\n";
        }

        cursor.close();


        return ret;
    }
}
