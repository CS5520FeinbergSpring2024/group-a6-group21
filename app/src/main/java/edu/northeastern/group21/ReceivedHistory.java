package edu.northeastern.group21;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ReceivedHistory extends AppCompatActivity {

    private String curUserName;
    private ReceivedStickersAdapter receivedStickersAdapter;
    private RecyclerView receivedStickerRecyclerView;
    private List<ReceivedSticker> receivedStickers;
    private FirebaseDatabase mDatabase;
    private final String TAG = "-----ReceivedHistory----";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_list);

        receivedStickers = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance();
        curUserName = getIntent().getStringExtra("userName");

        //Retrieve current user data
        retrieveUserReceivedHistory();
        Log.v(TAG,"retrieveUserReceivedHistory Function Ran");
    }

    protected void retrieveUserReceivedHistory() {
        mDatabase.getReference().child("users").child(curUserName).child("receivedStickers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot entrySnapshot: snapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) entrySnapshot.getValue();
                    int stickerID = ((Long) Objects.requireNonNull(map.get("stickerID"))).intValue();
                    String receivedFrom = (String) Objects.requireNonNull(map.get("receivedFrom"));
                    String receivedDate = (String) Objects.requireNonNull(map.get("receivedDate"));
                    receivedStickers.add(new ReceivedSticker(stickerID, receivedFrom, receivedDate));
                }
                init();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    protected void init() {
        if(receivedStickers != null) {
            receivedStickerRecyclerView = findViewById(R.id.sticker_recycler_view);
            receivedStickerRecyclerView.setHasFixedSize(true);
            receivedStickerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            receivedStickersAdapter = new ReceivedStickersAdapter(receivedStickers, this);
            receivedStickerRecyclerView.setAdapter(receivedStickersAdapter);
        }
    }
}
