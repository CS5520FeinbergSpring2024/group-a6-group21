package edu.northeastern.group21;

import com.google.firebase.database.PropertyName;

public class SentSticker {
    private int stickerID;
    private int stickerCount;

    public SentSticker(int sticker, int count) {
        this.stickerID = sticker;
        this.stickerCount = count;
    }

    public int getSticker() {
        return stickerID;
    }

    public void setSticker(int sticker) {
        this.stickerID = sticker;
    }

    public int getCount() {
        return stickerCount;
    }

    public void setCount(int count) {
        this.stickerCount = count;
    }
}
