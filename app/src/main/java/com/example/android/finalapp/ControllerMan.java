package com.example.android.finalapp;

import android.content.Context;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;

/**
 * Created by dhrushit.s on 2/17/2016.
 */
public class ControllerMan {

    private boolean allowFeature = false;
    public static String fetchNotifications(String contents, Context context) {
        return null;
    }

    public static String changeSystemSettings(String content, Context context) {

        return null;
    }
    private WifiManager wifiManager;

    public void turnOnWifi(Context context) {

        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if(wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(false);
        }else{
            wifiManager.setWifiEnabled(true);
        }

    }
    public  void changeVolume(Context context)
    {
        AudioManager am =
                (AudioManager) context.getSystemService(context.AUDIO_SERVICE);

        am.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                0);
    }

}
