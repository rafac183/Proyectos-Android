package com.rafac183.myappcheckbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private CheckBox seleccionarChk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        seleccionarChk = (CheckBox) findViewById(R.id.chkMas);
    }
    public void btnChek(View v){
        String mensaje;
        if (seleccionarChk.isChecked() == true){
            mensaje = "Seleccionado";
            Snackbar.make(v, mensaje,Snackbar.LENGTH_SHORT).show();
            Intent myIntent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(myIntent);
        } else {
            mensaje = "No Seleccionado";
            Toast tost = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
            tost.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.START, 90, 0);
            tost.show();
        }

    }
}