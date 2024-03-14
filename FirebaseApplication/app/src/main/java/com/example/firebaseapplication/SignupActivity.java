package com.example.firebaseapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import models.Auth;
import models.User;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://fir-application-456c3-default-rtdb.firebaseio.com/");
        DatabaseReference users = db.getReference().child("Users");


        Button btn = findViewById(R.id.btn);
        EditText txtFirstName = findViewById(R.id.txtFirstName);
        EditText txtUsername = findViewById(R.id.txtUsername);
        EditText txtPasswd = findViewById(R.id.txtPasswd);

        btn.setOnClickListener(v -> {
            String firstName = txtFirstName.getText().toString();
            String username = txtUsername.getText().toString();
            String passwd = txtPasswd.getText().toString();

//            Check username is unique
//            Query query = users.orderByChild("username").equalTo(username); // old code
//            query.addListenerForSingleValueEvent();
//            if(exists){
//                Toast
//            }

            Auth.fetchUser(username, new Auth.OnUserReadyListener() {
                @Override
                public void OnUserReady(User u) {
                    if (u != null) {
                        //error, such username exists
                    } else {
                        //do create user
                    }
                }
            });

//             В этом месте мы уверены что все данные введены корректно и username уникальный
            User user = new User(username, passwd, firstName);
            DatabaseReference push = users.push();
            user.setKey( push.getKey() );
            push.setValue(user);

            txtFirstName.setText("");
            txtUsername.setText("");
            txtPasswd.setText("");

            Toast.makeText(SignupActivity.this, "Success", Toast.LENGTH_LONG).show();
        });
    }
}