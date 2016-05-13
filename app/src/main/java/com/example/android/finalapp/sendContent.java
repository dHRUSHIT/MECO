package com.example.android.finalapp;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by dhrushit.s on 2/17/2016.
 */
public class sendContent {
    public static void sendSMS(String replyMessage, String senderNum, Context context) {
        if(replyMessage != null) {
            try {
                SmsManager smsManager = SmsManager.getDefault();

                PendingIntent sentPendingIntent = PendingIntent.getBroadcast(context, 0, new Intent("SMS_SENT"), 0);
                PendingIntent deliveredPendingIntent = PendingIntent.getBroadcast(context, 0, new Intent("SMS_DELIVERED"), 0);

                ArrayList<String> smsBodyParts = smsManager.divideMessage(replyMessage);
                ArrayList<PendingIntent> sentPendingIntents = new ArrayList<PendingIntent>();
                ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<PendingIntent>();

                for (int i = 0; i < smsBodyParts.size(); i++) {
                    sentPendingIntents.add(sentPendingIntent);
                    deliveredPendingIntents.add(deliveredPendingIntent);
                }
                smsManager.sendMultipartTextMessage(senderNum, null, smsBodyParts, sentPendingIntents, deliveredPendingIntents);

                //                smsManager.sendTextMessage(toPhoneNumber,null,smsReplyBody,null,null);
                Toast.makeText(context, "SMS sent to " + senderNum, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, replyMessage, Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(context, "SMS sending failed", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        else{
            // Get the default instance of SmsManager
            SmsManager smsManager = SmsManager.getDefault();
// Send a text based SMS
            smsManager.sendTextMessage(senderNum, null, "Sorry, could not fetch the data", null, null);
        }
    }
}
