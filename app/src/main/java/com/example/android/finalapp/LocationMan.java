package com.example.android.finalapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.Locale;

/**
 * Created by dhrushit.s on 2/17/2016.
 */
public class LocationMan implements LocationListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {

    //Variables

    private GoogleApiClient mGoogleApiClient;
    private String latitude;
    private String longitude;
    private String returnLocation;
    LocationRequest mLocationRequest;
    Location mCurrentLocation;
    Location mLastLocation;
    private volatile boolean locationFetched = false;
    private volatile boolean locationFetchedFail = false;
    private static final String TAG = "LocationActivity";
    private Context context1;
    private String receiverNumber;

    //METHODS

    public LocationMan(Context context, String senderNum) {
//        turnGPSOn(context);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();
        receiverNumber = senderNum;
        context1 = context;
    }

    public void fetchLocation() {

        getPermissionToAccessLocation(context1);
        createLocationRequest();


    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected ...............................................");

        createLocationRequest();
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            latitude = (String.valueOf(mLastLocation.getLatitude()));
            longitude = (String.valueOf(mLastLocation.getLongitude()));

        }
        startLocationUpdates();

    }

    private void startLocationUpdates() {
        Log.d(TAG, "Location update starting ..............: ");

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    protected void stopLocationUpdates() {
        Log.d(TAG, "Location update stopping ..............: ");
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    private static final int permissionCheck = 1;

    @TargetApi(Build.VERSION_CODES.M)
    private void getPermissionToAccessLocation(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION)) {

            }
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    permissionCheck);


        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        locationFetched = true;
        stopLocationUpdates();
        mGoogleApiClient.disconnect();
        String replyMessage = location.getLatitude() + "/" + location.getLongitude() + "\n" + "Accuracy: " + location.getAccuracy() + "\n" +
                "Provider: " + location.getProvider();
        Geocoder geocoder = new Geocoder(context1, Locale.ENGLISH);
        String myAddress = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }

                myAddress = strReturnedAddress.toString();
            } else {
                myAddress = "No Address returned!";
            }
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
            myAddress = "Canont get Address!";
        }

        Log.d(TAG,"the receiver number is "+receiverNumber);
        replyMessage = replyMessage + "\n" + myAddress;
        if(receiverNumber!="") sendContent.sendSMS(replyMessage, receiverNumber, context1);
        Toast.makeText(context1, replyMessage, Toast.LENGTH_SHORT).show();
//        turnGPSOff();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        locationFetchedFail = true;
        locationFetched = true;
        sendContent.sendSMS("Connection to the provier has failed!\nCannot provide the location.", receiverNumber, context1);
        stopLocationUpdates();
        mGoogleApiClient.disconnect();
    }


    private void turnGPSOn(Context c) {
        String provider = Settings.Secure.getString(c.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (!provider.contains("gps")) { //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            c.sendBroadcast(poke);
        }
        Toast.makeText(c,"turning gps on...",Toast.LENGTH_SHORT).show();
    }

    private void turnGPSOff() {
        String provider = Settings.Secure.getString(context1.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (provider.contains("gps")) { //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            context1.sendBroadcast(poke);
        }
        Toast.makeText(context1,"turning gps off...",Toast.LENGTH_SHORT).show();
    }
}