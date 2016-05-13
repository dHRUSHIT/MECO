package com.example.android.finalapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class PreviousMainActivity extends AppCompatActivity  {
    boolean activityLayout=true;
    public static int featureVariable = -1;
    public static String[] featureString = {"battery",
            "contacts",
            "logs",
            "notifications",
            "messages",
            "location"};

    Context context = this;
    String replyMessage;
    //    public TextView textView = (TextView) findViewById(R.id.textView);
    public static String defaultSmsApp;
    final static String TAG = "finalApp";

    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //change all to false later
        editor.putBoolean("allow_battery_access",true);
        editor.putBoolean(getString(R.string.allow_contacts_access),true);
        editor.putBoolean(getString(R.string.allow_logs_access),true);
        editor.putBoolean(getString(R.string.allow_notification_access),true);
        editor.putBoolean(getString(R.string.allow_messages_access), true);
        editor.putBoolean(getString(R.string.allow_location_access), true);

        editor.putBoolean(getString(R.string.battery_global_password), true);
        editor.putBoolean(getString(R.string.contacts_global_password), true);
        editor.putBoolean(getString(R.string.logs_global_password), true);
        editor.putBoolean(getString(R.string.notifications_global_password), true);
        editor.putBoolean(getString(R.string.messages_global_password), true);
        editor.putBoolean(getString(R.string.location_global_password), true);

        editor.putBoolean(getString(R.string.battery_reply_mode_global), false);
        editor.putBoolean(getString(R.string.contacts_reply_mode_global), false);
        editor.putBoolean(getString(R.string.logs_reply_mode_global), false);
        editor.putBoolean(getString(R.string.notifications_reply_mode_global), false);
        editor.putBoolean(getString(R.string.messages_reply_mode_global), false);
        editor.putBoolean(getString(R.string.location_reply_mode_global), false);

        editor.putBoolean(getString(R.string.battery_safe_numbers), false);
        editor.putBoolean(getString(R.string.contacts_safe_numbers), false);
        editor.putBoolean(getString(R.string.logs_safe_numbers), false);
        editor.putBoolean(getString(R.string.notifications_safe_numbers), false);
        editor.putBoolean(getString(R.string.messages_safe_numbers), false);
        editor.putBoolean(getString(R.string.location_safe_numbers), false);

        editor.putString(getString(R.string.battery_custom_password_key), String.valueOf(R.string.battery_custom_password_value));
        editor.putString(getString(R.string.contacts_custom_password_key), String.valueOf(R.string.contacts_custom_password_value));
        editor.putString(getString(R.string.logs_custom_password_key), String.valueOf(R.string.logs_custom_password_value));
        editor.putString(getString(R.string.notifications_custom_password_key), String.valueOf(R.string.notifications_custom_password_value));
        editor.putString(getString(R.string.messages_custom_password_key), String.valueOf(R.string.messages_custom_password_value));
        editor.putString(getString(R.string.location_custom_password_key), String.valueOf(R.string.location_custom_password_value));

        editor.putBoolean("battery_reply_with_mail", false);
        editor.putBoolean("contacts_reply_with_mail", false);
        editor.putBoolean("logs_reply_with_mail", false);
        editor.putBoolean("notifications_reply_with_mail", false);
        editor.putBoolean("messages_reply_with_mail", false);
        editor.putBoolean("location_reply_with_mail", false);

        editor.putBoolean("battery_reply_with_message", false);
        editor.putBoolean("contacts_reply_with_message", false);
        editor.putBoolean("logs_reply_with_message", false);
        editor.putBoolean("notifications_reply_with_message",false);
        editor.putBoolean("messages_reply_with_message",false);
        editor.putBoolean("location_reply_with_message",false);

        editor.putString("global_password","password");
        editor.putBoolean(getString(R.string.global_reply_message),true);
        editor.putBoolean(getString(R.string.global_reply_email),true);

        editor.putInt("battery",0);
        editor.putInt("contacts",1);
        editor.putInt("log",2);
        editor.putInt("message",3);
        editor.putInt("notification",4);
        editor.putInt("location",5);

        editor.putString("User","Dhrushit");

        editor.commit();





        /*defaultSmsApp = Telephony.Sms.getDefaultSmsPackage(context);
        final String myPackageName = getPackageName();
        if(!Telephony.Sms.getDefaultSmsPackage(context).equals(myPackageName)){
            Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME,context.getPackageName());
            startActivity(intent);
        }*/
    }

    public void generateSMSBcast(View view){
        Intent intent = new Intent();
        intent.putExtra("extra","Extra");
        intent.setAction("android.provider.Telephony.SMS_RECEIVED");
        sendBroadcast(intent);
    }
    public void getBatteryStat(View view){
        EditText editText = (EditText)findViewById(R.id.editText);
        /*String temp=*/editText.setText("dhrushit\npassword\nbattery");/*getText().toString();
        replyMessage = BatteryMan.getBatteryStats(temp, context);
        Toast.makeText(MainActivity.this, replyMessage, Toast.LENGTH_SHORT).show();*/
    }
    public void getContacts(View view){
        EditText editText = (EditText)findViewById(R.id.editText);
        /*String temp=*/editText.setText("dhrushit\npassword\ncontacts");/*getText().toString();*/
            /*replyMessage = ContactMan.getContacts(temp, context);
        Toast.makeText(MainActivity.this, replyMessage, Toast.LENGTH_SHORT).show();*/
    }
    public void getLocation(View view){
        EditText editText = (EditText)findViewById(R.id.editText);
        editText.setText("dhrushit\npassword\nlocation");
        /*LocationMan locationMan = new LocationMan(context,"934923493");
        locationMan.fetchLocation(null, context);*/
//        Toast.makeText(MainActivity.this, replyMessage, Toast.LENGTH_SHORT).show();
    }
    public void getLogs(View view){
        /*LogMan logMan = new LogMan(context);

        logMan.getLogs(null, context);*/
        EditText editText = (EditText)findViewById(R.id.editText);
        editText.setText("dhrushit\npassword\nlogs");
//        Toast.makeText(MainActivity.this, replyMessage, Toast.LENGTH_SHORT).show();
    }
    public void getMessage(View view){/*
        replyMessage = MessagesMan.getMessages(null, context);
        Toast.makeText(MainActivity.this, replyMessage, Toast.LENGTH_SHORT).show();*/
        EditText editText = (EditText)findViewById(R.id.editText);
        editText.setText("dhrushit\npassword\nmessages");
    }

    public void getNotified(View view){
        NotificationMan notificationMan = new NotificationMan();
        replyMessage = notificationMan.fetchNotifications(null, context);
        Toast.makeText(PreviousMainActivity.this,replyMessage,Toast.LENGTH_SHORT).show();
    }
    public void changeUI(View view){
        RelativeLayout relativeLayout = (RelativeLayout)view.getParent();
        if(activityLayout) {
            setContentView(R.layout.activity_main2);
            activityLayout = false;
        }
        else {
            setContentView(R.layout.activity_main);
            activityLayout = true;
        }

    }

    public void setSettings(View view){
        Intent intent = new Intent(this,settingSelector.class);
        startActivityForResult(intent,0);
    }
    public void sendMessage(View view){
        SMSReceiverTesting smsReceiverTesting = new SMSReceiverTesting();
        EditText editText = (EditText)findViewById(R.id.editText);
        String temp=editText.getText().toString();
        smsReceiverTesting.onReceive(context,temp);
    }
}
