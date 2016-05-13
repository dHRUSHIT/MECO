package com.example.android.finalapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by dhrushit.s on 2/23/2016.
 */
public class DialogImageAdapter extends BaseAdapter {
    private Context mContext;
    SharedPreferences sharedPreferences;

    public int castBooleanToInt(boolean b){
        return (b)?1:0;
    }
    public DialogImageAdapter(Context c,SharedPreferences s) {
        mContext = c;
        sharedPreferences = s;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    ImageView imageView;

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        View view = null;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            int isOnOffInt;
            view = View.inflate(mContext,R.layout.dialog_grid_items,null);
            imageView = (ImageView)view.findViewById(R.id.dialogItemImage);
            if(position<6) {
                boolean isOnOff = sharedPreferences.getBoolean("allow_" + MainActivity.featureString[position] + "_access", false);
                isOnOffInt = castBooleanToInt(isOnOff);
            }
            else
                isOnOffInt = 0;
            imageView.setImageResource(mThumbIds[position][isOnOffInt]);

        } else {
            view = convertView;
        }

        /*imageView.setImageResource(mThumbIds[position]);*/
        return view;
    }

    // references to our images
    public static Integer[][] mThumbIds =  {
        {R.drawable.ic_social_person_gray,R.drawable.ic_social_person},
        {R.drawable.ic_communication_phone_gray,R.drawable.ic_communication_call_green},
        {R.drawable.ic_communication_location_on_gray,R.drawable.ic_communication_location_on_red},
        {R.drawable.ic_device_battery_std_gray,R.drawable.ic_device_battery_std},
        {R.drawable.ic_alert_error_gray,R.drawable.ic_alert_error},
        {R.drawable.ic_communication_message_gray,R.drawable.ic_communication_message},
        {R.drawable.ic_social_share_green,R.drawable.ic_social_share_green},
            {R.drawable.ic_action_settings,R.drawable.ic_action_settings}


    };


    /*private int[2][2] mArray = { {1,2},
                                 {3,4}
    };*/
}
