package com.example.firebaseapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import models.Task;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.textView);

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://fir-application-456c3-default-rtdb.firebaseio.com/");
        DatabaseReference tasks = db.getReference().child("Tasks");

        Query query = tasks;

        System.out.println("1: " + System.currentTimeMillis());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("2: " + System.currentTimeMillis());
                textView.setText("");
                for (DataSnapshot data : snapshot.getChildren()) {
                    Task t = data.getValue(Task.class);

                    textView.setText(textView.getText() + t.toString() + "\n");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        System.out.println("3: " + System.currentTimeMillis());

        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(v -> {
            Random rand = new Random();

            Task t = new Task("Task-" + System.currentTimeMillis(),
                    rand.nextInt(2) == 0,
                    rand.nextInt(7) + 3);

            DatabaseReference push = tasks.push();
            push.setValue(t);

        });


        System.out.println("Finish");
    }
}