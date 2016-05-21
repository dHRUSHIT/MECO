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
public class SMSReceiver extends BroadcastReceiver {
    SharedPreferences sharedPreferences;
    final SmsManager sms =  SmsManager.getDefault();
    public SMSReceiver() {
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);

        SmsMessage[] msgs = null;
        String str = "";
        Log.i(MainActivity.TAG, "onReceive --------------------------------------------------------------------");
        Toast.makeText(context,"SMS RECEIVER",Toast.LENGTH_SHORT).show();
        try {
            Log.d(MainActivity.TAG, "trying --------------------------------------------------------------------");
            if (bundle != null) {
                Log.d(MainActivity.TAG, "bundle not null --------------------------------------------------------------------");
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                msgs = new SmsMessage[pdusObj.length];
                String replyMessage = null;
                String senderNum = null;
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();
                    Toast.makeText(context, "senderNum: " + senderNum + ",\nmessage: " + message, Toast.LENGTH_SHORT).show();

                    String[] contents = message.split("\r\n|\r|\n|\\s+",4);

                    Log.d(MainActivity.TAG, "sender -----------------------------" + phoneNumber);

                    for (int j = 0; j < contents.length-1; j++) {
                        contents[j] = contents[j].trim();
                        Log.d("------------","content " + j + "=" +  contents[j] );
                    }


                    if(contents[0].equalsIgnoreCase(sharedPreferences.getString("User","dhrushit")) ){
                        SecurityMan securityMan = new SecurityMan(context);
                        if(!securityMan.checkPassPhrase(contents[2],contents[1], context)) {
                            Log.d("---------", "success matching the password and checking feature on or off");
                            /*remove this comment later on*/
//                            sendContent.sendSMS(SecurityMan.error,senderNum,context);
                            SecurityMan.error = null;
                            return;
                        }
                    }else if(contents[0].equals("APPOTP")){
                        OTPVerification.verifyOTP(sharedPreferences, contents[2], phoneNumber);
                        Toast.makeText(context,"is OTP for "+phoneNumber+" verified"+Boolean.toString(sharedPreferences.getBoolean("is_"+phoneNumber+"_verified",false)),Toast.LENGTH_SHORT).show();
                        Log.d("Verification Complete",phoneNumber + " has been verified");
                        return;

                    } else{
                        Log.d("---------", "expected " + sharedPreferences.getString("User", "dhrushit") + " but found "+contents[0]);
//                        sendContent.sendSMS("The username phrase is not found in the database", senderNum, context);
                        Log.d("---------", "sms sent");
                        return;
                    }

//                    String s = "contents[2] = " + contents[2];
//                    Toast.makeText(context, s, Toast.LENGTH_LONG).show();

                    //--------------------------------------------------------------------------------------------

                    if (sharedPreferences.getBoolean(contents[2]+"_safe_numbers",false) == true){
                        if(sharedPreferences.getBoolean("is_"+phoneNumber.substring(3)+"_verified",false)==false){
                            replyMessage = "you are not on safe number list";
                            Toast.makeText(context, "you are not on safe number list", Toast.LENGTH_SHORT).show();
                            sendContent.sendSMS(replyMessage, senderNum,context );
                            return;
                        }
                    }
                    //--------------------------------------------------------------------------------------------


                    //check the message for the data asked
                    switch (contents[2]) {
                        case "ringer":
                            RingerMan ringerMan= new RingerMan(context);
                            replyMessage = ringerMan.manageRinger(contents[3]);

                        case "contacts":
                            replyMessage = ContactMan.getContacts(contents[3], context);
                            MainActivity.featureVariable = -1;

                            break;
                        case "location":
                            LocationMan locationMan = new LocationMan(context, senderNum);
                            locationMan.fetchLocation();

                            return;
                        case "notifications":
                            NotificationMan notificationMan = new NotificationMan();
                            replyMessage = notificationMan.fetchNotifications(contents[3], context);

                            break;
                        case "calllogs":
                            LogMan logMan = new LogMan(context);
                            logMan.getLogs(contents[3], context);

                            break;
                        case "battery":
                            replyMessage = BatteryMan.getBatteryStats(contents[3], context);

                            break;
                        case "messages":
                            replyMessage = MessagesMan.getMessages(contents[3], context);

                            break;
                        case "control":
                            replyMessage = ControllerMan.changeSystemSettings(contents[3], context);

                            break;
                        default:
                            return;
                    }


                }
                sendContent.sendSMS(replyMessage, senderNum,context );
            }
        }catch (Exception e){
            Log.d(MainActivity.TAG, "catching --------------------------------------------------------------------");
            Log.e("Sms Receiver","Exception Received " + e);
        }

    }




}
