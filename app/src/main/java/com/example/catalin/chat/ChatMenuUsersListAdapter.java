package com.example.catalin.chat;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.ConnectException;
import java.util.ArrayList;

public class ChatMenuUsersListAdapter extends BaseAdapter {

    private ArrayList<User> users;
    Context context;

    private ButtonInterface listener;
    interface ButtonInterface{

        void OnMoreButtonClicked(User user);
    }

    ChatMenuUsersListAdapter(Context context, ArrayList<User> users, ButtonInterface listener)
    {
        this.users = users;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        Log.e("SIZE ADAPTER ", " " + users.size());
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        User user = users.get(position);
        ViewHolderUser vh_user;
        if (convertView == null) {
            vh_user = new ViewHolderUser();
            convertView = LayoutInflater.from(context).inflate(R.layout.participants_row, parent, false);
            vh_user.name = (TextView) convertView.findViewById(R.id.participant_name);
            vh_user.more_button = (ImageButton) convertView.findViewById(R.id.participant_more);
            vh_user.more_button.setOnClickListener(v -> listener.OnMoreButtonClicked(users.get(position)));
            convertView.setTag(vh_user);
        } else { //reuse already created view
            vh_user = (ViewHolderUser) convertView.getTag();
        }

        vh_user.name.setText(user.getChatNickname());

        return convertView;
    }

    class ViewHolderUser
    {
        TextView name;
        ImageButton more_button;
    }

    class ViewHolderButton
    {
        Button button;
    }
}
