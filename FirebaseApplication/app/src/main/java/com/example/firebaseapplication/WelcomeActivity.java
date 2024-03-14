package com.example.firebaseapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import models.Auth;
import models.User;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        SharedPreferences preferences = getSharedPreferences("app", MODE_PRIVATE);
        String username = preferences.getString("username", null);
        if (username == null) {
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            FirebaseDatabase db = FirebaseDatabase.getInstance("https://jsc-2021-default-rtdb.europe-west1.firebasedatabase.app/");
            DatabaseReference users = db.getReference().child("Users");

            Query queryAuth = users.orderByChild("username").equalTo(username);
            queryAuth.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        User authorizedUser = data.getValue(User.class);
                        Auth.authorizedUser = authorizedUser;
                        Intent intent = new Intent(WelcomeActivity.this, TaskListFirebaseActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        intent.putExtra("user", authorizedUser);
                        startActivity(intent);
                        return;
                    }
//                    Toast: Not authorized
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
    }
}