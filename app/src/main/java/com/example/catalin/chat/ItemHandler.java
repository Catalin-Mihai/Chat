package com.example.catalin.chat;


import android.view.View;

import java.util.Objects;

public abstract class ItemHandler<ViewHolder>{

    protected View rootView;
    protected ViewHolder viewHolder;
    protected abstract ViewHolder getViews(View rootView);
    protected abstract void handleLogic();

    ItemHandler()
    {
        this.rootView = null;
    }

    void notifyItemBound(ChatMenuListAdapter.AdapterSignal signal, View rootView)
    {
        Objects.requireNonNull(signal); //a valid ChatMenuAdapter has been passed
        this.rootView = rootView;
        viewHolder = getViews(rootView);
        handleLogic();
    }

}
