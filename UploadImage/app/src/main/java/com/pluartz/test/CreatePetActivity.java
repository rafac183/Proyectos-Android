package com.pluartz.test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreatePetActivity extends AppCompatActivity {

   ImageView photo_pet;
   Button btn_add;
   Button btn_cu_photo, btn_r_photo;
   LinearLayout linearLayout_image_btn;
   EditText name, age, color, precio_vacuna;
   private FirebaseFirestore mfirestore;
   private FirebaseAuth mAuth;

   StorageReference storageReference;
   String storage_path = "pet/*";

   private static final int COD_SEL_STORAGE = 200;
   private static final int COD_SEL_IMAGE = 300;

   private Uri image_url;
   String photo = "photo";
   String idd;

   ProgressDialog progressDialog;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_create_pet);
      this.setTitle("Mascota");
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      progressDialog = new ProgressDialog(this);
      String id = getIntent().getStringExtra("id_pet");
      mfirestore = FirebaseFirestore.getInstance();
      mAuth = FirebaseAuth.getInstance();
      storageReference = FirebaseStorage.getInstance().getReference();

      linearLayout_image_btn = findViewById(R.id.images_btn);
      name = findViewById(R.id.nombre);
      age = findViewById(R.id.edad);
      color = findViewById(R.id.color);
      precio_vacuna = findViewById(R.id.precio_vacuna);
      photo_pet = findViewById(R.id.pet_photo);
      btn_cu_photo = findViewById(R.id.btn_photo);
      btn_r_photo = findViewById(R.id.btn_remove_photo);
      btn_add = findViewById(R.id.btn_add);

      btn_cu_photo.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            uploadPhoto();
         }
      });

      btn_r_photo.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("photo", "");
            mfirestore.collection("pet").document(idd).update(map);
            Toast.makeText(CreatePetActivity.this, "Foto eliminada", Toast.LENGTH_SHORT).show();
         }
      });

      if (id == null || id == ""){
         linearLayout_image_btn.setVisibility(View.GONE);
         btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String namepet = name.getText().toString().trim();
               String agepet = age.getText().toString().trim();
               String colorpet = color.getText().toString().trim();
               Double precio_vacunapet = Double.parseDouble(precio_vacuna.getText().toString().trim());

               if(namepet.isEmpty() && agepet.isEmpty() && colorpet.isEmpty()){
                  Toast.makeText(getApplicationContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();
               }else{
                  postPet(namepet, agepet, colorpet, precio_vacunapet);
               }
            }
         });
      }else{
         idd = id;
         btn_add.setText("Update");
         getPet(id);
         btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String namepet = name.getText().toString().trim();
               String agepet = age.getText().toString().trim();
               String colorpet = color.getText().toString().trim();
               Double precio_vacunapet = Double.parseDouble(precio_vacuna.getText().toString().trim());

               if(namepet.isEmpty() && agepet.isEmpty() && colorpet.isEmpty()){
                  Toast.makeText(getApplicationContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();
               }else{
                  updatePet(namepet, agepet, colorpet, precio_vacunapet, id);
               }
            }
         });
      }
   }

   private void uploadPhoto() {
      Intent i = new Intent(Intent.ACTION_PICK);
      i.setType("image/*");

      startActivityForResult(i, COD_SEL_IMAGE);
   }


   @Override
   protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      if(resultCode == RESULT_OK){
         if (requestCode == COD_SEL_IMAGE){
            image_url = data.getData();
            subirPhoto(image_url);
         }
      }
      super.onActivityResult(requestCode, resultCode, data);
   }

   private void subirPhoto(Uri image_url) {
      progressDialog.setMessage("Actualizando foto");
      progressDialog.show();
      String rute_storage_photo = storage_path + "" + photo + "" + mAuth.getUid() +""+ idd;
      StorageReference reference = storageReference.child(rute_storage_photo);
      reference.putFile(image_url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
         @Override
         public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
            while (!uriTask.isSuccessful());
               if (uriTask.isSuccessful()){
                  uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                     @Override
                     public void onSuccess(Uri uri) {
                        String download_uri = uri.toString();
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("photo", download_uri);
                        mfirestore.collection("pet").document(idd).update(map);
                        Toast.makeText(CreatePetActivity.this, "Foto actualizada", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                     }
                  });
               }
         }
      }).addOnFailureListener(new OnFailureListener() {
         @Override
         public void onFailure(@NonNull Exception e) {
            Toast.makeText(CreatePetActivity.this, "Error al cargar foto", Toast.LENGTH_SHORT).show();
         }
      });
   }

   private void updatePet(String namepet, String agepet, String colorpet, Double precio_vacunapet, String id) {
      Map<String, Object> map = new HashMap<>();
      map.put("name", namepet);
      map.put("age", agepet);
      map.put("color", colorpet);
      map.put("vaccine_price", precio_vacunapet);

      mfirestore.collection("pet").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
         @Override
         public void onSuccess(Void unused) {
            Toast.makeText(getApplicationContext(), "Actualizado exitosamente", Toast.LENGTH_SHORT).show();
            finish();
         }
      }).addOnFailureListener(new OnFailureListener() {
         @Override
         public void onFailure(@NonNull Exception e) {
            Toast.makeText(getApplicationContext(), "Error al actualizar", Toast.LENGTH_SHORT).show();
         }
      });
   }

   private void postPet(String namepet, String agepet, String colorpet, Double precio_vacunapet) {
      String idUser = mAuth.getCurrentUser().getUid();
      DocumentReference id = mfirestore.collection("pet").document();

      Map<String, Object> map = new HashMap<>();
      map.put("id_user", idUser);
      map.put("id", id.getId());
      map.put("name", namepet);
      map.put("age", agepet);
      map.put("color", colorpet);
      map.put("vaccine_price", precio_vacunapet);

      mfirestore.collection("pet").document(id.getId()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
         @Override
         public void onSuccess(Void unused) {
            Toast.makeText(getApplicationContext(), "Creado exitosamente", Toast.LENGTH_SHORT).show();
            finish();
         }
      }).addOnFailureListener(new OnFailureListener() {
         @Override
         public void onFailure(@NonNull Exception e) {
            Toast.makeText(getApplicationContext(), "Error al ingresar", Toast.LENGTH_SHORT).show();
         }
      });
   }

   private void getPet(String id){
      mfirestore.collection("pet").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
         @Override
         public void onSuccess(DocumentSnapshot documentSnapshot) {
            DecimalFormat format = new DecimalFormat("0.00");
            String namePet = documentSnapshot.getString("name");
            String agePet = documentSnapshot.getString("age");
            String colorPet = documentSnapshot.getString("color");
            Double precio_vacunapet = documentSnapshot.getDouble("vaccine_price");
            String photoPet = documentSnapshot.getString("photo");

            name.setText(namePet);
            age.setText(agePet);
            color.setText(colorPet);
            precio_vacuna.setText(format.format(precio_vacunapet));
            try {
               if(!photoPet.equals("")){
                  Toast toast = Toast.makeText(getApplicationContext(), "Cargando foto", Toast.LENGTH_SHORT);
                  toast.setGravity(Gravity.TOP,0,200);
                  toast.show();
                  Picasso.with(CreatePetActivity.this)
                          .load(photoPet)
                          .resize(150, 150)
                          .into(photo_pet);
               }
            }catch (Exception e){
               Log.v("Error", "e: " + e);
            }
         }
      }).addOnFailureListener(new OnFailureListener() {
         @Override
         public void onFailure(@NonNull Exception e) {
            Toast.makeText(getApplicationContext(), "Error al obtener los datos", Toast.LENGTH_SHORT).show();
         }
      });
   }

   @Override
   public boolean onSupportNavigateUp() {
      onBackPressed();
      return false;
   }
}