package com.example.catalin.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Chat.ChatListener {

    Context context;
    private RecyclerView recyclerView;
    private EditText editText;
    private RecyclerView.Adapter mAdapter;
    private InputTextHandler inputTextHandler;
    private Contact contact;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        Chat.getInstance().setListener(this);

        GetInfoFromPrevActivity(getIntent());
        InitializeComponents();

        Log.e("Activity", "Create!");
        LoadMessages(contact.getGroup_ID());

    }

    public void onStart()
    {
        super.onStart();
        Log.e("ACTIVITY", "STARTTT");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        //Chat.getInstance().ResetMessagesArray();
        Log.e("ACTIVITY", "RESTART!");
    }

    @Override
    public void onPause() {
        super.onPause();
        //Chat.getInstance().ResetMessagesArray();
        Log.e("ACTIVITY", "PAUSE!");
    }

    public void onResume(){
        super.onResume();
        Chat.getInstance().ResetMessagesArray();
        Log.e("Activity", "RESUME!");
    }

    public void onStop() {
        super.onStop();
        Log.e("ACTIVITY", "STOP!");
    }

    void LoadMessages(int group_ID)
    {
        try {
            Chat.getInstance().GetMessages(100, group_ID);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.chat_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_button)
        {
            //deschide alta activitate
        }
        return super.onOptionsItemSelected(item);
    }

    void InitializeComponents()
    {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true); //for performance
        recyclerView.setOnClickListener(new ChatLayoutClickListener());

        editText = findViewById(R.id.editText);
        inputTextHandler = new InputTextHandler(context, editText);
        //editText.setOnFocusChangeListener(new InputFocusListener());

        ImageButton sendBtn = findViewById(R.id.sendButton);
        SendButtonHandler sendButtonHandler = new SendButtonHandler();
        sendBtn.setOnClickListener(sendButtonHandler);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter mAdapter = new ChatAdapter(Chat.getInstance().getMessagesArray());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void DBMessagesReceived(ArrayList<Message> newData) {

        ArrayList<Message> mData = Chat.getInstance().getMessagesArray();
        newData.addAll(mData);
        mData.clear();
        mData.addAll(newData);
        //Log.d("BABABA", Integer.toString(mData.size()));
        if(recyclerView.getAdapter() != null)
        {
            //Log.d("BABABA", Integer.toString(recyclerView.getAdapter().getItemCount()));
            //recyclerView.getAdapter().notifyDataSetChanged();
            recyclerView.getAdapter().notifyItemRangeInserted(0, mData.size());
        }
    }

    void GetInfoFromPrevActivity(Intent intent)
    {
        IntentInfo info = (IntentInfo) intent.getSerializableExtra("info");
        user = info.getUser();
        contact = info.getContact();
    }

    @Override
    public void MessagePosted(Message message) {

        ArrayList<Message> mData = Chat.getInstance().getMessagesArray();
        mData.add(message);
        if(recyclerView.getAdapter() != null)
        recyclerView.getAdapter().notifyItemChanged(mData.size() - 1);
    }

    class ChatLayoutClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            KeyboardHandler.HideKeyboard(context);
            inputTextHandler.setKeyboardCursorVisible(false);
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(context, "Back!", Toast.LENGTH_SHORT).show();
        if(editText.hasFocus())
        {
            Toast.makeText(context, "ARE FOCUS!", Toast.LENGTH_SHORT).show();
            KeyboardHandler.HideKeyboard(context);
            inputTextHandler.setKeyboardCursorVisible(false);
            inputTextHandler.setInputViewText("");
        }
        finish();
    }

    public class SendButtonHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String text;
            text = inputTextHandler.getInputTextAsString();

            if(text.length() > 0)
            {
                Message msg = new Message();
                msg.setText(text);
                msg.setSenderID(user.getID());
                msg.setGrupID(contact.getGroup_ID());
                msg.setOrientation(Message.ORIENTATION_RIGHT);
                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
                msg.setDate(dateFormat.format(c.getTime()));
                Chat.getInstance().CreateMessage(msg);
            }

            //Ascunde tastatura dupa apasarea butonului de send
            KeyboardHandler.HideKeyboard(context);
            inputTextHandler.setKeyboardCursorVisible(false);
            inputTextHandler.setInputViewText("");
        }
    }

}
