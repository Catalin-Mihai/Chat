package com.example.catalin.chat;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MessagesPoster extends AsyncTask<String, Integer, String> {

    private static final String TAG = MessagesPoster.class.getSimpleName();

    /*private int userID;
    private String message;
    private String date;

    MessagesPoster(int userID, String message, String date)
    {
        this.userID = userID;
        this.message = message;
        this.date = date;
    }*/

    MessagesPoster(){}

    @Override
    protected String doInBackground(String... urls) {

        String response = null;
        HttpHandler sh = new HttpHandler();
        // Making a request to url and getting response
        Log.e("URL", urls[0]);
        return sh.makeServiceCall(urls[0]);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Toast.makeText(context, "Mesaj trimis!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        //ProcessMessages(result);
    }

    /*private void ProcessMessages(String jsonStr)
    {
        try {
            JSONArray jsonArr = new JSONArray(jsonStr);
            for(int i = 0; i < jsonArr.length();i++)
            {
                JSONObject jsonObject = jsonArr.getJSONObject(i);
                Message message = new Message();
                message.setID(jsonObject.getInt("ID"));
                message.setDate(jsonObject.getString("date"));
                message.setText(jsonObject.getString("text"));
                message.setUserID(jsonObject.getInt("userID"));
                chat.CreateLeftMessage(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/
}
