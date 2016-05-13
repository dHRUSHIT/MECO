package com.example.android.finalapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Random;

/**
 * Created by dhrushit.s on 4/4/2016.
 */
public class OTPVerification {

    SharedPreferences sharedPreferences;
    Context context;


    public static String sendVerificationCode(Context context,String phoneNumber){
        Random r = new Random();
        int i1 = r.nextInt(9000 - 1) + 999;


        String OTP = String.valueOf(i1);
        String messageBody = "Please reply to this message with following\nAPPOTP : "+OTP;
        sendContent.sendSMS(messageBody,phoneNumber,context);

        return OTP;
    }

    public static void verifyOTP(SharedPreferences s,String OTPReply,String phno){
        String phone = phno.substring(3);
        String get = "SNOTP_" + phone;
        String OTP_Local = s.getString(get, null);
        Log.d("Other SP",s.getString("SN1",""));
        Log.d("Other SP",s.getString("global_password",""));
        Log.d("Other SP","dummy log");
        if(OTP_Local == null)
            Log.d("Other SP","Local otp is null------------------------------------");
        SharedPreferences.Editor editor = s.edit();
        if(OTP_Local.equals(OTPReply)){
            editor.putBoolean("is_"+phno+"_verified",true);

        }
        editor.commit();

    }
}
