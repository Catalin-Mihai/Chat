package com.example.catalin.chat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity implements GlobalGetter.HttpContentListener, ContactDialog.ContactAdderListener{

    private RecyclerView recyclerView;
    private ArrayList<Contact> contactsList;
    private Context context;
    private User user;
    private ConstraintLayout new_contact_butt;
    private TextView contact_search_info;
    private DialogInfo dialogInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        //Log.e("TESTTEST", "ONCREATE");
        context = this;
        /*User user1 = new User(1, "Mihai", "asdas@adsasd", "123");
        User user2 = new User(2, "Catalin", "babas@adsasd", "5123");
        ArrayList<User> userArrayList = new ArrayList<User>();
        userArrayList.add(user1);
        userArrayList.add(user2);
        Contact contact = new Contact(1, "Group_Test_Name" ,userArrayList);
        contactsList = new ArrayList<Contact>();
        contactsList.add(contact);
        contactsList.add(contact);
        contactsList.add(contact);
        contactsList.add(contact);
        //contactsList = new ArrayList<Contact>();
        //contactsList.add(new Contact());*/
        contactsList = new ArrayList<Contact>();
        InitializeComponents();
        GetInfoFromPrevActivity(getIntent());
        try {
            LoadUserContactsList(user);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }



    void InitializeComponents()
    {

        //Dialog info
        dialogInfo = new DialogInfo(this, findViewById(R.id.contacts_mainlayout));

        new_contact_butt = findViewById(R.id.newcontact_butt);
        new_contact_butt.setOnClickListener(new NewContactButtonListener());
        recyclerView = findViewById(R.id.contacts_list);
        recyclerView.setHasFixedSize(true); //for performance
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter mAdapter = new ContactsAdapter(contactsList, new RecyclerItemClickListener());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new ContactsRecyclerSpaceItemDecoration(this, 8));
    }

    void GetInfoFromPrevActivity(Intent intent)
    {
        user = (User) intent.getSerializableExtra("userinfo");
        //Log.d("BABABA", Integer.toString(user.getID()));
    }

    void LoadUserContactsList(User user) throws UnsupportedEncodingException {

        String url = APILinkBuilder.Build("getcontacts.php", "userid", Integer.toString(user.getID()));
        GlobalGetter globalGetter = new GlobalGetter();
        globalGetter.setListener(this);
        globalGetter.execute(url);
    }

    public void ContactsLoaded(ArrayList<Contact> contacts) {
        //contactsList.clear();
        contactsList.clear();
        contactsList.addAll(contacts);
        Log.d( "asdas" ,contacts.get(0).getName());
        if(recyclerView.getAdapter() != null)
        {
            recyclerView.getAdapter().notifyItemRangeInserted(0, contactsList.size());
        }
    }

    public void ContactsNotFound() {

    }

    @Override
    public void ContentReceived(String response) {
        //contactsList =
        //Log.d( "asdas" ,contactsList.get(0).getName());
        if(response == null)
        {
            ContactsNotFound();
            return;
        }
        String temp = response.replaceAll("\\s+","");
        Log.d("EROR", "." + temp + "..");

        ArrayList<Contact> contacts = new ArrayList<>();
        try {
            JSONArray jsonArr = new JSONArray(response);
            if(jsonArr.length() == 0)
            {
                ContactsNotFound();
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
            ContactsLoaded(contacts);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ContentNotFound() {

    }

    //Called when user requested a valid contact addition
    @Override
    public void ContactRequested(int userid) {
        Log.e("TESTTTTTTT", String.valueOf(userid));

        //Create a new contact formed by the user and the requested user
        ContactInserterListener contactInserterListener = new ContactInserterListener();
        ArrayList<User> userArrayList = new ArrayList<User>();
        User user2 = new User(userid);
        userArrayList.add(user);
        userArrayList.add(user2);
        Contact contact = new Contact(1, "New Contact" ,userArrayList);
        contactInserterListener.SetContact(contact);

        GlobalGetter globalGetter = new GlobalGetter();
        globalGetter.setListener(contactInserterListener);
        try {
            //Insert the new contact into Database
            globalGetter.execute(APILinkBuilder.Build("insertcontact.php", "name", contact.getName(), "user1",
                    String.valueOf(userid), "user2", String.valueOf(user.getID())));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //if(globalGetter.getStatus() == AsyncTask.Status.FINISHED)//Nu mai executa ceva
        //globalGetter.execute();
    }

    private class ContactInserterListener implements GlobalGetter.HttpContentListener
    {

        private Contact contact;

        void SetContact(Contact contact)
        {
            this.contact = contact;
        }

        @Override
        public void ContentReceived(String response) {
            response = response.trim();
            if(response.equals(GlobalGetter.CONTENT_NOT_INSERTED))
            {
                dialogInfo.ShowDialogInfo("A problem has occured while adding your new contact. Please send us this error!", DialogInfo.InfoType.INFO_ERROR);
            }
            else
            {
                dialogInfo.ShowDialogInfo("Your contact has been succesfully added!", DialogInfo.InfoType.INFO_SUCCESS);
                contact.setGroup_ID(Integer.parseInt(response));
                contact.setName("Group id " + contact.getGroup_ID());
                contactsList.add(0, contact);
                if(recyclerView.getAdapter() != null)
                {
                    recyclerView.getAdapter().notifyItemInserted(0);
                    //recyclerView.getAdapter().notifyDataSetChanged();
                    //recyclerView.getAdapter().notifyItemRangeInserted(0, contactsList.size());
                }
            }
        }

        @Override
        public void ContentNotFound() {

        }
    }

    private class NewContactButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            ContactDialog dialog = new ContactDialog();
            dialog.setListener(ContactsActivity.this);
            dialog.show(fragmentManager, "new_contact_dialog");
        }
    }

    private class RecyclerItemClickListener implements ContactsAdapter.OnItemClickListener
    {
        @Override
        public void OnItemClick(Contact contact) {
            //E momentul in care trimitem mai departe la activitatea cu chat-ul/mesajele grupului respectiv
            Intent intent = new Intent(ContactsActivity.this, MainActivity.class);
            intent.putExtra("info", new IntentInfo(user, contact));
            //Log.e("Contact id: ", contact.)
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
