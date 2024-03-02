package edu.northeastern.group21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase mDatabase;
    private String currentUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentUserName = getIntent().getStringExtra("userName");
    }

    public void onclick(View view) {
        int theId = view.getId();

        if (theId == R.id.button_groupInfo) {
            // Click for Group Info
            Intent intent = new Intent(MainActivity.this, GroupInfo.class);
            startActivity(intent);

        } else if (theId == R.id.button_webService) {
            // Click for A6 Web service
            Intent intent = new Intent(MainActivity.this, WebService.class);
            startActivity(intent);
        } else if (theId == R.id.button_Firebase) {
            // Click for A7
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        }
    }
}