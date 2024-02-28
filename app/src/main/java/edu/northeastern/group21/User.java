package edu.northeastern.group21;

import java.net.HttpCookie;
import com.google.firebase.database.DataSnapshot;


public class User {
    public String userName;

    public User(String userName) {
        this.userName = userName;
    }

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

