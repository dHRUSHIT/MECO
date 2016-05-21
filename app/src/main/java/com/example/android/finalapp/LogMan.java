package com.example.android.finalapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.support.v4.app.ActivityCompat.requestPermissions;
import static android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale;

/**
 * Created by dhrushit.s on 2/17/2016.
 */
public class LogMan {
    private Cursor cursor = null;
    private ContentResolver contentResolver;
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 1;
    private Context context1;

    public LogMan(Context context) {
        context1 = context;
    }

    public void getLogs(String string, Context context) throws ParseException {

        contentResolver = context1.getContentResolver();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            getPermissionToReadContactLogs();
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        String[] projectionFields = new String[]{CallLog.Calls._ID, CallLog.Calls.CACHED_NAME,CallLog.Calls.CACHED_NORMALIZED_NUMBER,CallLog.Calls.DATE};

        Calendar c = Calendar.getInstance();
        c.add(c.DATE,-1);


        SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
//        String today = curFormater.format(String.valueOf(c.getTime()));
        cursor = contentResolver.query(CallLog.Calls.CONTENT_URI, null, CallLog.Calls.DATE + ">=?", new String[] { String.valueOf(c.getTime())}, null);
        Log.d("Date", String.valueOf((c.getTime())));

        String toast="";
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            int numberIdx = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            String number = cursor.getString(numberIdx);
            toast = toast + number + "\n";


        }
        Toast.makeText(context, "cur count = "+ cursor.getCount(), Toast.LENGTH_SHORT).show();
        Toast.makeText(context,""+toast ,Toast.LENGTH_LONG).show();

    }
    private static final int permissionCheck = 1;
    @TargetApi(Build.VERSION_CODES.M)
    private void getPermissionToReadContactLogs() {
        if (ContextCompat.checkSelfPermission(context1, Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {

            // The permission is NOT already granted.
            // Check if the user has been asked about this permission already and denied
            // it. If so, we want to give more explanation about why the permission is needed.
            if (shouldShowRequestPermissionRationale((Activity)context1,
                    Manifest.permission.READ_CALL_LOG)) {
                // Show our own UI to explain to the user why we need to read the contacts
                // before actually requesting the permission and showing the default UI
            }

            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI
            requestPermissions((Activity)context1,new String[]{Manifest.permission.READ_CALL_LOG},
                    permissionCheck);
        }
    }

//    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request

        if (requestCode == READ_CONTACTS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context1, "Read Contacts permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context1, "Read Contacts permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
