package com.example.android.finalapp;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by dhrushit.s on 2/17/2016.
 */
public class ContactMan {

    private boolean allowFeature = false;
    private static String[] parts;

    public static String getContacts(String contents, Context context) {
        Uri contactUri = ContactsContract.Contacts.CONTENT_URI;
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Uri emailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        ContentResolver cr = context.getContentResolver();
        String[] projection_contact = null;
        String[] projection_email = null;
        String[] projection_phone = null;

        String selection_phone = "";
        String selection_email = "";

        String[] args;
        String[] arg_email;
        String[] arg_phone;

        ArrayList<String> Projection_email = new ArrayList<>();
        ArrayList<String> Projection_phone = new ArrayList<>();
        ArrayList<String> Args_email = new ArrayList<>();
        ArrayList<String> Args_phone = new ArrayList<>();

        boolean get_email=false;
        boolean get_phone=false;

        Cursor cursor1 = null;
        //-----------------------------------------------------------------------------------------

        String str = "";
        String contact_id="";
        ArrayList<String> Projection = new ArrayList<>();
        Projection.add(ContactsContract.Contacts._ID);
        ArrayList<String > Args = new ArrayList<>();
//        String[] args={};
        String SELECTION="";
        if(contents != null)
            parts = contents.split("\r\n|\r|\n| |:");

        if(parts == null){
            projection_contact = new String[]{ContactsContract.Contacts._ID,ContactsContract.Contacts.DISPLAY_NAME};
            cursor1 = cr.query(contactUri,projection_contact,null,null,null);
        }
        else{
            if(parts[0].equals("all")){
                projection_contact = new String[]{ContactsContract.Contacts._ID,ContactsContract.Contacts.DISPLAY_NAME};
                String[] arg = {"%%"};
                cursor1 = cr.query(contactUri,projection_contact,null,null,null);
                get_email = get_phone = true;
                Projection_email.add(ContactsContract.CommonDataKinds.Email.ADDRESS);
                Projection_phone.add(ContactsContract.CommonDataKinds.Phone.NUMBER);
                selection_email = ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ? ";
                selection_phone = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? ";
            }
            else if(parts[0].equals("add")){

            }
            else{
                projection_contact = new String[]{ContactsContract.Contacts._ID,ContactsContract.Contacts.DISPLAY_NAME};
                String[] arg = {"%"+parts[0]+"%"};
                cursor1 = cr.query(contactUri,projection_contact,ContactsContract.Contacts.DISPLAY_NAME + " LIKE ? ",arg,null);

            }

        }


        if(parts!=null && parts.length > 1 ){
            selection_email = ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ? ";
            selection_phone = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? ";






            for ( int i = 1 ; i<parts.length ; i++){
                switch (parts[i]){
                    case "email":
                        Projection_email.add(ContactsContract.CommonDataKinds.Email.ADDRESS);
                        get_email = true;
                        break;
                    case "number":
                        Projection_phone.add(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        get_phone = true;
                        break;
                    case "detailed":
                        Projection_phone.add(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        Projection_email.add(ContactsContract.CommonDataKinds.Email.ADDRESS);
                        get_phone = true;
                        get_email = true;
                        break;
                }
            }
        }
        else{
            selection_email = ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ? ";
            selection_phone = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? ";
            Projection_phone.add(ContactsContract.CommonDataKinds.Phone.NUMBER);
            Projection_email.add(ContactsContract.CommonDataKinds.Email.ADDRESS);
            get_phone = true;
            get_email = true;
        }

        Toast.makeText(context, "found " + cursor1.getCount() + " contacts", Toast.LENGTH_SHORT).show();
        projection_email = Projection_email.toArray(new String[Projection_email.size()]);
        projection_phone = Projection_phone.toArray(new String[Projection_phone.size()]);
//        arg_email = Args_email.toArray(new String[Args_email.size()]);
//        arg_phone = Args_email.toArray(new String[Args_email.size()]);

        if (cursor1.getCount() > 0) {

            while(cursor1.moveToNext()){
                contact_id = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID));

                str = str + cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                str = str + "\n";

                if(get_phone/* && Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0*/){
                    str = str + "\tPhone:\n";
                    arg_phone = new String[]{contact_id};
                    Cursor cursor_phone = cr.query(phoneUri,projection_phone,selection_phone,arg_phone,null);
                    while (cursor_phone.moveToNext()){
                        str = str + "\t\t"+ cursor_phone.getString(cursor_phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) + "\n";
                    }
                }
                if(get_email/* && Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0*/){
                    str = str + "\tEmail:\n";
                    arg_email = new String[]{contact_id};
                    Cursor cursor_email = cr.query(emailUri,projection_email,selection_email,arg_email,null);
                    while (cursor_email.moveToNext()){
                        str = str + "\t\t"+ cursor_email.getString(cursor_email.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)) + "\n";
                    }
                }


            }
            Toast.makeText(context, str, Toast.LENGTH_SHORT);


            return str;
        }


        return null;
    }
}

