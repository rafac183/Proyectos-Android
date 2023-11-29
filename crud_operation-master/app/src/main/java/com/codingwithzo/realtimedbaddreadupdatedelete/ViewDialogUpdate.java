package com.codingwithzo.realtimedbaddreadupdatedelete;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

public class ViewDialogUpdate {

    DatabaseReference databaseReference;
    public void showDialog(Context context, String id, String name, String email, String country) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialog_add_new_user);

        EditText textName = dialog.findViewById(R.id.textName);
        EditText textEmail = dialog.findViewById(R.id.textEmail);
        EditText textCountry = dialog.findViewById(R.id.textCountry);

        textName.setText(name);
        textEmail.setText(email);
        textCountry.setText(country);


        Button buttonUpdate = dialog.findViewById(R.id.buttonAdd);
        Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

        buttonUpdate.setText("UPDATE");

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newName = textName.getText().toString();
                String newEmail = textEmail.getText().toString();
                String newCountry = textCountry.getText().toString();

                if (name.isEmpty() || email.isEmpty() || country.isEmpty()) {
                    Toast.makeText(context, "Please Enter All data...", Toast.LENGTH_SHORT).show();
                } else {

                    if (newName.equals(name) && newEmail.equals(email) && newCountry.equals(country)) {
                        Toast.makeText(context, "you don't change anything", Toast.LENGTH_SHORT).show();
                    } else {
                        databaseReference.child("USERS").child(id).setValue(new UsersItem(id, newName, newEmail, newCountry));
                        Toast.makeText(context, "User Updated successfully!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }


                }
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }
}
