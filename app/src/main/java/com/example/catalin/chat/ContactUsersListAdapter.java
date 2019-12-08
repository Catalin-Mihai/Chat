package com.example.catalin.chat;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.ConnectException;
import java.util.ArrayList;

public class ContactUsersListAdapter extends BaseAdapter {

    ArrayList<User> users;
    Context context;

    ContactUsersListAdapter(Context context, ArrayList<User> users)
    {
        this.users = users;
        this.context = context;
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
        ViewHolder vh = new ViewHolder();

        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.participants_row, parent, false);
            vh.name = (TextView) convertView.findViewById(R.id.participant_name);
            vh.more_button = (ImageButton) convertView.findViewById(R.id.participant_more);
        }
        else{
            vh = (ViewHolder) convertView.getTag();
        }

        vh.name.setText(user.getChatNickname());
        return convertView;
    }

    class ViewHolder
    {
        TextView name;
        ImageButton more_button;
    }
}
