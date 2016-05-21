package com.example.android.finalapp;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhrushit.s on 3/8/2016.
 */
public class SafeNumberListActivity extends AppCompatActivity {

    //Variables
    public static final int CONTACT_LOADER_ID = 78;
    Cursor phones, email;
    ListView listView;
    ArrayList<SelectUser> selectUsers;
    List<SelectUser> temp;
    ContentResolver resolver;
    SelectUserAdapter adapter;
    ViewHolder v;
    Context c=this;
    SharedPreferences sharedPreferences;
    final int SNLimit = 3;

    //Classes
    class SelectUser{
        String number;
        String Name;
        Boolean Checked=false;
        String ID;

        public SelectUser(){}

        public void setName(String s){
            this.Name = s;
        }

        public void setNumber(String s){
            this.number = s;
        }
        public void setChecked(Boolean b){
            this.Checked= b;
        }
        public void setID(String s){
            this.ID = s;
        }

        public String getName(){
            return this.Name;
        }

        public String getNumber(){
            return this.number;
        }
        public Boolean getChecked(){
            return this.Checked;
        }
        public String getID(){
            return this.ID;
        }


    }

    class LoadContact extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            if(phones != null) {
                Log.e("count", "" + phones.getCount());
                if (phones.getCount() == 0) {
                    //Toast.makeText(this, "No contacts in your contact list.", Toast.LENGTH_LONG).show();
                    Toast.makeText(c, "No contacts in your contact list.", Toast.LENGTH_SHORT).show();

                }

                while(phones.moveToNext()){
                    String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    SelectUser selectUser = new SelectUser();
                    selectUser.setName(name);
                    selectUser.setNumber(phoneNumber);
                    selectUser.setID(id);
                    selectUsers.add(selectUser);

                }
            }else {
                Log.e("Cursor Closed 1","--------------------");
            }
            return null;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPreExecute();
            adapter = new SelectUserAdapter(selectUsers,c);
            listView.setAdapter(adapter);
            listView.setFastScrollEnabled(true);
        }
    }

    public class SelectUserAdapter extends BaseAdapter{
        public List<SelectUser> data;
        private ArrayList<SelectUser> arrayList;
        Context c;
        ViewHolder viewHolder;

        public SelectUserAdapter(List<SelectUser> selectUsers,Context context){
            data = selectUsers;
            c = context;
            this.arrayList = new ArrayList<SelectUser>();
            this.arrayList.addAll(data);
        }
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if(view == null){
                LayoutInflater li = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = li.inflate(R.layout.safe_number_item,null);

                Log.e("Inside", "here--------------------------- In view1");
            }
            else{
                view = convertView;
            }


            v = new ViewHolder();
            v.check = (CheckBox) view.findViewById(R.id.safe_person_list_check);

//            v.check.setOnCheckedChangeListener();
            final SelectUser s = (SelectUser)data.get(position);
            v.check.setText(s.getName() + "\n\t"+ s.getNumber());
            v.check.setTag(position);
            v.check.setChecked(s.getChecked());
            view.setTag(s);
            return view;
        }
    }

    static class ViewHolder {
        ImageView imageView;
        TextView title, phone;
        CheckBox check;
    }

    //Methods

    public SafeNumberListActivity(){SelectUser selectUser;}


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.safe_number_list);

        sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.x = -10;
//        params.height = 1000;
//        params.width = 550;
//        params.y = -10;

        listView = (ListView) findViewById(R.id.safe_numbers_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CheckBox checkBox = (CheckBox)view.findViewById(R.id.safe_person_list_check);
                checkBox.setChecked(!checkBox.isChecked());
                SelectUser selectUser=selectUsers.get(position);
                selectUser.setChecked(checkBox.isChecked());
                Toast.makeText(getApplicationContext(),"check set to "+ Boolean.toString(checkBox.isChecked())+" for "+selectUser.getNumber(), Toast.LENGTH_SHORT).show();
                Log.d("safenumberactivity","here");
            }
        });
        selectUsers = new ArrayList<SelectUser>();
        resolver = this.getContentResolver();

//        Cursor cids;
//        cids = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,new String[]{ContactsContract.Contacts._ID},null,null,null);
//        String[] ids = new String[cids.getCount()];
//        if(cids.getCount()>0){
//            while (cids.moveToNext()){
//                ids[cids.getPosition()] = cids.getString(cids.getColumnIndex(ContactsContract.Contacts._ID));
//            }
//        }
//        phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", ids, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        LoadContact loadContact = new LoadContact();
        loadContact.execute();

        //
        // setUpCursorAdapter();

        //userAdapter = new UserAdapter(this,);

//        listView.setAdapter(userAdapter);

//        getSupportLoaderManager().initLoader(CONTACT_LOADER_ID,
//                new Bundle(),contactsLoader);



    }

    public void startVerification(View view){
        int SNIndex=1;
        ListView listView = (ListView) view.getRootView().findViewById(R.id.safe_numbers_list);
        for(int i=0;i<selectUsers.size();i++){
            SelectUser selectUser = selectUsers.get(i);
            Boolean b = selectUser.getChecked();
            if(b!=null) {
                if (b) {
                    String phno = selectUser.getNumber();
                    phno = phno.replaceAll(" ","");
                    String id = selectUser.getID();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String put = "SN" + Integer.toString(SNIndex);
                    editor.putString(put , phno);
                    editor.putBoolean("is_" + phno + "_verified", false);

                    String OTP = OTPVerification.sendVerificationCode(getApplicationContext(), phno);
                    put = "SNOTP_"+phno;
                    editor.putString(put, OTP);
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "Added OTP " + OTP + " to " + phno, Toast.LENGTH_SHORT).show();
                }
            }
        }

        finish();
    }



    /*public void toggleSN(View view){
        CheckBox checkBox = (CheckBox)view;
        Boolean b = checkBox.isChecked();



    }*/
}






