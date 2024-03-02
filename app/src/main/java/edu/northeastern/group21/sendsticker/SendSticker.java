package edu.northeastern.group21.sendsticker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import edu.northeastern.group21.Login;
import edu.northeastern.group21.R;
import edu.northeastern.group21.ReceivedHistory;
import edu.northeastern.group21.ReceivedSticker;
import edu.northeastern.group21.SentHistory;
import edu.northeastern.group21.SentSticker;
import edu.northeastern.group21.User;


public class SendSticker extends AppCompatActivity {

    private final static String TAG = "----SendSticker----";

    private DatabaseReference usersRef;
    private List<User> onlineUserList = new ArrayList<>();
    private Spinner onlineUserSpinner;

    private String currentUserName = "";
    private User selectedUser = null;

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;
    private TextView selectStickerTxt;

    private Integer selectedImageId = null;
    private String selectImageName;

    private Button btnSentHistory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sticker);


        // test firebase connection 1
//        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("message");
//        System.out.println("send sticker oncreate 1");
//        myRef.setValue("Hello, World!");
        usersRef = FirebaseDatabase.getInstance().getReference("users");

        // get current username
        currentUserName = getIntent().getStringExtra("userName");

        //spinner
        onlineUserSpinner = findViewById(R.id.spinnerOnlineUser);
        getOnlineUsers();
        bindListenerToSpinner();

        //show stickers
        selectStickerTxt = findViewById(R.id.selectedResultText);
        imageView1 = findViewById(R.id.imageView11);
        imageView2 = findViewById(R.id.imageView12);
        imageView3 = findViewById(R.id.imageView13);
        imageView4 = findViewById(R.id.imageView21);
        imageView5 = findViewById(R.id.imageView22);
        imageView6 = findViewById(R.id.imageView23);

        bindListenerToImageView();



    }

    public void toSendSticker(View view) {
//        Log.d(TAG, "toSendSticker: current user: " + currentUserName + ", sticker: " + selectedImageId + ", user: " + selectedUser.getUserName());
        if (selectedImageId == null) {
            sendToast("Please select a sticker by click a picture.");
        } else if (currentUserName == null || currentUserName.length() == 0) {
            sendToast("Please log in.");
        } else if (selectedUser == null) {
            sendToast("Please choose a user");
        } else {
//            // create a new sent record
            DatabaseReference sentStickersRef = usersRef.child(currentUserName).child("sentStickers");
            sentStickersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int stickerCount = 0;
                    for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
                        int key = Integer.parseInt(entrySnapshot.getKey());
                        Log.d(TAG, "key:" + key);
                        if (key == selectedImageId) {
//                            // java.lang.ClassCastException: java.util.HashMap cannot be cast to edu.northeastern.group21.SentSticker
//                            SentSticker sentSticker = (SentSticker) entrySnapshot.getValue();
//                            stickerCount = sentSticker.getStickerCount();
//                            Log.d(TAG, "sentSticker id:" + sentSticker.getStickerID());
//                            Log.d(TAG, "sentSticker: count = " + sentSticker.getStickerCount());

                            Map<String, Object> map = (Map<String, Object>) entrySnapshot.getValue();
                            stickerCount = ((Long) Objects.requireNonNull(map.get("stickerCount"))).intValue();
                            Log.d(TAG, "original count:" + stickerCount);
                        }
                    }

                    stickerCount++;
                    sentStickersRef.child(selectedImageId.toString()).child("stickerID").setValue(selectedImageId);
                    sentStickersRef.child(selectedImageId.toString()).child("stickerCount").setValue(stickerCount);

                    // Get the current count of records
//                    SentSticker sentSticker = dataSnapshot.getValue(SentSticker.class);
//                    Log.d(TAG, "onDataChange: sentSticker:" + sentSticker.getStickerID());
//                    Log.d(TAG, "onDataChange: count = " + sentSticker.getStickerCount());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                }
            });

            // create a new received record
            DatabaseReference receiverRef = usersRef.child(selectedUser.getUserName());
            receiverRef.child("receivedStickers").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    long recordCount = dataSnapshot.getChildrenCount();
                    long newRecordId = recordCount + 1;

                    // create a new receivedSticker object
                    Date currentDate = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String formattedDate = dateFormat.format(currentDate);
                    ReceivedSticker receivedSticker = new ReceivedSticker(selectedImageId, currentUserName, formattedDate);

                    // Set the new record under the "user2" node
                    receiverRef.child("receivedStickers").child(String.valueOf(newRecordId)).setValue(receivedSticker);
                    sendToast("Send Successfully");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                }
            });


//            Intent intent = new Intent(SendSticker.this, SentHistory.class);
//            intent.putExtra("userName", currentUserName);
//            startActivity(intent);

        }
    }

    private void getOnlineUsers() {
        // Query for online users
        Query onlineUsersQuery = usersRef.orderByChild("online").equalTo(true);

        onlineUsersQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("query");
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user != null) {
                        onlineUserList.add(user);
                    }
                }

                // Populate the Spinner with the retrieved data
                ArrayAdapter<User> adapter = new ArrayAdapter<User>(SendSticker.this, android.R.layout.simple_spinner_item, onlineUserList) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setText(onlineUserList.get(position).getUserName()); // Display the user name
                        return textView;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                        textView.setText(onlineUserList.get(position).getUserName()); // Display the user name
                        return textView;
                    }
                };
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                onlineUserSpinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });

    }

    private void bindListenerToSpinner() {
        onlineUserSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected item from the spinner
                selectedUser = (User) parentView.getItemAtPosition(position);

                // Do something with the selected item
                Log.d("Spinner", "Selected user: " + selectedUser.getUserName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle case where nothing is selected (optional)
            }
        });
    }

    private void bindListenerToImageView() {
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event here
                // For example, open a new activity or perform some action
                selectedImageId = 1;
                selectStickerTxt.setText("Chosen sticker: mexico");
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event here
                // For example, open a new activity or perform some action
                selectedImageId = 2;
                selectStickerTxt.setText("Chosen sticker: sahara");
                selectImageName = "sahara";
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event here
                // For example, open a new activity or perform some action
                selectedImageId = 3;
                selectStickerTxt.setText("Chosen sticker: sydney");
                selectImageName = "sydney";
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event here
                // For example, open a new activity or perform some action
                selectedImageId = 4;
                selectStickerTxt.setText("Chosen sticker: toronto");
                selectImageName = "toronto";
            }
        });

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event here
                // For example, open a new activity or perform some action
                selectedImageId = 5;
                selectStickerTxt.setText("Chosen sticker: turkey");
                selectImageName = "turkey";
            }
        });

        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event here
                // For example, open a new activity or perform some action
                selectedImageId = 6;
                selectStickerTxt.setText("Chosen sticker: washington");
                selectImageName = "washington";
            }
        });
    }

    public void onClickSentHistory(View view) {
        // Intent to start next activity, passing the username as an extra
        Intent intent = new Intent(SendSticker.this, SentHistory.class);
        intent.putExtra("userName", currentUserName);
        startActivity(intent);
    }

    public void onClickReceivedHistory(View view) {
        // Intent to start next activity, passing the username as an extra
        Intent intent = new Intent(SendSticker.this, ReceivedHistory.class);
        intent.putExtra("userName", currentUserName);
        startActivity(intent);
    }

    private void sendToast(String msg) {
        Toast.makeText(SendSticker.this, (CharSequence) msg, Toast.LENGTH_LONG).show();
    }

}