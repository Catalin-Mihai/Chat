package com.example.catalin.chat;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

public class ChatMenuItemViewHolder{

    ArrayList<View> views;

    ChatMenuItemViewHolder(View ... views)
    {
        this.views = new ArrayList<>();
        Collections.addAll(this.views, views);
        for(View v: this.views){
            Log.e("VIEWARRAY", v.toString());
        }
    }

    public void addView(View v)
    {
        views.add(v);
    }
}