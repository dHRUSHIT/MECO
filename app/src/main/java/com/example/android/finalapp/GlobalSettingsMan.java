package com.example.android.finalapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * Created by dhrushit.s on 4/4/2016.
 */
public class GlobalSettingsMan extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.global_settings_layout);

    }

    public void launchSafeNumberActivity(View view){
        Intent intent = new Intent(this.getApplicationContext(),SafeNumberListActivity.class);
        startActivity(intent);
    }


}
