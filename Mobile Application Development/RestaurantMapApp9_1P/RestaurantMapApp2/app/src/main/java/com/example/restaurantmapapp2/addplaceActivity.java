package com.example.restaurantmapapp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantmapapp2.data.DatabaseHelper;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;


import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class addplaceActivity extends AppCompatActivity {
    private static final String TAG = "TAG";
    EditText placename, locationview;
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    LatLng placelocation;
    String placenamestr;
    DatabaseHelper db;

    LocationManager locationManager;
    LocationListener locationListener;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 120, 0, locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addplace);
        placename = findViewById(R.id.placenameView);
        locationview = findViewById(R.id.locationView);
        if (!Places.isInitialized()) {
            // Initialize the SDK
            Places.initialize(getApplicationContext(), getString(R.string.places_api));


        }
        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(this);

        /*// Set the fields to specify which types of place data to
        // return after the user has made a selection.
        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                Toast.makeText(addplaceActivity.this, "Place: " + place.getName() + ", " + place.getId(), Toast.LENGTH_LONG);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            }


            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });*/






    }

    public void getlocationfunction(View view) {


        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.d(TAG, "onLocationChanged: ");
                locationview.setText(location.toString(), TextView.BufferType.NORMAL);
                Log.d(TAG, String.valueOf(location.getLatitude())+String.valueOf(location.getLongitude()));
                placelocation=new LatLng(location.getLatitude(), location.getLongitude());
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            else
            {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
        else{
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                Log.i("Places AutoComplete getLatLang", String.valueOf(place.getLatLng()));
                if(place.getLatLng()!=null)
                {
                    Log.i("Places AutoComplete", String.valueOf(place.getLatLng().latitude));
                }
                placelocation=place.getLatLng();
                placenamestr=place.getName();
                locationview.setText(String.valueOf(place.getLatLng().latitude)+"\t::\t"+String.valueOf(place.getLatLng().longitude));
                placename.setText(placenamestr, TextView.BufferType.NORMAL);


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }




    public void showonmapfunction(View view) {
        Intent intent = new Intent(addplaceActivity.this, MapsActivity.class);
        intent.putExtra("selectedlat",placelocation.latitude);
        intent.putExtra("selectedlog",placelocation.longitude);
        intent.putExtra("placenamestr",placenamestr);
        intent.putExtra("activity","addplaceActivity");
        startActivity(intent);
    }

    public void savefunction(View view) {
        DatabaseHelper db=new DatabaseHelper(addplaceActivity.this);
        Log.i("Save Button check","Working");
        long result=db.insertPlace(new com.example.restaurantmapapp2.model.Place(placenamestr,String.valueOf(placelocation.latitude),String.valueOf(placelocation.longitude)));
        if (result > 0)
        {
            Toast.makeText(addplaceActivity.this, "Saved successfully!", Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            Toast.makeText(addplaceActivity.this, "Save Unsuccessful!", Toast.LENGTH_SHORT).show();
        }


    }

    public void autocompletefunction(View view) {
        List<Place.Field> fields = Arrays.asList(Place.Field.LAT_LNG,Place.Field.ID, Place.Field.NAME);

        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

}