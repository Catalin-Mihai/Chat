package com.example.catalin.chat;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MessagesGetter extends AsyncTask<String, Integer, String> {

    private static final String TAG = MessagesGetter.class.getSimpleName();
    private Chat chat;

    MessagesGetter()
    {
        chat = Chat.getInstance();
    }

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
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Chat chat = Chat.getInstance();
        chat.ProcessDBMessages(result);
    }
}
