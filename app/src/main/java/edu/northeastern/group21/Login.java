package edu.northeastern.group21;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.northeastern.group21.sendsticker.SendSticker;

public class Login extends AppCompatActivity {

    EditText username;
    Button loginButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.editTextUserName);
        loginButton = findViewById(R.id.buttonLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = username.getText().toString().trim();
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                if(name != null && !name.isEmpty()){
                    disconnectCurrentUser();
                }

                if (name.isEmpty()) {
                    Toast.makeText(Login.this, "Please enter a valid username", Toast.LENGTH_SHORT).show();
                    return; // Exit the method early
                }

                reference.child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            proceedToNextActivity(name);
                        } else{
                            reference.child(name).setValue(new User(name));
                            proceedToNextActivity(name);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Login.this, "Database error", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }


    private void proceedToNextActivity(String userName) {
        DatabaseReference userRef = reference.child(userName);

        // When logging in, store the username
        SharedPreferences sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("LastLoggedInUser", userName);
        editor.apply();

        userRef.child("online").setValue(true);
        userRef.child("online").onDisconnect().setValue(false);

        // Intent to start next activity, passing the username as an extra
        Intent intent = new Intent(Login.this, SendSticker.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }

    // To disconnect the current user, log-out current user if any
    private void disconnectCurrentUser() {
        SharedPreferences sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String currentUserName = sharedPref.getString("LastLoggedInUser", null);
        if (currentUserName != null) {
            DatabaseReference userRef = database.getReference("users").child(currentUserName);
            userRef.child("online").setValue(false);
        }
    }

    @Override
    public void onBackPressed() {
        disconnectCurrentUser(); // Call this method before the super method
        super.onBackPressed();
    }

}