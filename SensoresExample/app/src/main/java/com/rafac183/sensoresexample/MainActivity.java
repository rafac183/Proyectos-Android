package com.rafac183.sensoresexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rafac183.sensoresexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void openProximity(View view) {
        startActivity(new Intent(MainActivity.this,
                ProximityActivity.class));
    }
    public void openGyroscope(View view) {
        startActivity(new Intent(MainActivity.this,
                GyroscopeActivity.class));
    }
    public void openRotationVector(View view) {
        startActivity(new Intent(MainActivity.this,
                RotationVectorActivity.class));
    }
}
