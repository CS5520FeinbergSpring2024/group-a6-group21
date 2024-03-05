package edu.northeastern.group21;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class NotificationListenerService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String userId = intent.getStringExtra("userId"); // Make sure to pass the userId when starting the service
        DatabaseReference notificationRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("notifications");

        notificationRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    NotificationData notificationData = dataSnapshot.getValue(NotificationData.class);
                    if (notificationData != null) {
                        showNotification(notificationData);
                    }
                }
            }

        });

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null; // No binding provided
    }

    public class NotificationData {
        private int stickerID;
        private String fromUserName;
        private String dateSent;

        public NotificationData() {

        }
        public int getStickerID() {
            return stickerID;
        }

        public void setStickerID(int stickerID) {
            this.stickerID = stickerID;
        }

        public void setStickerId(int stickerID) {
            this.stickerID = stickerID;
        }

        public String getFromUserName() {
            return fromUserName;
        }

        public void setFromUserName(String fromUserName) {
            this.fromUserName = fromUserName;
        }

        public String getDataSent() {
            return dateSent;
        }

        public void setDateSent(String dateSent) {
            this.dateSent = dateSent;
        }

        public String getTitle() {
            return "Some title";
        }

        public String getMessage() {
            return "Some message";
        }
    }

    private void showNotification(NotificationData notificationData) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(notificationData.getTitle())
                .setContentText(notificationData.getMessage())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(new Random().nextInt(), builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.default_notification_channel_id), name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}