package com.example.catalin.chat;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

class UserInserter extends AsyncTask<String, Integer, String> {

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
    }
}
