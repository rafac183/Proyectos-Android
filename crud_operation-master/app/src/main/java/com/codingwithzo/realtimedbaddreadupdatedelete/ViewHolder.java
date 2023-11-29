package com.codingwithzo.realtimedbaddreadupdatedelete;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView textName;
    TextView textEmail;
    TextView textCountry;

    Button buttonDelete;
    Button buttonUpdate;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        textName = itemView.findViewById(R.id.textName);
        textEmail = itemView.findViewById(R.id.textEmail);
        textCountry = itemView.findViewById(R.id.textCountry);

        buttonDelete = itemView.findViewById(R.id.buttonDelete);
        buttonUpdate = itemView.findViewById(R.id.buttonUpdate);
    }
}
