package com.example.restaurantmapapp2;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.restaurantmapapp2.data.DatabaseHelper;
import com.example.restaurantmapapp2.model.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.restaurantmapapp2.databinding.ActivityMapsBinding;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/


        Intent intent=getIntent();
        String activity=intent.getStringExtra("activity");
        if(activity.equals("addplaceActivity"))
        {
            LatLng selectedplace = new LatLng(intent.getDoubleExtra("selectedlat", 0), intent.getDoubleExtra("selectedlog",0));
            mMap.addMarker(new MarkerOptions().position(selectedplace).title("Marker in "+intent.getStringExtra("placenamestr")));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedplace,8));
            String testing="asdasdasdasdasda";

        }
        else if(activity.equals("MainActivity"))
        {

            DatabaseHelper db=new DatabaseHelper(MapsActivity.this);
            List<Place> placeList=db.fetchAllPlaces();
            for(int i=0;i<placeList.size();i++)
            {
                Log.i("Intent MainActivity","Working");
                if(isNumeric(placeList.get(i).getPlace_lat())&&isNumeric(placeList.get(i).getPlace_long()))
                {
                    LatLng selectedplace = new LatLng(Double.parseDouble(placeList.get(i).getPlace_lat()), Double.parseDouble(placeList.get(i).getPlace_long()));
                    mMap.addMarker(new MarkerOptions().position(selectedplace).title("Marker in "+placeList.get(i).getPlace_name()));
                    if(i==(placeList.size()-1))
                    {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedplace,6));
                    }
                }



            }


        }
    }
}