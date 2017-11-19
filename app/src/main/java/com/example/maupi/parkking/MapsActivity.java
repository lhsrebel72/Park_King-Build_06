package com.example.maupi.parkking;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private UiSettings mUiSettings;
    private String selectedMarkerID = "111111";
    private DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Sets up map settings
        mMap = googleMap;
        mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);

        //Sets up markers
        ArrayList<ParkingMeterData> meters = new ArrayList<>();
        int numberOfMeters = 4;
        for(int i = 0; i < numberOfMeters; i++){
            meters.add(putDataintoMeter(i));
        }

        for(ParkingMeterData p : meters){
            if(db.checkMeterTable()){
                db.insertMeter(p);
            }
        }

        //Puts markers on the map
        mMap.addMarker(new MarkerOptions()
                .position(meters.get(0).getLatlng())
                .title("Currently Available: " + ( meters.get(0).isAvailable()? "YES" : "NOPE" ))
                .snippet("Minutes until available: " + meters.get(0).getTimeTillAvailble()));
        mMap.addMarker(new MarkerOptions()
                .position(meters.get(1).getLatlng())
                .title("Currently Available: " + ( meters.get(1).isAvailable()? "YES" : "NOPE" ))
                .snippet("Minutes until available: " + meters.get(1).getTimeTillAvailble()));
        mMap.addMarker(new MarkerOptions()
                .position(meters.get(2).getLatlng())
                .title("Currently Available: " + ( meters.get(2).isAvailable()? "YES" : "NOPE" ))
                .snippet("Minutes until available: " + meters.get(2).getTimeTillAvailble()));
        mMap.addMarker(new MarkerOptions()
                .position(meters.get(3).getLatlng())
                .title("Currently Available: " + ( meters.get(3).isAvailable()? "YES" : "NOPE" ))
                .snippet("Minutes until available: " + meters.get(3).getTimeTillAvailble()));
        //Sets default location of the map
        //TODO change to user's location
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(meters.get(3).getLatlng(), 14.0f));

        //Make the Marker's infoWindow clickable
        mMap.setOnInfoWindowClickListener(this);
    }

    public ParkingMeterData putDataintoMeter(int meterNumber){
        ParkingMeterData pmd = new ParkingMeterData();
        switch (meterNumber){
            case 0: pmd.setId(111111);  //My address
                pmd.setAvailable(true);
                pmd.setTimeTillAvailble(0);
                pmd.setPrice(5.2);
                pmd.setTimePerUse(45);
                pmd.setLatlng(new LatLng(31.7942, -85.9452));
                pmd.setAddress("205 S Franklin Dr\n36081\nTroy\nAlabama");
                break;
            case 1: pmd.setId(111112);  //University Avenue
                pmd.setAvailable(false);
                pmd.setTimeTillAvailble(15);
                pmd.setPrice(10);
                pmd.setTimePerUse(45);
                pmd.setLatlng(new LatLng(31.7988, -85.957));
                pmd.setAddress("205 S Franklin Dr\n36081\nTroy\nAlabama");
                break;
            case 2: pmd.setId(111113);  //Walmart
                pmd.setAvailable(true);
                pmd.setTimeTillAvailble(0);
                pmd.setPrice(4.55);
                pmd.setTimePerUse(30);
                pmd.setLatlng(new LatLng(31.7789, -85.9411));
                pmd.setAddress("205 S Franklin Dr\n36081\nTroy\nAlabama");
                break;
            case 3: pmd.setId(111114);  //TC
                pmd.setAvailable(false);
                pmd.setTimeTillAvailble(30);
                pmd.setPrice(3);
                pmd.setTimePerUse(60);
                pmd.setLatlng(new LatLng(31.8018, -85.9554));
                pmd.setAddress("205 S Franklin Dr\n36081\nTroy\nAlabama");
                break;
        }
        return pmd;
    }

    public static void putMeterIntoDB(ParkingMeterData pmd){
        pmd.getId();
    }
    @Override
    public void onInfoWindowClick(Marker marker) {
        //Toast.makeText(this, marker.getSnippet(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MapsActivity.this, MeterActivity.class);
        intent.putExtra("ParkingMeterID", selectedMarkerID);
        startActivity(intent);
        //finish();
        //setContentView(R.layout.activity_meter);
    }
}
