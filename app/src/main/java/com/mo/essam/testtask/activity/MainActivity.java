package com.mo.essam.testtask.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mo.essam.testtask.R;
import com.mo.essam.testtask.helper.LocationProvider;
import com.mo.essam.testtask.helper.PrefManager;

public class MainActivity extends AppCompatActivity implements LocationProvider.LocationCallback {
    ImageView backImage, logOutImage;
    TextView titleText;
    ProgressBar homeProgress;
    PrefManager prefManager;

    boolean isLoading=false;
    LocationProvider locationProvider;
    int LOCATION_PERMISSIONS = 1000,CONTACTS_PERMISSION=2000;
    double lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        prefManager = new PrefManager(this);
        locationProvider = new LocationProvider(this, this);
    }

    public void goToProducts(View view) {
        if (!isLoading) {
            Intent intent = new Intent(this,ProductsActivity.class);
            startActivity(intent);
        }
    }

    public void goToMaps(View view) {
        if (!isLoading) {
            requestPermissions(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    public void goToContacts(View view) {
        if (!isLoading) {
            requestPermissions(Manifest.permission.READ_CONTACTS);
        }
    }

    public void goToCountries(View view) {
        if (!isLoading) {
            Intent intent = new Intent(this,CountriesActivity.class);
            startActivity(intent);
        }
    }

    public void goToEvents(View view) {
        if (!isLoading) {
            Intent intent = new Intent(this,AddEventActivity.class);
            startActivity(intent);
        }
    }

    private void requestPermissions(String permission) {
        isLoading=true;
        if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            if (permission.equalsIgnoreCase(Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this,new String[]{
                        permission}, LOCATION_PERMISSIONS);
            }
            else {
                ActivityCompat.requestPermissions(this,new String[]{
                        permission}, CONTACTS_PERMISSION);
            }

        } else {

            if (permission.equalsIgnoreCase(Manifest.permission.ACCESS_FINE_LOCATION)){
                getLocation();
            }
            else {
                isLoading=false;
                Intent intent = new Intent(this,ContactsActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSIONS) {
            if (grantResults.length > 0 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                isLoading=false;
                Toast.makeText(MainActivity.this, getString(R.string.permission_warning), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            isLoading=false;
            if (grantResults.length > 0 && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                    == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(this,ContactsActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, getString(R.string.permission_warning), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getLocation() {
        homeProgress.setVisibility(View.VISIBLE);
        if (locationProvider != null) {
            locationProvider.connect();
        }
    }

    @Override
    public void handleNewLocation(Location location) {
        isLoading=false;
        homeProgress.setVisibility(View.GONE);
        locationProvider.disconnect();
        lat = location.getLatitude();
        lng  = location.getLongitude();
        googleMapsDialog();
    }

    private void googleMapsDialog(){
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_map,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView googleMapsText, inAppText;
        googleMapsText = dialogView.findViewById(R.id.googleMapsText);
        inAppText = dialogView.findViewById(R.id.inAppText);

        googleMapsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGoogleMaps();
                alertDialog.dismiss();
            }
        });

        inAppText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MapActivity.class);

                intent.putExtra("LAT",lat);
                intent.putExtra("LNG",lng);

                startActivity(intent);
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void goToGoogleMaps(){
        Uri gmmIntentUri = Uri.parse("geo:"+lat+","+lng+"?q=stores");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    private void initializeViews(){
        backImage = findViewById(R.id.backImage);
        titleText = findViewById(R.id.titleText);
        homeProgress = findViewById(R.id.homeProgress);
        logOutImage = findViewById(R.id.logOutImage);

        titleText.setText(getString(R.string.home));
        backImage.setVisibility(View.GONE);
        logOutImage.setVisibility(View.VISIBLE);

        logOutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefManager.setIsLoggedIn(false);
                Intent intent = new Intent(MainActivity.this,SplashActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
