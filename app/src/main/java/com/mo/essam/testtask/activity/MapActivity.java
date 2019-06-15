package com.mo.essam.testtask.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mo.essam.testtask.R;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    ImageView backImage;
    TextView titleText;

    private GoogleMap mMap;
    double lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        initializeViews();
        if (getIntent().hasExtra("LAT")){
            lat=getIntent().getDoubleExtra("LAT",0);
            lng=getIntent().getDoubleExtra("LNG",0);
        }
        setupMap();

    }

    private void setupMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng loc = new LatLng(lat,lng);

        mMap.addMarker(new MarkerOptions().position(loc).title("Me"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,16));
    }

    private void initializeViews(){
        backImage = findViewById(R.id.backImage);
        titleText = findViewById(R.id.titleText);

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        titleText.setText(getString(R.string.map));

    }
}
