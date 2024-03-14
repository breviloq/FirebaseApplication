package com.example.firebaseapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import models.User;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences preferences = getSharedPreferences("app", MODE_PRIVATE);

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://fir-application-456c3-default-rtdb.firebaseio.com/");
        DatabaseReference users = db.getReference().child("Users");

        Button btn = findViewById(R.id.btn);
        EditText txtUsername = findViewById(R.id.txtUsername);
        EditText txtPasswd = findViewById(R.id.txtPasswd);

        btn.setOnClickListener(v -> {
            String username = txtUsername.getText().toString();
            String passwd = txtPasswd.getText().toString();

            Query query = users.orderByChild("username").equalTo(username); // && passwd == passwd
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        User user = data.getValue(User.class);
                        //check passwd
                        if (user.getPasswd().equals(passwd)) {
                            //success
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("username", user.getUsername());
                            editor.apply();

                            Toast.makeText(LoginActivity.this, "Success auth", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, TaskListFirebaseActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            //password is not correct
                            Toast.makeText(LoginActivity.this, "Password is not correct", Toast.LENGTH_LONG).show();
                        }
                        return;
                    }
                    Toast.makeText(LoginActivity.this, "Username is not correct", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }
}