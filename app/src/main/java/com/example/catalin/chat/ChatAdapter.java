package com.example.catalin.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder>{

    private ArrayList<Message> mDataset;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView textView;
        MyViewHolder(View v) {
            super(v);
            textView = (TextView) itemView.findViewById(R.id.messageView);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    ChatAdapter(ArrayList<Message> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        int xml_res;
        switch (viewType) {
            case Message.ORIENTATION_LEFT:
                xml_res = R.layout.message_layout_left;
                break;
            case Message.ORIENTATION_RIGHT:
                xml_res = R.layout.message_layout_right;
                break;
            default:
                xml_res = R.layout.message_layout_left;
                break;
        }
        View v = LayoutInflater.from(parent.getContext())
                .inflate(xml_res, parent, false);
        Log.d("MVH", "A MAI CREAT CEVA");
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //Log.e("Trallala", "blalalla");
        holder.textView.setText(mDataset.get(position).getText());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return mDataset.get(position).getOrientation();
    }
}
