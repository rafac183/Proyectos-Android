package com.pluartz.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.pluartz.test.adapter.PetAdapter;
import com.pluartz.test.model.Pet;

public class MainActivity extends AppCompatActivity{

   Button btn_add, btn_add_fragment, btn_exit;
   PetAdapter mAdapter;
   RecyclerView mRecycler;
   FirebaseFirestore mFirestore;
   FirebaseAuth mAuth;
   SearchView search_view;
   Query query;

   @SuppressLint("NotifyDataSetChanged")
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      mFirestore = FirebaseFirestore.getInstance();
      mAuth = FirebaseAuth.getInstance();
      search_view = findViewById(R.id.search);

      btn_add = findViewById(R.id.btn_add);
      btn_add_fragment = findViewById(R.id.btn_add_fragment);
      btn_exit = findViewById(R.id.btn_close);

      btn_add.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, CreatePetActivity.class));
         }
      });
      btn_add_fragment.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            CreatePetFragment fm = new CreatePetFragment();
            fm.show(getSupportFragmentManager(), "Navegar a fragment");
         }
      });
      btn_exit.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            mAuth.signOut();
            finish();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
         }
      });

      setUpRecyclerView();
      search_view();
   }

   @SuppressLint("NotifyDataSetChanged")
   private void setUpRecyclerView() {
      mRecycler = findViewById(R.id.recyclerViewSingle);
      mRecycler.setLayoutManager(new LinearLayoutManager(this));
//      Query query = mFirestore.collection("pet").whereEqualTo("id_user", mAuth.getCurrentUser().getUid());
      query = mFirestore.collection("pet");

      FirestoreRecyclerOptions<Pet> firestoreRecyclerOptions =
              new FirestoreRecyclerOptions.Builder<Pet>().setQuery(query, Pet.class).build();

      mAdapter = new PetAdapter(firestoreRecyclerOptions, this, getSupportFragmentManager());
      mAdapter.notifyDataSetChanged();
      mRecycler.setAdapter(mAdapter);
   }

   private void search_view() {
      search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
         @Override
         public boolean onQueryTextSubmit(String s) {
            textSearch(s);
            return false;
         }

         @Override
         public boolean onQueryTextChange(String s) {
            textSearch(s);
            return false;
         }
      });
   }
   public void textSearch(String s){
      FirestoreRecyclerOptions<Pet> firestoreRecyclerOptions =
              new FirestoreRecyclerOptions.Builder<Pet>()
                      .setQuery(query.orderBy("name")
                              .startAt(s).endAt(s+"~"), Pet.class).build();
      mAdapter = new PetAdapter(firestoreRecyclerOptions, this, getSupportFragmentManager());
      mAdapter.startListening();
      mRecycler.setAdapter(mAdapter);
   }

   @Override
   protected void onStart() {
      super.onStart();
      mAdapter.startListening();
   }
   @Override
   protected void onStop() {
      super.onStop();
      mAdapter.stopListening();
   }
}