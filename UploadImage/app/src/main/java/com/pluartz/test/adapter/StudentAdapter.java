package com.pluartz.test.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pluartz.test.R;
import com.pluartz.test.model.Student;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.viewHolderStudent>{

    ArrayList<Student> studentList;

    public StudentAdapter(ArrayList<Student> studentList) {
        this.studentList = (ArrayList<Student>) studentList;
    }

    @NonNull
    @Override
    public viewHolderStudent onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_single, parent, false);
        return new viewHolderStudent(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderStudent holder, int position) {
        Student student = studentList.get(position);
        Log.d("zzzzzz", "onBindViewHolder: "+student);
        holder.name.setText(student.getName());
        holder.email.setText(student.getEmail());
        holder.phone_number.setText(student.getPhone_number());
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public static class viewHolderStudent extends RecyclerView.ViewHolder {
        TextView name, email, phone_number;
        public viewHolderStudent(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            phone_number = itemView.findViewById(R.id.phone_number);
        }
    }

}
