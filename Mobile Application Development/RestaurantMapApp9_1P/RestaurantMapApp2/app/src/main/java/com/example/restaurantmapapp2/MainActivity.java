package com.example.restaurantmapapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void addplacefunction(View view) {
        Intent intent = new Intent(MainActivity.this, addplaceActivity.class);
        startActivity(intent);
    }

    public void showallfunction(View view) {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        intent.putExtra("activity","MainActivity");
        startActivity(intent);
    }
}