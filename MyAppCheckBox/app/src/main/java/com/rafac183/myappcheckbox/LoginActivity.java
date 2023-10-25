package com.rafac183.myappcheckbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {
    private EditText user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = (EditText) findViewById(R.id.textUser);
    }
    public void btnLogin(View v){
        String mensaje;
        String datos = user.getText().toString();
        if (datos.equals("")) {
            mensaje = "Ingrese Datos";
            Snackbar.make(v, mensaje, Snackbar.LENGTH_SHORT).show();
        } else {
            Intent myIntent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(myIntent);
        }


    }
}