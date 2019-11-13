package com.example.catalin.chat;
import android.support.annotation.NonNull;

public class Message {

    public static final int ORIENTATION_LEFT = 0;
    public static final int ORIENTATION_RIGHT = 1;
    private int senderID;
    private int grupID;
    private String text;
    private String date;
    private int ID;
    private int orientation;

    Message()
    {
        this.ID = -1;
        this.text = "null";
        this.date = null;
        this.senderID = -1;
    }

    Message(int ID, int senderID, String date, String text)
    {
        this.ID = ID;
        this.text = text;
        this.date = date;
        this.senderID = senderID;
    }

    int getOrientation(){
        return orientation;
    }

    void setOrientation(int or){
        orientation = or;
    }

    String getDate() {
        return date;
    }

    int getSenderID() {
        return senderID;
    }

    public int getID() {
        return ID;
    }

    String getText() {
        return text;
    }

    void setDate(String date) {
        this.date = date;
    }

    void setText(String text) {
        this.text = text;
    }

    void setID(int ID) {
        this.ID = ID;
    }

    void setSenderID(int userID) {
        this.senderID = userID;
    }

    public int getGrupID() {
        return grupID;
    }

    public void setGrupID(int grupID) {
        this.grupID = grupID;
    }
}

