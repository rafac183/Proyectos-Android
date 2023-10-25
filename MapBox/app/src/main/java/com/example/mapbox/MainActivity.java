package com.example.mapbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapView mapView = findViewById(R.id.mapView);
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);
    }
}