package models;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Auth {
    public static User authorizedUser = null;

    public static void fetchUser(String username, OnUserReadyListener listener) {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://fir-application-456c3-default-rtdb.firebaseio.com/");
        DatabaseReference users = db.getReference().child("Users");

        Query queryAuth = users.orderByChild("username").equalTo(username);
        queryAuth.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    authorizedUser = data.getValue(User.class);
                    listener.OnUserReady(authorizedUser);
                    return;
                }
//                    Toast: Not authorized
                listener.OnUserReady(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.OnUserReady(null);
            }
        });

    }

    public interface OnUserReadyListener {
        void OnUserReady(User u);
    }
}
