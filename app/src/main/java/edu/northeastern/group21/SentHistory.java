package edu.northeastern.group21;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SentHistory extends AppCompatActivity {

    private String curUserName;
    private SentStickersAdapter sentStickersAdapter;
    private RecyclerView sentStickerRecyclerView;
    private List<SentSticker> sentStickers;
    private FirebaseDatabase mDatabase;
    private final String TAG = "-----SentHistory----";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_list);
        Log.v(TAG,"In the page");

        //Assume the logged in user is user1
        curUserName = "user1";
        sentStickers = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance();
        Log.v(TAG,"Database Connected");

        //Retrieve current user data
        retrieveUserSentHistory();
        Log.v(TAG,"retrieveUserSentHistory Function Ran");
    }

    protected void retrieveUserSentHistory() {
        mDatabase.getReference().child("users").child(curUserName).child("sentStickers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "in onDataChange");
                for(DataSnapshot entrySnapshot: snapshot.getChildren()) {
                    int key = Integer.parseInt(entrySnapshot.getKey());
                    Log.d(TAG, "retrieved key");
                    Log.d(TAG, "Key is "+key);
                    Log.d(TAG, "Value is "+entrySnapshot.getValue());
                    // Get the value as a map
                    Map<String, Object> map = (Map<String, Object>) entrySnapshot.getValue();

                    // Retrieve stickerID and stickerCount from the map
                    int stickerID = ((Long) Objects.requireNonNull(map.get("stickerID"))).intValue();
                    int stickerCount = ((Long) Objects.requireNonNull(map.get("stickerCount"))).intValue();

                    // Now you have stickerID and stickerCount, you can use them as needed
                    Log.d(TAG, "Sticker ID is " + stickerID);
                    Log.d(TAG, "Sticker count is " + stickerCount);
                    Log.d(TAG, "finish innerLoop");
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
        Log.d(TAG, "in init function");
        if(sentStickers != null) {
            for(SentSticker sentSticker: sentStickers) {
                Log.d(TAG, "sticker ID is: "+sentSticker.getSticker());
                Log.d(TAG, "sticker ID is: "+sentSticker.getCount());
            }
            //Initialize recycler view
            sentStickerRecyclerView = findViewById(R.id.sticker_recycler_view);
            sentStickerRecyclerView.setHasFixedSize(true);
            sentStickerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            sentStickersAdapter = new SentStickersAdapter(sentStickers, this);
            sentStickerRecyclerView.setAdapter(sentStickersAdapter);

        } else {
            Log.e(TAG,"user data is null");
        }
    }
}
