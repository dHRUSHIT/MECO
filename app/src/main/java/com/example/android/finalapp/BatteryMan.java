package com.example.android.finalapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

/**
 * Created by dhrushit.s on 2/17/2016.
 */
public class BatteryMan {

    private boolean allowFeature = false;

    public static String getBatteryStats(String contents, Context context) {


        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent intent = context.registerReceiver(null, ifilter);
        int  health= intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
        int  icon_small= intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL, 0);
        int  level= intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        int  plugged= intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
        boolean  present= intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT);
        int  scale= intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
        int  status= intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
        String  technology= intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
        int  temperature= intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
        int  voltage= intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);
        String ret = "\tBATTERY STATUS\n\t======= ========\n";

        String[] parts;
        if(contents != null && !contents.isEmpty() )
            parts = contents.split("\r|\n|\r\n");
        else{
            ret = ret+ "health : " + health + "\nplugged : " + plugged + "\npresent : " + present + "\nscale : "
                    + scale + "\nstatus : " + status + "\ntechnology : " + technology + "\ntemperature : " + temperature
                    + "\nvoltage : " + voltage+ "\nlevel : " + level;
            return ret;
        }

        for(int i =0 ;i<parts.length;i++){
            parts[i]=parts[i].trim();
            switch (parts[i]){
                case "all":
                    ret = ret+ "health : " + health + "\nplugged : " + plugged + "\npresent : " + present + "\nscale : "
                            + scale + "\nstatus : " + status + "\ntechnology : " + technology + "\ntemperature : " + temperature
                            + "\nvoltage : " + voltage+ "\nlevel : " + level;
                    return ret;
                case "level":
                    ret = ret+"battery level : " + level;
                    break;
                case "plugged":
                    ret = ret+"\nplugged : " ;
                    if (plugged == 0)
                        ret = ret+"no";
                    else if (plugged == 1)
                        ret = ret + "yes\nplugged to charger";
                    else if (plugged == 2)
                        ret = ret + "yes\nplugged to pc(or a low power source)";
                    break;
                case "temp":
                    ret = ret+"\ntemperature : " + temperature;
                    break;
                default:
                    ret = ret+ "health : " + health + "\nplugged : " + plugged + "\npresent : " + present + "\nscale : "
                            + scale + "\nstatus : " + status + "\ntechnology : " + technology + "\ntemperature : " + temperature
                            + "\nvoltage : " + voltage+ "\nlevel : " + level;
                    return ret;

            }
        }
        /*ret = ret+ "health : " + health + "\nplugged : " + plugged + "\npresent : " + present + "\nscale : "
                + scale + "\nstatus : " + status + "\ntechnology : " + technology + "\ntemperature : " + temperature
                + "\nvoltage : " + voltage+ "\nlevel : " + level;*/
        return ret;
    }


}
