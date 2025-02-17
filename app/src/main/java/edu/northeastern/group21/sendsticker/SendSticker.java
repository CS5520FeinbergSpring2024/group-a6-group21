package edu.northeastern.group21.sendsticker;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    private List<String> allUserName = new ArrayList<>();
    private Spinner onlineUserSpinner;

    private String currentUserName = "";
//    private User selectedUser = null;
    private String selectedUserName = "";

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;
    private TextView selectStickerTxt;

    private Integer selectedImageId = null;
    private String selectImageName;

    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static final String CHANNEL_NAME = "CHANNEL_NAME";
    private static final String CHANNEL_DESCRIPTION = "CHANNEL_DESCRIPTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sticker);

        usersRef = FirebaseDatabase.getInstance().getReference("users");

        // get current username
        currentUserName = getIntent().getStringExtra("userName");

        //spinner
        onlineUserSpinner = findViewById(R.id.spinnerOnlineUser);
//        getOnlineUsers();
        getAllUsers();
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
        } else if (selectedUserName == null || selectedUserName.length() == 0) {
            sendToast("Please choose a user");
        } else {
            // sent a sticker message to Firebase
            sendStickerToUserB(selectedUserName, selectedImageId, selectImageName);
            // Assuming a successful send is displayed here
            sendToast("Sticker sent successfully.");
            //            // create a new sent record
            DatabaseReference sentStickersRef = usersRef.child(currentUserName).child("sentStickers");
            sentStickersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int stickerCount = 0;
                    for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
                        int key = Integer.parseInt(entrySnapshot.getKey());
                        if (key == selectedImageId) {
                            Map<String, Object> map = (Map<String, Object>) entrySnapshot.getValue();
                            stickerCount = ((Long) Objects.requireNonNull(map.get("stickerCount"))).intValue();
                            Log.d(TAG, "original count:" + stickerCount);
                        }
                    }
                    stickerCount++;
                    sentStickersRef.child(selectedImageId.toString()).child("stickerID").setValue(selectedImageId);
                    sentStickersRef.child(selectedImageId.toString()).child("stickerCount").setValue(stickerCount);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                }
            });
            // create a new received record
            DatabaseReference receiverRef = usersRef.child(selectedUserName);
            receiverRef.child("receivedStickers").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    long recordCount = dataSnapshot.getChildrenCount();
                    long newRecordId = recordCount + 1;
                    Date currentDate = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String formattedDate = dateFormat.format(currentDate);
                    ReceivedSticker receivedSticker = new ReceivedSticker(selectedImageId, currentUserName, formattedDate);

                    receiverRef.child("receivedStickers").child(String.valueOf(newRecordId)).setValue(receivedSticker);
                    sendToast("Send Successfully");

                    // Assume selectImageName is the name of the sticker i want to display in the notification
                    sendNotification(selectImageName);
                    sendToast("Send Successfully");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }
    }
    private void getAllUsers() {
        Query onlineUsersQuery = usersRef.orderByChild("userName");
        onlineUsersQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) userSnapshot.getValue();
                    String userName = (Objects.requireNonNull(map.get("userName"))).toString();
                    allUserName.add(userName);
//                    Log.d(TAG, "username: " + userName);
                }
                // Populate the Spinner with the retrieved data
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(SendSticker.this, android.R.layout.simple_spinner_item, allUserName) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setText(allUserName.get(position)); // Display the user name
                        return textView;
                    }
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                        textView.setText(allUserName.get(position)); // Display the user name
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
                selectedUserName = (String) parentView.getItemAtPosition(position);
                Log.d("Spinner", "Selected userName: " + selectedUserName);
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
                selectedImageId = 1;
                selectStickerTxt.setText("Chosen sticker: mexico");
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImageId = 2;
                selectStickerTxt.setText("Chosen sticker: sahara");
                selectImageName = "sahara";
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImageId = 3;
                selectStickerTxt.setText("Chosen sticker: sydney");
                selectImageName = "sydney";
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImageId = 4;
                selectStickerTxt.setText("Chosen sticker: toronto");
                selectImageName = "toronto";
            }
        });
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImageId = 5;
                selectStickerTxt.setText("Chosen sticker: turkey");
                selectImageName = "turkey";
            }
        });
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImageId = 6;
                selectStickerTxt.setText("Chosen sticker: washington");
                selectImageName = "washington";
            }
        });
    }
    public void onClickSentHistory(View view) {
        Intent intent = new Intent(SendSticker.this, SentHistory.class);
        intent.putExtra("userName", currentUserName);
        startActivity(intent);
    }
    public void onClickReceivedHistory(View view) {
        Intent intent = new Intent(SendSticker.this, ReceivedHistory.class);
        intent.putExtra("userName", currentUserName);
        startActivity(intent);
    }
    private void sendToast(String msg) {
        Toast toast = Toast.makeText(SendSticker.this, msg, Toast.LENGTH_LONG);
        toast.show();
    }
    private void sendStickerToUserB(String receiverUserId, int stickerId, String stickerName) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("stickers").child(receiverUserId);
        String key = dbRef.push().getKey();

        HashMap<String, Object> stickerInfo = new HashMap<>();
        stickerInfo.put("from", currentUserName);
        stickerInfo.put("stickerID", stickerId);
        stickerInfo.put("stickerName", stickerName);
        stickerInfo.put("timestamp", System.currentTimeMillis());

        if (key != null) {
            dbRef.child(key).setValue(stickerInfo)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Sticker sent successfully."))
                    .addOnFailureListener(
                            e -> Log.d(TAG, "Failed to send sticker.", e));
        }
    }


    /**
     * Create and show a notification for a newly sent sticker message
     */
    private void sendNotification(String stickerName) {
        Intent intent = new Intent(this, SendSticker.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Create the pending intent to launch the activity
        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                1410, intent, PendingIntent.FLAG_ONE_SHOT);

        // Create the notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.frame1) // Applying small Sticker Resources
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.round_circle_24)) // Applying Large Icon Resources
                .setContentTitle("You have a new sticker!")
                .setContentText("Sticker name: " + stickerName) // Use Sticker Name
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);

        // Set the notification channel and build the notification
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES .O){
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.setDescription(CHANNEL_DESCRIPTION);
                notificationManager.createNotificationChannel(notificationChannel);
            }
            notificationManager.notify(0, builder.build());
        }
    }
}