package com.example.catalin.chat;

import android.net.sip.SipSession;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.acl.LastOwnerException;

class UserGetter extends AsyncTask<String, Integer, String> {

    private static final String USER_NOT_FOUND = "NOT_FOUND";
    LogListener listener;

    interface LogListener {
        void UserLogged(User user);
        void UserNotFound();
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
        ProcessUser(result);
    }

    public void setListener(LogListener listener) {
        this.listener = listener;
    }

    private void ProcessUser(String jsonStr)
    {
        if(jsonStr == null)
        {
            listener.UserNotFound();
            return;
        }
        String temp = jsonStr.replaceAll("\\s+","");
        Log.d("EROR", "." + temp + "..");
        if(temp.equals(USER_NOT_FOUND))
        {
            listener.UserNotFound();
        }
        else {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                User user = new User();
                user.setID(jsonObject.getInt("ID"));
                user.setEmail(jsonObject.getString("Email"));
                user.setPassword(jsonObject.getString("Password"));
                user.setUserName("Nume_Test");
                listener.UserLogged(user);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
