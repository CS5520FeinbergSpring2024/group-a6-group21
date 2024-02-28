package edu.northeastern.group21;

import com.google.firebase.database.PropertyName;

import java.util.List;

public class User {
    @PropertyName("userName")
    private String userName;
    @PropertyName("receivedStickers")
    private List<ReceivedSticker> receivedStickers;
    @PropertyName("sentStickers")
    private List<SentSticker> sentStickers;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String userName) {
        this.userName = userName;
    }

    public User(String userName, List<ReceivedSticker> receivedStickers, List<SentSticker> sentStickers) {
        this.userName = userName;
        this.receivedStickers = receivedStickers;
        this.sentStickers = sentStickers;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<ReceivedSticker> getReceivedStickers() {
        return receivedStickers;
    }

    public void setReceivedStickers(List<ReceivedSticker> receivedStickers) {
        this.receivedStickers = receivedStickers;
    }

    public List<SentSticker> getSentStickers() {
        return sentStickers;
    }

    public void setSentStickers(List<SentSticker> sentStickers) {
        this.sentStickers = sentStickers;
    }
}
