package com.example.catalin.chat;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.NO_ID;

class Chat{

    //private int lastMessageId;
    //private ArrayList<Message> mData;

    private static Chat instance = null;
    private ChatListener listener;
    private ArrayList<Message> mData;

    private Chat(){
        mData = new ArrayList<Message>();
    }

    interface ChatListener {
        void DBMessagesReceived(ArrayList<Message> newData);
        void MessagePosted(Message message);
    }

    public void setListener(ChatListener listener) {
        this.listener = listener;
    }

    public static Chat getInstance()
    {

        if(instance == null)
            instance = new Chat();
        return instance;
    }

    public void ResetMessagesArray()
    {
        mData.clear();
    }

    public ArrayList<Message> getMessagesArray()
    {
        return mData;
    }

    private void CreateMessagesFromDB(ArrayList<Message> newData)
    {
        listener.DBMessagesReceived(newData);
    }

    void GetMessages(int count, int grup_ID) throws UnsupportedEncodingException {
        /*String url = "https://catalinmihai.000webhostapp.com/rest/RestController.php?getMeth=grupID&messagesCount="
                + URLEncoder.encode(Integer.toString(count), "UTF-8")
                + "&grupID=" + URLEncoder.encode(Integer.toString(grup_ID), "UTF-8");*/
        String url = APILinkBuilder.Build("getmessages.php", "getMeth", "grupID", "messagesCount", Integer.toString(count),
                "grupID", Integer.toString(grup_ID));
        new MessagesGetter().execute(url);
    }

    private void PostMessage(Message message) throws UnsupportedEncodingException {
        /*String url = "https://catalinmihai.000webhostapp.com/rest/insert.php?senderID="
        + URLEncoder.encode(Integer.toString(message.getSenderID()), "UTF-8")
        + "&text=" + URLEncoder.encode(message.getText(), "UTF-8")
        + "&date=" + URLEncoder.encode(message.getDate(), "UTF-8")
        + "&grupID=" + URLEncoder.encode(Integer.toString(message.getGrupID()), "UTF-8");*/
        String url = APILinkBuilder.Build("insert.php", "senderID", Integer.toString(message.getSenderID()),
                "text", message.getText(), "date", message.getDate(), "grupID", Integer.toString(message.getGrupID()));
        new MessagesPoster().execute(url);
    }

    void CreateMessage(Message message)
    {
        try {
            PostMessage(message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        listener.MessagePosted(message);
    }


    void ProcessDBMessages(String jsonStr)
    {
        ArrayList<Message> newData = new ArrayList<>();
        try {
            JSONArray jsonArr = new JSONArray(jsonStr);
            for(int i = 0; i < jsonArr.length();i++)
            {
                JSONObject jsonObject = jsonArr.getJSONObject(i);
                Message message = new Message();
                message.setID(jsonObject.getInt("ID"));
                message.setDate(jsonObject.getString("date"));
                message.setText(jsonObject.getString("text"));
                message.setSenderID(jsonObject.getInt("senderID"));
                message.setGrupID(jsonObject.getInt("grupID"));
                Log.d("DASAS", Integer.toString(message.getGrupID()));
                if(message.getSenderID() == 1)
                    //Trimis de utilizator
                    message.setOrientation(Message.ORIENTATION_RIGHT);
                else {
                    Log.d("ASD", "LEFY");
                    message.setOrientation(Message.ORIENTATION_LEFT);
                }
                newData.add(message);
                //chat.CreateLeftMessage(message);
            }
            //chat.setMessages(mData);
            this.CreateMessagesFromDB(newData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
