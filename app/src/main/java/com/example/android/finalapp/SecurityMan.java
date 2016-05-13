package com.example.android.finalapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by dhrushit.s on 2/17/2016.
 */
public class SecurityMan {
    SharedPreferences sharedPreferences;

    public SecurityMan(Context context){c=context;};
    public static String error;
    Context c;

    public boolean checkPassPhrase(String feature, String content, Context context) {
        feature = feature.split("\r|\n|\r\n| |\\s+")[0];
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
       // sharedPreferences = c.getApplicationContext().getSharedPreferences("com.example.finalApp.PREFERENCE_FILE_KEY" ,0);

        sharedPreferences = c.getSharedPreferences("com.example.finalApp.PREFERENCE_FILE_KEY",0);

        Log.d("--------------","SharedPreferences received" + c.getString(R.string.preference_file_key));

        String currentFeature = "allow_"+feature+"_access";
//        sharedPreferences.
        boolean isExist = sharedPreferences.contains("allow_"+feature+"_access");
        boolean featureAllowance = sharedPreferences.getBoolean("allow_"+feature+"_access",false);
        boolean featureAllowance1 = sharedPreferences.getBoolean("allow_"+feature+"_access",true);

        if(sharedPreferences.getBoolean("allow_"+feature+"_access",false)){
            Log.d("-----------","allow "+ feature + " access true");
            boolean featureGlobalPassword = sharedPreferences.getBoolean(feature+"_global_password",false);
            if(sharedPreferences.getBoolean(feature+"_global_password",false)){
                Log.d("---------------------", feature + " globalpassword true");
                String globalPassword = sharedPreferences.getString("global_password","password");
                String cont = content;
                if(globalPassword.equals(content))
                    return true;
                else {
                    error = "wrong password";
                    Log.d("",content +"not same as"+ sharedPreferences.getString("global_password",""));
                    Log.d("-------","global password mismatch");
                    return false;
                }
            }
            else{
                if(sharedPreferences.getString(feature+"_custom_password_key",null).equals(content)){
                    Log.d("custom password","matched");
                    Log.d("returning","true");
                    return true;
                }

                else {
                    error = "wrong password";
                    Log.d("----------","custom password mismatch");
                    return false;
                }

            }
        }
        else{

            error = "feature false";
            Log.d(feature + " access","false");
            return false;
        }
    }
}
