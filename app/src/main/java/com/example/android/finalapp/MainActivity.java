package com.example.android.finalapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.media.ToneGenerator;
import android.net.MailTo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity  {

    //Variables------------------------------------------------------------------
    boolean activityLayout=true;
    boolean isPlaying = false;
    public static int featureVariable = -1;
    public static String[] featureString = {"contacts",
            "logs",
            "location",
            "battery",
            "notification",
            "messages","ringer"};

    Context context = this;
    String replyMessage;
//    public TextView textView = (TextView) findViewById(R.id.textView);
    public static String defaultSmsApp;
    final static String TAG = "finalApp";

    SharedPreferences sharedPreferences;
    ImageView imageView;
    TextView textView;
    public static String features[] = {"contacts","logs","location","battery","notification","messages","ringer"};





    //Methods---------------------------------------------------------------------------
    @Override
        protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);

        File f = new File(
                "/data/data/com.example.android.finalapp/shared_prefs/"+getString(R.string.preference_file_key)+".xml");
        if (f.exists()) {
            Log.d("TAG", "SharedPreferences Name_of_your_preference : exist");
        }
        else {
            setAllSPValues();
            Log.d("TAG", "Setup default preferences");

        }

        sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);





//---------------------------------------------------------------------------------

        final GridView gridview = (GridView) findViewById(R.id.dialogGridview);
        gridview.setAdapter(new DialogImageAdapter(this,sharedPreferences));
        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "Long Press", Toast.LENGTH_SHORT).show();
                if(position>6){
                    Toast.makeText(MainActivity.this, "No action defined", Toast.LENGTH_SHORT).show();
                    return false;
                }
                Intent intent = new Intent(context, SetSettings.class);
                intent.putExtra("position", position);
                startActivityForResult(intent, 1);

                if(position == 2)
                    Toast.makeText(MainActivity.this, "Keep GPS on to use this feature", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 8) {
                    Intent intent = new Intent(context,GlobalSettingsMan.class);
                    startActivity(intent);
                }else if (position != 7){
                    String current_feature = "allow_" + features[position] + "_access";
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Boolean isOn = sharedPreferences.getBoolean(current_feature, false);
                    if (isOn) {
                        editor.putBoolean(current_feature, false);
                        editor.commit();
                        Log.d(TAG, current_feature + " set to " + Boolean.toString(sharedPreferences.getBoolean(current_feature, false)));

                        Toast.makeText(context, features[position] + " turned off", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(context, "shared preference value = "+Boolean.toString(sharedPreferences.getBoolean(current_feature,false)), Toast.LENGTH_SHORT).show();

                        imageView = (ImageView) view.findViewById(R.id.dialogItemImage);
                        imageView.setImageResource(DialogImageAdapter.mThumbIds[position][0]);
                        textView = (TextView) view.findViewById(R.id.feature_description_text);
                        textView.setTextColor(Color.parseColor("#808080"));

                    }
                    else
                    {
                        editor.putBoolean(current_feature, true);
                        editor.commit();
                        Log.d(TAG, current_feature + " set to " + Boolean.toString(sharedPreferences.getBoolean(current_feature, false)));

                        Toast.makeText(context, features[position] + " turned on", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(context, "shared preference value = "+Boolean.toString(sharedPreferences.getBoolean(current_feature,false)), Toast.LENGTH_SHORT).show();
                        imageView = (ImageView) view.findViewById(R.id.dialogItemImage);
                        imageView.setImageResource(DialogImageAdapter.mThumbIds[position][1]);
                        textView = (TextView) view.findViewById(R.id.feature_description_text);
                        textView.setTextColor(Color.parseColor("#33b5e5"));
                        if(features[position] == "location")
                            Toast.makeText(MainActivity.this, "Keep GPS on to use this feature", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });


        activityLayout = false;


        //---------------------------------------------------------






        /*defaultSmsApp = Telephony.Sms.getDefaultSmsPackage(context);
        final String myPackageName = getPackageName();
        if(!Telephony.Sms.getDefaultSmsPackage(context).equals(myPackageName)){
            Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME,context.getPackageName());
            startActivity(intent);
        }*/
    }

    void setAllSPValues(){

        sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //change all to false later

        //strings
        editor.putString(getString(R.string.battery_custom_password_key), String.valueOf(R.string.battery_custom_password_value));
        editor.putString(getString(R.string.contacts_custom_password_key), String.valueOf(R.string.contacts_custom_password_value));
        editor.putString(getString(R.string.logs_custom_password_key), String.valueOf(R.string.logs_custom_password_value));
        editor.putString(getString(R.string.notifications_custom_password_key), String.valueOf(R.string.notifications_custom_password_value));
        editor.putString(getString(R.string.messages_custom_password_key), String.valueOf(R.string.messages_custom_password_value));
        editor.putString(getString(R.string.location_custom_password_key), String.valueOf(R.string.location_custom_password_value));
        editor.putString(getString(R.string.ringer_custom_password_key), String.valueOf(R.string.location_custom_password_value));

        editor.putString("global_password", "password");

        editor.putString("User","Dhrushit");

        editor.putString("SN1","9599444125");

        //booleans
        editor.putBoolean("allow_battery_access", false);
        editor.putBoolean(getString(R.string.allow_contacts_access), false);
        editor.putBoolean(getString(R.string.allow_logs_access), false);
        editor.putBoolean(getString(R.string.allow_notification_access), false);
        editor.putBoolean(getString(R.string.allow_messages_access), false);
        editor.putBoolean(getString(R.string.allow_location_access), false);
        editor.putBoolean(getString(R.string.allow_ringer_access), false);


        editor.putBoolean(getString(R.string.battery_global_password), true);
        editor.putBoolean(getString(R.string.contacts_global_password), true);
        editor.putBoolean(getString(R.string.logs_global_password), true);
        editor.putBoolean(getString(R.string.notifications_global_password), true);
        editor.putBoolean(getString(R.string.messages_global_password), true);
        editor.putBoolean(getString(R.string.location_global_password), true);
        editor.putBoolean(getString(R.string.ringer_global_password), true);

        editor.putBoolean(getString(R.string.battery_reply_mode_global), false);
        editor.putBoolean(getString(R.string.contacts_reply_mode_global), false);
        editor.putBoolean(getString(R.string.logs_reply_mode_global), false);
        editor.putBoolean(getString(R.string.notifications_reply_mode_global), false);
        editor.putBoolean(getString(R.string.messages_reply_mode_global), false);
        editor.putBoolean(getString(R.string.location_reply_mode_global), false);
        editor.putBoolean(getString(R.string.ringer_reply_mode_global), false);

        editor.putBoolean(getString(R.string.battery_safe_numbers), false);
        editor.putBoolean(getString(R.string.contacts_safe_numbers), false);
        editor.putBoolean(getString(R.string.logs_safe_numbers), false);
        editor.putBoolean(getString(R.string.notifications_safe_numbers), false);
        editor.putBoolean(getString(R.string.messages_safe_numbers), false);
        editor.putBoolean(getString(R.string.location_safe_numbers), false);
        editor.putBoolean(getString(R.string.ringer_safe_numbers), false);

        editor.putBoolean("battery_reply_with_mail", false);
        editor.putBoolean("contacts_reply_with_mail", false);
        editor.putBoolean("logs_reply_with_mail", false);
        editor.putBoolean("notifications_reply_with_mail", false);
        editor.putBoolean("messages_reply_with_mail", false);
        editor.putBoolean("location_reply_with_mail", false);
        editor.putBoolean("ringer_reply_with_mail", false);

        editor.putBoolean("battery_reply_with_message", false);
        editor.putBoolean("contacts_reply_with_message", false);
        editor.putBoolean("logs_reply_with_message", false);
        editor.putBoolean("notifications_reply_with_message", false);
        editor.putBoolean("messages_reply_with_message", false);
        editor.putBoolean("location_reply_with_message", false);
        editor.putBoolean("ringer_reply_with_message", false);

        editor.putBoolean(getString(R.string.global_reply_message), true);
        editor.putBoolean(getString(R.string.global_reply_email), true);

        editor.putBoolean("SNFunctionality",true);

        //ints
        editor.putInt("battery",0);
        editor.putInt("contacts",1);
        editor.putInt("log",2);
        editor.putInt("message", 3);
        editor.putInt("notification", 4);
        editor.putInt("location",5);
        editor.putInt("ringer",5);



        editor.commit();


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
        Toast.makeText(MainActivity.this,replyMessage,Toast.LENGTH_SHORT).show();
    }
    public void changeUI(View view){
//        LinearLayout linearLayout = (LinearLayout)view.getParent();
        if(activityLayout) {
            setContentView(R.layout.dialog_layout);
            //UI
            final GridView gridview = (GridView) findViewById(R.id.dialogGridview);
            gridview.setAdapter(new DialogImageAdapter(this,sharedPreferences));
            gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(context, "Long Press", Toast.LENGTH_SHORT).show();
                    if(position>6){
                        Toast.makeText(MainActivity.this, "No action defined", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    Intent intent = new Intent(context, SetSettings.class);
                    intent.putExtra("position", position);
                    startActivityForResult(intent, 1);

                    if(position == 2)
                        Toast.makeText(MainActivity.this, "Keep GPS on to use this feature", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(position == 8) {
                        Intent intent = new Intent(context,GlobalSettingsMan.class);
                        startActivity(intent);
                    }else if (position != 7){
                        String current_feature = "allow_" + features[position] + "_access";
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Boolean isOn = sharedPreferences.getBoolean(current_feature, false);
                         if (isOn) {
                            editor.putBoolean(current_feature, false);
                            editor.commit();
                            Log.d(TAG, current_feature + " set to " + Boolean.toString(sharedPreferences.getBoolean(current_feature, false)));

                             Toast.makeText(context, current_feature + " turned off", Toast.LENGTH_SHORT).show();
                             Toast.makeText(context, "shared preference value = "+Boolean.toString(sharedPreferences.getBoolean(current_feature,false)), Toast.LENGTH_SHORT).show();

                            imageView = (ImageView) view.findViewById(R.id.dialogItemImage);
                            imageView.setImageResource(DialogImageAdapter.mThumbIds[position][0]);
                             textView = (TextView) view.findViewById(R.id.feature_description_text);
                            textView.setTextColor(Color.parseColor("#808080"));

                        }
                        else
                        {
                            editor.putBoolean(current_feature, true);
                            editor.commit();
                            Log.d(TAG, current_feature + " set to " + Boolean.toString(sharedPreferences.getBoolean(current_feature, false)));

                            Toast.makeText(context, current_feature + " turned on", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(context, "shared preference value = "+Boolean.toString(sharedPreferences.getBoolean(current_feature,false)), Toast.LENGTH_SHORT).show();
                            imageView = (ImageView) view.findViewById(R.id.dialogItemImage);
                            imageView.setImageResource(DialogImageAdapter.mThumbIds[position][1]);
                            textView = (TextView) view.findViewById(R.id.feature_description_text);
                            textView.setTextColor(Color.parseColor("#33b5e5"));
                            if(current_feature == "allow_location_access")
                                Toast.makeText(MainActivity.this, "Keep GPS on to use this feature", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });


            activityLayout = false;
        } else {
            setContentView(R.layout.activity_main);
            activityLayout = true;
        }

    }

    public void setSettings(View view){
        Intent intent = new Intent(this,settingSelector.class);
        startActivityForResult(intent, 0);
    }
    public void sendMessage(View view){
        SMSReceiverTesting smsReceiverTesting = new SMSReceiverTesting();
        EditText editText = (EditText)findViewById(R.id.editText);
        String temp=editText.getText().toString();
        smsReceiverTesting.onReceive(context, temp);
    }

//    public void playsound(View view) throws IllegalArgumentException,
//            SecurityException,
//            IllegalStateException,
//            IOException {
//        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        MediaPlayer mMediaPlayer = new MediaPlayer();
//        mMediaPlayer.setDataSource(context, soundUri);
//        final AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//
//        if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
//            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
//            mMediaPlayer.setLooping(true);
//            mMediaPlayer.prepare();
//            mMediaPlayer.start();
//        }
//    }
    public void playsound(View view){
        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        int T=10;
        while(T!=0){
        toneGen1.startTone(ToneGenerator.MAX_VOLUME);T--;
        }
    }

    public int castBooleanToInt(boolean b){
        return (b)?1:0;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null)
            return;
        // check if the request code is same as what is passed  here it is 2
        if(requestCode== 1)
        {
            int pos=data.getIntExtra("position", 1);
            if(pos>6)
                return;
            /*String current_feature = "allow_" + features[pos] + "_access";
            imageView = (ImageView) findViewById(R.id.dialogItemImage);
            imageView.setImageResource(DialogImageAdapter.mThumbIds[pos][castBooleanToInt(sharedPreferences.getBoolean(current_feature, false))]);
            Log.d(TAG,"icon changed------------------------");*/
            updateView(pos);
        }
    }

    private void updateView(int index){
        //final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
          //      .findViewById(R.id.dialogGridview)).getChildAt(index);
        View view = ((ViewGroup) this.findViewById(R.id.dialogGridview)).getChildAt(index);
        if(view == null)
            return;
        ImageView imageView = (ImageView) view.findViewById(R.id.dialogItemImage);
        imageView.setImageResource(DialogImageAdapter.mThumbIds[index][castBooleanToInt(sharedPreferences.getBoolean("allow_"+features[index]+"_access", false))]);
    }


    public void startGlobalSettingsActivity(View view) {
        Intent intent = new Intent(context,GlobalSettingsMan.class);
        startActivity(intent);

    }

    @Override
    protected void onResume(){
        super.onResume();
        for(int i=0;i<7;i++)
            updateView(i);
    }
    @Override
    protected void onPause(){
        super.onPause();
        Toast.makeText(MainActivity.this, "Pausing Main Activity", Toast.LENGTH_SHORT).show();
    }

    public void ring(View view) {
        EditText editText = (EditText)findViewById(R.id.editText);
        /*String temp=*/editText.setText("dhrushit\npassword\nringer volume max");
    }
}
