package com.example.android.finalapp;

import android.content.Context;
import android.media.AudioManager;
import android.provider.MediaStore;
import android.widget.Toast;

/**
 * Created by dhrushit.s on 5/17/2016.
 */
public class RingerMan {
    Context c;
    RingerMan(Context context){c=context;}
    AudioManager am;

    String manageRinger(String s){
        am = (AudioManager)c.getSystemService(Context.AUDIO_SERVICE);
        Toast.makeText(c, s, Toast.LENGTH_SHORT).show();
        String parts[]=s.split("\r\n|\r|\n|\\s+");
        switch (parts[0]){
            case "volume":
                switch (parts[1]){
                    case "max":
                        Toast.makeText(c, "Max Volume", Toast.LENGTH_SHORT).show();
                        am.setStreamVolume(AudioManager.STREAM_RING,am.getStreamMaxVolume(AudioManager.STREAM_RING),0);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC,am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),0);
                        am.setStreamVolume(AudioManager.STREAM_NOTIFICATION,am.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION),0);
                        am.setStreamVolume(AudioManager.STREAM_ALARM,am.getStreamMaxVolume(AudioManager.STREAM_ALARM),0);
                        break;
                    default:
                        Toast.makeText(c, "Volume" + parts[1], Toast.LENGTH_SHORT).show();
                        int volume = Integer.parseInt(parts[1]);
                        am.setStreamVolume(AudioManager.STREAM_RING,volume,0);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC,volume,0);
                        am.setStreamVolume(AudioManager.STREAM_NOTIFICATION,volume,0);
                        am.setStreamVolume(AudioManager.STREAM_ALARM,volume,0);
                }
                break;

            case "beep":
                am.setStreamVolume(AudioManager.STREAM_RING,am.getStreamMaxVolume(AudioManager.STREAM_RING),0);
                am.setStreamVolume(AudioManager.STREAM_MUSIC,am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),0);
                am.setStreamVolume(AudioManager.STREAM_NOTIFICATION,am.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION),0);
                am.setStreamVolume(AudioManager.STREAM_ALARM,am.getStreamMaxVolume(AudioManager.STREAM_ALARM),0);



        }
        return "";
    }
}
