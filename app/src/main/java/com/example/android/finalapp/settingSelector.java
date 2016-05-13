package com.example.android.finalapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dhrushit.s on 2/26/2016.
 */
public class settingSelector extends Activity {
    View view;
    CheckBox checkBox, checkBox2;
    Switch aSwitch;
    boolean passwordVisibilityVariable = false;
    ImageView passwordVisibility;

    SharedPreferences sharedPreferences;

    Boolean allowFeature = true;
    Boolean useGlobalPassword = true;
    Boolean globalReplyMode = true;
    Boolean replyWithMessage = false;
    Boolean replyWithMail = false;
    String customPassword;

    String[] feature = {"contacts", "logs", "location", "battery", "notification", "message", "control"};
    String feature_current;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("setup for...");

        sharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int color = Color.rgb(127, 127, 127);
        setTitleColor(color);
        setContentView(R.layout.dialog_layout);

        view = View.inflate(this, R.layout.settings_layout, null);

        passwordVisibility = (ImageView) view.findViewById(R.id.imageView);
        checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        checkBox2 = (CheckBox) view.findViewById(R.id.checkBox2);
        aSwitch = (Switch) view.findViewById(R.id.on_off_feature);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    allowFeature = false;
                    setDefaultSettings(feature_current);

                    finish();
                }
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    useGlobalPassword = true;
                    EditText editText = (EditText) view.findViewById(R.id.editText);
                    ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
                    ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
                    editText.setEnabled(false);
//                    editText.animate().translationY(-editText.getHeight()).alpha(0.0f).setDuration(300);

//                    editText.animate().translationY(editText.getHeight()).alpha(0.0f).setDuration(300);
                    editText.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                } else {
                    useGlobalPassword = false;
                    EditText editText = (EditText) view.findViewById(R.id.editText);
                    ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
                    ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
//                    editText.animate().translationY(0).alpha(1.0f).setDuration(300);
                    /*editText.setFocusable(true);
                    editText.setFocusableInTouchMode(true);*/
                    editText.setEnabled(true);
                    editText.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    globalReplyMode = true;
//                    ImageView imageView1 = (ImageView) findViewById(R.id.imageView);
                    ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
                    ImageView imageView3 = (ImageView) findViewById(R.id.imageView3);

//                    imageView1.setVisibility(View.GONE);
                    imageView2.setVisibility(View.GONE);
                    imageView3.setVisibility(View.GONE);
                } else {
                    globalReplyMode = false;
//                    ImageView imageView1 = (ImageView) findViewById(R.id.imageView);
                    ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
                    ImageView imageView3 = (ImageView) findViewById(R.id.imageView3);

//                    imageView1.setVisibility(View.VISIBLE);
                    imageView2.setVisibility(View.VISIBLE);
                    imageView3.setVisibility(View.VISIBLE);
                }
            }
        });
        final GridView gridview = (GridView) findViewById(R.id.dialogGridview);
        gridview.setAdapter(new DialogImageAdapter(this,sharedPreferences));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(), "" + position,
                        Toast.LENGTH_SHORT).show();

                feature_current = feature[position];
                RelativeLayout relativeLayout = (RelativeLayout) gridview.getParent();
                relativeLayout.removeView(gridview);
                gridview.invalidate();
                setTitle("set up the variables ...........");
                relativeLayout.addView(view);


                //setting up variables from sharedPreferences

                useGlobalPassword = true;
                globalReplyMode = true;
                replyWithMessage = false;
                replyWithMail = false;
                customPassword = sharedPreferences.getString(feature_current + "_custom_password_key", null);

            }
        });


    }

    public void onCheckClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        if (checked == true) {
            view.findViewById(R.id.editText).setEnabled(false);

        } else {
            view.findViewById(R.id.editText).setEnabled(true);

        }
    }

    public void mailReplyToggle(View view1) {

        ImageView imageView = (ImageView) view1;
        if (!replyWithMail) {
            imageView.setImageResource(R.drawable.ic_communication_email_green);
            replyWithMail = true;
        } else {
            imageView.setImageResource(R.drawable.ic_communication_email);
            replyWithMail = false;
        }
    }

    public void messageReplyToggle(View view1) {
        ImageView imageView = (ImageView) view1;

        if (!replyWithMessage) {
            imageView.setImageResource(R.drawable.ic_communication_message);
            replyWithMessage = true;
        } else {
            imageView.setImageResource(R.drawable.ic_communication_message_gray);
            replyWithMessage = false;
        }
    }

    public void passwordVisibilityToggle(View view1/*view returned here is Image view and not the parent's view*/) {
        if (passwordVisibilityVariable) {

            EditText editText = (EditText) view.findViewById(R.id.editText);
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            editText.setSelection(editText.getText().length());
            passwordVisibility.setImageResource(R.drawable.ic_action_visibility_gray);
            passwordVisibilityVariable = false;
        } else {
            EditText editText = (EditText) view.findViewById(R.id.editText);
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            editText.setSelection(editText.getText().length());
            passwordVisibility.setImageResource(R.drawable.ic_action_visibility_green);
            passwordVisibilityVariable = true;
        }
    }

    public void saveTempSettings(View view1) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(!useGlobalPassword) {
            editor.putString(feature_current + "_custom_password_key", ((EditText) view.findViewById(R.id.editText)).getText().toString());
            Log.d("------------------", "custom password for " + feature_current + "set to " + ((EditText) view.findViewById(R.id.editText)).getText().toString());
        }
        else{
            editor.putString(feature_current + "_custom_password_key", sharedPreferences.getString("global_password",null));
            Log.d("------------------", "custom password for " + feature_current + "set to " + sharedPreferences.getString("global_password",null));
        }

        editor.putBoolean(feature_current + "_global_password", useGlobalPassword);
        Log.d("---------", feature_current + " global password = " + useGlobalPassword);

        editor.putBoolean(feature_current + "_reply_mode_global", globalReplyMode);
        Log.d("---------", feature_current + " global reply mode = " + globalReplyMode);

        if(globalReplyMode) {
            editor.putBoolean(feature_current + "_reply_with_mail", sharedPreferences.getBoolean("global_reply_email",false));
            Log.d("---------", feature_current + " reply with mail = " + true);
            editor.putBoolean(feature_current + "_reply_with_message", sharedPreferences.getBoolean("global_reply_message",false));
            Log.d("---------", feature_current + " reply with message = " + true);
        }
        else{
            editor.putBoolean(feature_current + "_reply_with_mail", replyWithMail);
            Log.d("---------", feature_current + " reply with mail = " + replyWithMail);
            editor.putBoolean(feature_current + "_reply_with_message", replyWithMessage);
            Log.d("---------", feature_current + " reply with message = " + replyWithMessage);
        }
        editor.putBoolean("allow_" + feature_current + "_access", true);
        Log.d("---------", feature_current + " allowed");
        editor.commit();
        Log.d("---------", feature_current + "--------------------------------commit done------------------------------------------------");
        setTitle(null);
        setContentView(R.layout.all_done);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        }, 500);
        Toast.makeText(this, "All Done", Toast.LENGTH_SHORT).show();

    }

    public void setDefaultSettings(String s) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("allow_"+s+"_access",allowFeature);
        editor.putBoolean(s + "_global_password", true);
        editor.putBoolean(s + "_reply_with_message", true);
        editor.putBoolean(s + "_reply_with_mail", true);
        editor.putString(s + "_custom_password_key", null);
        editor.commit();

    }
}
