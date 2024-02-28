package edu.northeastern.group21;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

                final String name = username.getText().toString();
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

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
        // Intent to start next activity, passing the username as an extra
        Intent intent = new Intent(Login.this, SentHistory.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }


}