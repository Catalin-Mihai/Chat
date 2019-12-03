package com.example.catalin.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChatMenuActivity extends AppCompatActivity {

    User user;
    Contact contact;
    ArrayList<User> usersList;
    ListView users_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_menu);
        users_listview = (ListView) findViewById(R.id.participants_list);

        if(getSupportActionBar() != null)
            getSupportActionBar().hide();

        GetInfoFromPrevActivity(getIntent());
        GetContactUsers(contact);

    }

    void GetContactUsers(Contact contact)
    {
        GlobalGetter globalGetter = new GlobalGetter();
        globalGetter.setListener(new ContactUsersGetter());
        globalGetter.execute(APILinkBuilder.Build("getcontactusers.php", "groupid", Integer.toString(contact.getGroup_ID())));
    }

    class ContactUsersGetter implements GlobalGetter.HttpContentListener
    {

        @Override
        public void ContentReceived(String response) {

           // Log.e(ChatMenuActivity.class.getSimpleName(), response);
            usersList = new ArrayList<>();
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(response);
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    User user = new User();
                    user.setID(jsonObject.getInt("userID"));
                    user.setChatNickname(jsonObject.getString("Nickname"));

                    usersList.add(user);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Adaptare lista la noile date
            ContactUsersListAdapter contactUsersListAdapter = new ContactUsersListAdapter(ChatMenuActivity.this, usersList);
            users_listview.setAdapter(contactUsersListAdapter);
        }

        @Override
        public void ContentNotFound() {

        }
    }

    void GetInfoFromPrevActivity(Intent intent)
    {
        IntentInfo info = (IntentInfo) intent.getSerializableExtra("info");
        user = info.getUser();
        contact = info.getContact();
        Log.e(ChatMenuActivity.class.getSimpleName(), "info: " + user.getID() + "contactid: " + contact.getGroup_ID());
    }
}

