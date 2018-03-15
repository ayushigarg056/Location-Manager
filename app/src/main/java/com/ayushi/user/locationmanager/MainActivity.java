package com.ayushi.user.locationmanager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnShowLocation;
    EditText e1, e2;
    // GPSTracker class
    GPSTracker gps;
    LocationManager locationManager;
    static final int REQUEST_LOCATION = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = (EditText) findViewById(R.id.lat);
        e2 = (EditText) findViewById(R.id.lon);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getlocation();
        btnShowLocation = (Button) findViewById(R.id.btnShowLocation);

        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object
                gps = new GPSTracker(MainActivity.this);

                // check if GPS enabled
                if (gps.canGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                } else {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }
        });
    }

    private void getlocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new  String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);

        }else {
            Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location!=null)
            {
               double latitude = location.getLatitude();
               double longitude = location.getLongitude();

                e1.setText("latitude : "+latitude);
                e2.setText("longitude : "+longitude);
            }else {
                e1.setText("latitude null ");
                e2.setText("longitude nuull");

            }
        }
    }
    @Override
    public  void onRequestPermissionsResult(int resultCode , @NonNull String[] permission, @NonNull int[] grantResult)
    {
        super.onRequestPermissionsResult(resultCode,permission,grantResult);
        switch (resultCode)
        {
            case REQUEST_LOCATION:
                getlocation();
                break;
        }
    }
}