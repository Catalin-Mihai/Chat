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

public class ContactUsersListAdapter extends BaseAdapter {

    ArrayList<User> users;
    Context context;

    private OnAdderButtonClicked listener;
    interface OnAdderButtonClicked{

        void OnAdderButtonClick();
    }

    ContactUsersListAdapter(Context context, ArrayList<User> users, OnAdderButtonClicked listener)
    {
        this.users = users;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public int getCount() {
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

        if(position != getCount()-1) //Is not the last item
        {
            ViewHolderUser vh_user;
            if (convertView == null) {
                vh_user = new ViewHolderUser();
                convertView = LayoutInflater.from(context).inflate(R.layout.participants_row, parent, false);
                vh_user.name = (TextView) convertView.findViewById(R.id.participant_name);
                vh_user.more_button = (ImageButton) convertView.findViewById(R.id.participant_more);
                convertView.setTag(vh_user);
            } else { //reuse already created view
                vh_user = (ViewHolderUser) convertView.getTag();
            }

            vh_user.name.setText(user.getChatNickname());
        }
        else { //Is the last item (The adding button)
            ViewHolderButton vh_button = new ViewHolderButton();
            if(convertView == null) {

                convertView = LayoutInflater.from(context).inflate(R.layout.participants_adder_button, parent, false);
                vh_button.button = (Button) convertView.findViewById(R.id.adder_button);
                convertView.setTag(vh_button);
            }
            else { //reuse already created view
                vh_button = (ViewHolderButton) convertView.getTag();
            }

            //set up the click listener
            vh_button.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnAdderButtonClick();
                }
            });
        }

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
