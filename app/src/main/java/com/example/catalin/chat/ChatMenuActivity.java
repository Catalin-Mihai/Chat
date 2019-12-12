package com.example.catalin.chat;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ChatMenuActivity extends AppCompatActivity implements ContactDialog.ContactAdderListener{

    User user;
    Contact contact;
    ArrayList<User> usersList; //list of users
    ArrayList<ChatMenuItem> menuItems; //list that contains the all ListView items
    ListView menuListView;
    Button add_participant_button;
    DialogInfo dialogInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_menu);
        menuListView = (ListView) findViewById(R.id.menu_list);
        menuItems = new ArrayList<>();
        //add_participant_button = (Button) findViewById(R.id.add_participant);
        //add_participant_button.setOnClickListener(new AddParticipantButtonListener());
        usersList = new ArrayList<>();

        dialogInfo = new DialogInfo(ChatMenuActivity.this, findViewById(R.id.main_layout));

        if(getSupportActionBar() != null)
            getSupportActionBar().hide();

        GetInfoFromPrevActivity(getIntent());
        GetContactUsers(contact);

    }

    private void OnAdderButtonClick() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        ContactDialog dialog = new ContactDialog();
        dialog.setListener(ChatMenuActivity.this);
        dialog.show(fragmentManager, "new_contact_dialog");
    }

    private void OnUserMoreButtonClick(User onUser){
        Log.e("BUTTON", "MORE BUTTON CLICKED: " + onUser.getID());
    }

    @Override
    public void ContactRequested(int userid) {
        //callback called by ContactDialog submit button
        //received a valid user id. Insertion must be done here

        //check if userid is already in the group
        for(User user: usersList)
        {
            if(user.getID() == userid)
            {
                dialogInfo.ShowDialogInfo("The user is already in this group", DialogInfo.InfoType.INFO_ERROR);
                return;
            }
        }

        //insertion
        GlobalGetter userInserter = new GlobalGetter();
        userInserter.setListener(new UserInsertionListener(userid));
        userInserter.execute(APILinkBuilder.Build("insertuserintogroup.php", "userid",
                String.valueOf(userid), "groupid", String.valueOf(contact.getGroup_ID()), "nickname", User.DEFAULT_NICKNAME));
    }

    private void MakeMenu()
    {
        menuItems = new ArrayList<>();

        //Users list
        menuItems.add(new ChatMenuItem(R.layout.chat_menu_userslist, new UserListHandler()));
        //Participant add button
        menuItems.add(new ChatMenuItem(R.layout.participants_adder_button, new AddParticipantButtonHandler()));

    }

    private class UserInsertionListener implements GlobalGetter.HttpContentListener
    {

        private int userid;

        UserInsertionListener(int userid)
        {
            this.userid = userid;
        }

        @Override
        public void ContentReceived(String response) {

            //Fail
            if(response.equals(GlobalGetter.CONTENT_NOT_INSERTED))
            {
                dialogInfo.ShowDialogInfo("An error occured while adding the user", DialogInfo.InfoType.INFO_ERROR);
                return;
            }

            //Success. Add user in the current user list
            User user = new User();
            user.setChatNickname(User.DEFAULT_NICKNAME);
            user.setID(userid);
            usersList.add(user);
            //menuListView.deferNotifyDataSetChanged(); //notify the ListView. Maybe not needed?
        }

        @Override
        public void ContentNotFound() {

        }
    }

    void GetContactUsers(Contact contact)
    {
        GlobalGetter globalGetter = new GlobalGetter();
        globalGetter.setListener(new ContactUsersGetter());
        globalGetter.execute(APILinkBuilder.Build("getgroupusers.php", "groupid", Integer.toString(contact.getGroup_ID())));
    }

    class ContactUsersGetter implements GlobalGetter.HttpContentListener
    {

        @Override
        public void ContentReceived(String response) {

           // Log.e(ChatMenuActivity.class.getSimpleName(), response);
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(response);
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    User user = new User();
                    user.setID(jsonObject.getInt("userID"));
                    user.setChatNickname(jsonObject.getString("Nickname"));
                    Log.e("USER", " " + user.getID());
                    usersList.add(user);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Adaptare lista la noile date
            MakeMenu();
            ChatMenuListAdapter chatMenuListAdapter = new ChatMenuListAdapter(ChatMenuActivity.this, menuItems);
            menuListView.setAdapter(chatMenuListAdapter);
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

    //Item handling classes

    class UserListHandler extends ItemHandler<UserListHandler.ViewHolder>{

        class ViewHolder {
            ListView listView;
        }

        UserListHandler() {
            super();
        }

        @Override
        protected UserListHandler.ViewHolder getViews(View rootView){ //called when view is bound
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.listView = rootView.findViewById(R.id.chatmenu_userslist);
            return viewHolder;
        }

        @Override
        protected void handleLogic() { //called after getViews()
            Log.e("SIZE ", " " + usersList.size());
            viewHolder.listView.setAdapter(new ChatMenuUsersListAdapter(ChatMenuActivity.this,
                  usersList, ChatMenuActivity.this::OnUserMoreButtonClick));
        }

    }

    class AddParticipantButtonHandler extends ItemHandler<AddParticipantButtonHandler.ViewHolder> {

        class ViewHolder{
            Button button;
        }

        AddParticipantButtonHandler() {
            super();
        }

        @Override
        protected AddParticipantButtonHandler.ViewHolder getViews(View rootView){ //called when view is bound
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.button = rootView.findViewById(R.id.adder_button);
            return viewHolder;
        }

        @Override
        protected void handleLogic() { //called after getViews() is called
            viewHolder.button.setOnClickListener(v -> OnAdderButtonClick());
        }

    }
}

