package com.pluartz.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pluartz.test.adapter.StudentAdapter;
import com.pluartz.test.model.Student;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity{

    StudentAdapter studentAdapter;
    ArrayList<Student> studentArrayList;
    RecyclerView mRecyclerView;
    FirebaseFirestore mFirestore;
    SearchView searchView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        setTitle("Students");
        mFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);

        mRecyclerView = findViewById(R.id.recyclerStudent);
        searchView = findViewById(R.id.search);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentArrayList = new ArrayList<>();
        studentAdapter = new StudentAdapter(studentArrayList);
        mRecyclerView.setAdapter(studentAdapter);

        getStudents();
        searchStudent();
    }

    private  void getStudents(){
        progressDialog.setMessage("Obteniendo estudiantes");
        progressDialog.show();
//        mFirestore.collection("student").orderBy("name").get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @SuppressLint("NotifyDataSetChanged")
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()){
//                            for (DocumentSnapshot snapshot : task.getResult()){
//                                String id =
//                                Student student = new Student(
//                                        snapshot.getString("name"),
//                                        snapshot.getString("phone_number"),
//                                        snapshot.getString("email"));
//                                studentArrayList.add(student);
//                            }
//                            studentAdapter.notifyDataSetChanged();
//                            progressDialog.dismiss();
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(StudentActivity.this, "Error al obtener", Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
//            }
//        });
        mFirestore.collection("student").orderBy("name").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d("zzzzzz", document.getId() + " => " + document.getData());
                                Student student = document.toObject(Student.class);
//                                studentArrayList.add(student);
//                                Student student = new Student(
//                                        document.getString("name"),
//                                        document.getString("phone_number"),
//                                        document.getString("email"));
                                studentArrayList.add(student);
                            }
                            studentAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    private void searchStudent() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) { return false; }

            @Override
            public boolean onQueryTextChange(String s) {
                searchStudent(s);
                return true;
            }
        });
    }

    private void searchStudent(String s) {
        ArrayList<Student> listStudent = new ArrayList<>();
        for (Student student : studentArrayList){
            if (student.getName().toLowerCase().contains(s.toLowerCase())){
                listStudent.add(student);
            }
        }
        studentAdapter = new StudentAdapter(listStudent);
        mRecyclerView.setAdapter(studentAdapter);
    }
}