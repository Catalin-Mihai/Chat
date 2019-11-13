package com.example.catalin.chat;

import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/*public class ContactsGetter implements GlobalGetter.HttpContentListener{

    GlobalGetter globalGetter;

    ContactsGetter()
    {
        globalGetter = new GlobalGetter();
    }

    @Override
    public void ContentReceived(String response) {

    }

    @Override
    public void ContentNotFound() {

    }

    public void setListener(GlobalGetter.HttpContentListener listener)
    {
        globalGetter.setListener(this);
    }

    public void execute()
    {
        globalGetter.execute(new ContactsLinkBuilder().Build()...);
    }
}*/

class ContactsGetter extends AsyncTask<String, Integer, String> {

    private static final String CONTACTS_NOT_FOUND = "NOT_FOUND";
    LogListener listener;

    interface LogListener {
        void ContactsLoaded(ArrayList<Contact> contacts);
        void ContactsNotFound();
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

    public void setListener(LogListener listener) {
        this.listener = listener;
    }

    private void ProcessResult(String jsonStr)
    {
        if(jsonStr == null)
        {
            listener.ContactsNotFound();
            return;
        }
        String temp = jsonStr.replaceAll("\\s+","");
        Log.d("EROR", "." + temp + "..");
        if(temp.equals(CONTACTS_NOT_FOUND))
        {
            listener.ContactsNotFound();
        }
        else {

            ArrayList<Contact> contacts = new ArrayList<>();
            try {
                JSONArray jsonArr = new JSONArray(jsonStr);
                if(jsonArr.length() == 0)
                {
                    listener.ContactsNotFound();
                    return;
                }
                for(int i = 0; i < jsonArr.length();i++)
                {
                    JSONObject jsonObject = jsonArr.getJSONObject(i);

                    Contact contact = new Contact();
                    contact.setID(jsonObject.getInt("ID"));
                    contact.setGroup_ID(jsonObject.getInt("GrupID"));
                    contact.setName(jsonObject.getString("GrupName"));
                    contacts.add(contact);
                }
                listener.ContactsLoaded(contacts);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
