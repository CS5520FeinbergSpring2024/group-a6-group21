package edu.northeastern.group21.sendsticker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.group21.R;
import edu.northeastern.group21.User;


public class SendSticker extends AppCompatActivity {

    private DatabaseReference usersRef;
    private List<User> onlineUserList = new ArrayList<>();
    private Spinner onlineUserSpinner;
    private User selectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sticker);
//        FirebaseApp.initializeApp(this);

        onlineUserSpinner = findViewById(R.id.spinnerOnlineUser);

        // test firebase connection 1
//        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("message");
//        System.out.println("send sticker oncreate 1");
//        myRef.setValue("Hello, World!");


        //spinner
        getOnlineUsers();

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

        //recyclerView

    }

    public void onclick(View view) {
        int theId = view.getId();

//        if (theId == R.id.buttonSend) {
//            // Click for Group Info
//            Intent intent = new Intent(SentSticker.this, GroupInfo.class);
//            startActivity(intent);
//        }
    }

    private void getOnlineUsers() {
        // Assuming you have a DatabaseReference object pointing to the location of your data in Firebase
        usersRef = FirebaseDatabase.getInstance().getReference("users");

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
//                ArrayAdapter<String> adapter = new ArrayAdapter<>(SendSticker.this, android.R.layout.simple_spinner_item, dataList);
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

//    private void testDBConnection() {
//        System.out.println("test conn");
//        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
//        String testValue = "Hello Firebase!";
//
//        // Write data to a test location in the database
//        databaseRef.child("test").setValue(testValue)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        // Write operation successful
//                        Log.d("FirebaseTest", "Data written successfully!");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Write operation failed
//                        Log.e("FirebaseTest", "Error writing data to Firebase: " + e.getMessage());
//                    }
//                });
//
//    }
}