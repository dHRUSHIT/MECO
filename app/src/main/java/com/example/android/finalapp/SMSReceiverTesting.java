package com.example.android.finalapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by dhrushit.s on 2/15/2016.
 */
public class SMSReceiverTesting {
    SharedPreferences sharedPreferences;
    String replyMessage;

    public SMSReceiverTesting() {
    }
    public void onReceive(Context context,String message) {

        sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.preference_file_key), Context.MODE_PRIVATE);


        String str = "";
        Log.i(MainActivity.TAG, "onReceive --------------------------------------------------------------------");
        Toast.makeText(context, "SMS RECEIVER", Toast.LENGTH_SHORT).show();
        try {
            Log.d(MainActivity.TAG, "trying --------------------------------------------------------------------");
            if (message != null) {
                String[] contents = message.split("\r|\n|\r\n| |\\s+",4);
                for (int i = 0; i < contents.length ; i++)
                    contents[i] = contents[i].trim();
                if (contents[0].equalsIgnoreCase(sharedPreferences.getString("User", "dhrushit"))) {
                    SecurityMan securityMan = new SecurityMan(context);
                    if (!securityMan.checkPassPhrase(contents[2], contents[1], context)) {
                        Log.d("---------", "success matching the password and checking feature on or off");
                            /*remove this comment later on*/
//                            sendContent.sendSMS(SecurityMan.error,senderNum,context);
                        SecurityMan.error = null;
                        return;
                    }
                } else {
                    Log.d("---------", "expected " + sharedPreferences.getString("User", "dhrushit") + " but found " + contents[0]);
//                        sendContent.sendSMS("The username phrase is not found in the database", senderNum, context);
                    Log.d("---------", "sms sent");
                    return;
                }

//                    String s = "contents[2] = " + contents[2];
//                    Toast.makeText(context, s, Toast.LENGTH_LONG).show();


                //check the message for the data asked
                switch (contents[2]) {
                    case "ringer":
                        RingerMan ringerMan= new RingerMan(context);
                        replyMessage = ringerMan.manageRinger(contents[3]);
                        break;
                    case "contacts":
                        if(contents.length > 3)
                            replyMessage = ContactMan.getContacts(contents[3], context);
                        else
                            replyMessage = ContactMan.getContacts(null, context);


                        break;
                    case "location":
                        LocationMan locationMan = new LocationMan(context, "");
                        locationMan.fetchLocation();

                        return;
                    case "notifications":
                        NotificationMan notificationMan = new NotificationMan();
                        if(contents.length > 3)
                            replyMessage = notificationMan.fetchNotifications(contents[3], context);
                        else
                            replyMessage = notificationMan.fetchNotifications(null, context);

                        break;
                    case "logs":
                        LogMan logMan = new LogMan(context);
                        if(contents.length > 3)
                            logMan.getLogs(contents[3], context);
                        else
                            logMan.getLogs(null, context);

                        break;
                    case "battery":
                        if(contents.length > 3)
                            replyMessage = BatteryMan.getBatteryStats(contents[3], context);
                        else
                            replyMessage = BatteryMan.getBatteryStats(null, context);

                        break;
                    case "messages":
                        if(contents.length > 3)
                            replyMessage = MessagesMan.getMessages(contents[3], context);
                        else
                            replyMessage = MessagesMan.getMessages(null, context);

                        break;
                    case "control":
                        if(contents.length > 3)
                            replyMessage = ControllerMan.changeSystemSettings(contents[3], context);
                        else
                            replyMessage = ControllerMan.changeSystemSettings(null, context);

                        break;
                    default:
                        return;
                }


            }
            if(replyMessage !=null)
                Toast.makeText(context, replyMessage, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "null string", Toast.LENGTH_SHORT).show();

//            sendContent.sendSMS(replyMessage, senderNum, context);
        } catch (Exception e) {
            Log.d(MainActivity.TAG, "catching --------------------------------------------------------------------");
            Log.e("Sms Receiver", "Exception Received " + e);
        }
    }
}





