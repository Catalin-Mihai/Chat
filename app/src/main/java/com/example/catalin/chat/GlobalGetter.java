package com.example.catalin.chat;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class GlobalGetter extends AsyncTask<String, Integer, String>
{
    public static final String CONTENT_NOT_FOUND = "NOT_FOUND";
    public static final String CONTENT_NOT_INSERTED = "NOT_INSERTED";
    private HttpContentListener listener;
    private Serializable serializable;

    interface HttpContentListener {
        void ContentReceived(String response);
        void ContentNotFound();
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
        //Toast.makeText(context, "Mesaj trimis!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        ProcessResult(result);
    }

    public void setListener(HttpContentListener listener) {
        this.listener = listener;
    }

    private void ProcessResult(String jsonStr)
    {
        if(jsonStr == null)
        {
            listener.ContentNotFound();
            return;
        }
        String temp = jsonStr.replaceAll("\\s+","");
        Log.d("EROR", "." + temp + "..");
        if(temp.equals(CONTENT_NOT_FOUND))
        {
            listener.ContentNotFound();
        }
        else {
            listener.ContentReceived(jsonStr);
        }
    }

    public void setExtra(Serializable serializable)
    {
        this.serializable = serializable;
    }

    public Serializable getExtra()
    {
        return this.serializable;
    }

    /*public static <T> void ProcessContent(T object, String jsonStr)
    {
        if(object instanceof ArrayList) {
            try {

                JSONArray jsonArr = new JSONArray(jsonStr);
                if(jsonArr.length() == 0) return;
                for(int i = 0; i < jsonArr.length(); i++)
                {
                    JSONObject jsonObject = jsonArr.getJSONObject(i);
                    Contact contact = new Contact();
                    contact.setID(jsonObject.getInt("ID"));
                    contact.setGroup_ID(jsonObject.getInt("GrupID"));
                    contact.setName(jsonObject.getString("GrupName"));
                    object.add(contact);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }*/
}
