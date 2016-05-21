package com.example.android.finalapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by dhrushit.s on 4/4/2016.
 */
public class GlobalSettingsMan extends AppCompatActivity{
    boolean sendViaMail,sendViaMessage;
    SharedPreferences sharedPreferences ;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar ab = getSupportActionBar();

        setContentView(R.layout.global_settings_layout);

        sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        sendViaMail = sharedPreferences.getBoolean("global_reply_message",false);
        sendViaMessage = sharedPreferences.getBoolean("global_reply_email",false);

        if(sendViaMessage)
            ((ImageView)findViewById(R.id.imageView6)).setImageResource(R.drawable.ic_communication_message);
        else
            ((ImageView)findViewById(R.id.imageView6)).setImageResource(R.drawable.ic_communication_message_gray);

        if(sendViaMail)
            ((ImageView)findViewById(R.id.imageView7)).setImageResource(R.drawable.ic_communication_email_blue);
        else
            ((ImageView)findViewById(R.id.imageView7)).setImageResource(R.drawable.ic_communication_email);


    }

    public void launchSafeNumberActivity(View view){
        Intent intent = new Intent(this.getApplicationContext(),SafeNumberListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop(){
        super.onStop();
        EditText editText = (EditText) findViewById(R.id.editText2);
        String gPass = editText.getText().toString();
        Log.d("Global_Settings","gPass="+gPass);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(!gPass.equals("") && gPass!=null) {
            editor.putString("global_password", gPass);
            Log.d("Global_Settings","gPass updated");
        }

        editor.putBoolean("global_reply_message",sendViaMessage);
        editor.putBoolean("global_reply_email",sendViaMail);

    }

    public void turnAllFeaturesOn(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for(int i=0;i<MainActivity.features.length;i++){
            editor.putBoolean("allow_"+MainActivity.featureString[i]+"_access",true);

            editor.commit();
//            Toast.makeText(GlobalSettingsMan.this, MainActivity.featureString[i]+" : "+sharedPreferences.getBoolean("allow_"+MainActivity.featureString[i]+"_access",false), Toast.LENGTH_SHORT).show();
        }

    }

    public void turnAllFeaturesOff(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for(int i=0;i<MainActivity.features.length;i++){
            editor.putBoolean("allow_"+MainActivity.features[i]+"_access",false);
            editor.commit();
        }
    }

    public void toggleSendViaSMS(View view) {
        ImageView imageView = (ImageView)view;
        sendViaMessage = !sendViaMessage;
        if(sendViaMessage)
            imageView.setImageResource(R.drawable.ic_communication_message);
        else
            imageView.setImageResource(R.drawable.ic_communication_message_gray);
    }

    public void toggleSendViaEmail(View view) {
        ImageView imageView = (ImageView) view;
        sendViaMail = !sendViaMail;
        if(sendViaMail)
            imageView.setImageResource(R.drawable.ic_communication_email_blue);
        else
            imageView.setImageResource(R.drawable.ic_communication_email);
    }
}
