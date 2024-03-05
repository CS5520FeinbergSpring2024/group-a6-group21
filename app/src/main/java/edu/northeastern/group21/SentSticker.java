package edu.northeastern.group21;

import com.google.firebase.database.PropertyName;

public class SentSticker {
    private int stickerID;
    private int stickerCount;

    public SentSticker(){

    }

    public SentSticker(int sticker, int count) {
        this.stickerID = sticker;
        this.stickerCount = count;
    }

    public int getStickerID() {
        return stickerID;
    }

    public void setStickerID(int stickerID) {
        this.stickerID = stickerID;
    }

    public int getStickerCount() {
        return stickerCount;
    }

    public void setStickerCount(int stickerCount) {
        this.stickerCount = stickerCount;
    }
}
