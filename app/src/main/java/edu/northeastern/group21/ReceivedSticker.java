package edu.northeastern.group21;

public class ReceivedSticker {
    private int stickerID;
    private String receivedFrom;
    private String receivedDate;

    public ReceivedSticker() {
    }

    public ReceivedSticker(int stickerID, String receivedFrom, String receivedDate) {
        this.stickerID = stickerID;
        this.receivedFrom = receivedFrom;
        this.receivedDate = receivedDate;
    }

    public int getStickerID() {
        return stickerID;
    }

    public void setStickerID(int stickerID) {
        this.stickerID = stickerID;
    }

    public String getReceivedFrom() {
        return receivedFrom;
    }

    public void setReceivedFrom(String receivedFrom) {
        this.receivedFrom = receivedFrom;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }
}
