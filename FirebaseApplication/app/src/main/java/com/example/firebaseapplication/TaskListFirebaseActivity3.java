package com.example.firebaseapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import adapters.TaskListAdapter;
import models.Auth;
import models.Task;
import models.User;

public class TaskListFirebaseActivity3 extends AppCompatActivity {
    ArrayList<Task> list = new ArrayList<>();
    User authorizedUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list_firebase);
        Toast.makeText(this, "TaskListFirebaseActivity2", Toast.LENGTH_LONG).show();

        SharedPreferences preferences = getSharedPreferences("app", MODE_PRIVATE);
        String username = preferences.getString("username", null);

        Auth.fetchUser(username, new Auth.OnUserReadyListener() {
            @Override
            public void OnUserReady(User u) {
                if (u != null) {
                    authorizedUser = u;
                    work();
                } else {
                    //not auth
                }
            }
        });


    }

    private void work() {

//        User user = (User)getIntent().getExtras().get("user");
//        User user = Auth.authorizedUser;
        TextView tvFirstName = findViewById(R.id.tvFirstName);
        tvFirstName.setText("Hello " + authorizedUser.getFirstName());

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://fir-application-456c3-default-rtdb.firebaseio.com/");
        DatabaseReference tasks = db.getReference().child("Tasks");


        ListView listView = findViewById(R.id.listView);

        TaskListAdapter adapter = new TaskListAdapter(this, list);
        listView.setAdapter(adapter);


        Query query = tasks;
//        Query query = tasks.orderByKey().equalTo("-Np4hwYdX_f93f97OmPP");//найти по key
//        Query query = tasks.orderByChild("questionsCount").equalTo(9);//==
//        Query query = tasks.orderByChild("questionsCount").startAt(9);//>=
//Query query = tasks.orderByChild("questionsCount").startAfter("333");//>
//Query query = tasks.orderByChild("questionsCount").endAt("333");//<=
//Query query = tasks.orderByChild("questionsCount").endBefore("333");//<
//        Query query = tasks.orderByChild("questionsCount").startAt(9).endAt(13);//>=9 and <=13
//Query query = tasks.orderByChild("questionsCount").startAt(9).limitToFirst(2);
//        Query query = tasks.orderByChild("questionsCount").startAt(9).limitToLast(2);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
//                list = new ArrayList<>(); bug
                for (DataSnapshot data : snapshot.getChildren()) {
                    Task t = data.getValue(Task.class);
                    list.add(t);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Task t = list.get(position);
            tasks.child(t.getKey()).removeValue();

            Toast.makeText(TaskListFirebaseActivity3.this, t.toString(), Toast.LENGTH_LONG).show();
            return true;
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Task t = list.get(position);
            t.setQuestionsCount(t.getQuestionsCount() + 1);
            tasks.child(t.getKey()).setValue(t);
        });

        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(v -> {

            Random rand = new Random();

            Task t = new Task("Task-" + System.currentTimeMillis(),
                    rand.nextInt(2) == 0,
                    rand.nextInt(7) + 3);
//            t.setAuthorKey(  );

            DatabaseReference push = tasks.push();
            t.setKey(push.getKey());
            push.setValue(t);/*.addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(), "onSuccess", Toast.LENGTH_LONG).show();
                    System.out.println("onSuccess");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "onFailure", Toast.LENGTH_LONG).show();
                    System.out.println("onFailure");
                }
            }).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                    Toast.makeText(getApplicationContext(), "onComplete", Toast.LENGTH_LONG).show();
                    System.out.println("onComplete");
                }
            }).addOnCanceledListener(new OnCanceledListener() {
                @Override
                public void onCanceled() {
                    Toast.makeText(getApplicationContext(), "onCanceled", Toast.LENGTH_LONG).show();
                    System.out.println("onCanceled");
                }
            });*/
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            SharedPreferences preferences = getSharedPreferences("app", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("username");
            editor.apply();
            Auth.authorizedUser = null;

            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}