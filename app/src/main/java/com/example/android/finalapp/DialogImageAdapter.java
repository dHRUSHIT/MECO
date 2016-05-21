package com.example.android.finalapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        TextView textView;
        View view = null;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes

            view = View.inflate(mContext,R.layout.dialog_grid_items,null);
            imageView = (ImageView)view.findViewById(R.id.dialogItemImage);
            textView = (TextView)view.findViewById(R.id.feature_description_text);

            boolean isOn = false;
            isOn = sharedPreferences.getBoolean("allow_"+MainActivity.featureString[position]+"_access",false);
            int intIsOn = castBooleanToInt(isOn);
            imageView.setImageResource(mThumbIds[position][intIsOn]);
            textView.setText(MainActivity.featureString[position]);


        } else {
            view = convertView;
        }

        /*imageView.setImageResource(mThumbIds[position]);*/
        return view;
    }

    // references to our images
    public static Integer[][] mThumbIds =  {
        {R.drawable.ic_social_person_gray,R.drawable.ic_social_person_lblue},
        {R.drawable.ic_communication_phone_gray,R.drawable.ic_communication_call_blue},
        {R.drawable.ic_communication_location_off,R.drawable.ic_communication_location_on_blue},
        {R.drawable.ic_device_battery_std_gray,R.drawable.ic_device_battery_charging_full_blue},
        {R.drawable.ic_social_notifications_grey,R.drawable.ic_social_notifications_on_blue},
        {R.drawable.ic_communication_message_gray,R.drawable.ic_communication_message},
            {R.drawable.ic_av_volume_off_grey,R.drawable.ic_av_volume_up}
//        {R.drawable.ic_social_share,R.drawable.ic_social_share},
//            {R.drawable.ic_action_settings,R.drawable.ic_action_settings}


    };


    /*private int[2][2] mArray = { {1,2},
                                 {3,4}
    };*/

}
