package edu.northeastern.group21;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.northeastern.group21.sendsticker.SendSticker;

public class SentHistory extends AppCompatActivity {

    private String curUserName;
    private SentStickersAdapter sentStickersAdapter;
    private RecyclerView sentStickerRecyclerView;
    private List<SentSticker> sentStickers;
    private FirebaseDatabase mDatabase;
    private final String TAG = "-----SentHistory----";

    private Button sendSticker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_list);

        sentStickers = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance();
        curUserName = getIntent().getStringExtra("userName");

        //Retrieve current user data
        retrieveUserSentHistory();
        Log.v(TAG,"retrieveUserSentHistory Function Ran");
    }

    protected void retrieveUserSentHistory() {
        mDatabase.getReference().child("users").child(curUserName).child("sentStickers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot entrySnapshot: snapshot.getChildren()) {
                    int key = Integer.parseInt(entrySnapshot.getKey());
                    // Get the value as a map
                    Map<String, Object> map = (Map<String, Object>) entrySnapshot.getValue();

                    // Retrieve stickerID and stickerCount from the map
                    int stickerID = ((Long) Objects.requireNonNull(map.get("stickerID"))).intValue();
                    int stickerCount = ((Long) Objects.requireNonNull(map.get("stickerCount"))).intValue();

                    // Now you have stickerID and stickerCount, you can use them as needed
                    sentStickers.add(new SentSticker(stickerID, stickerCount));
                }
                init();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    protected void init() {
        if(sentStickers.size() != 0) {
            //Initialize recycler view
            sentStickerRecyclerView = findViewById(R.id.sticker_recycler_view);
            sentStickerRecyclerView.setHasFixedSize(true);
            sentStickerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            sentStickersAdapter = new SentStickersAdapter(sentStickers, this);
            sentStickerRecyclerView.setAdapter(sentStickersAdapter);
        } else {
            Toast.makeText(this, "No stickers have been sent yet", Toast.LENGTH_LONG).show();
        }
    }
}
