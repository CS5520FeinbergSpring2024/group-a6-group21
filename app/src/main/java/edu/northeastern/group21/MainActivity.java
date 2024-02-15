package edu.northeastern.group21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        }
    }
}