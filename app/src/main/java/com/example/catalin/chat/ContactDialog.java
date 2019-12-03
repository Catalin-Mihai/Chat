package com.example.catalin.chat;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

public class ContactDialog extends DialogFragment implements GlobalGetter.HttpContentListener {

    public interface ContactAdderListener{
        void ContactRequested(int userid);
    }

    TextView search_info;
    ContactAdderListener listener;
    EditText contact_email;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_addnewcontact, container);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button dialog_addcontact = view.findViewById(R.id.add_contact_dialog);
        dialog_addcontact.setOnClickListener(new NewContact_Dialog());
        search_info = view.findViewById(R.id.contact_search_info);
        contact_email = view.findViewById(R.id.contact_email_text);
    }

    void SubmitPressed(String email)
    {
        GlobalGetter globalGetter = new GlobalGetter();
        globalGetter.setListener(this);
        globalGetter.execute(APILinkBuilder.Build("getuser.php", "email", email));
    }

    //Contactul cerut este valid. Informam listenerul cu id-ul contactului
    @Override
    public void ContentReceived(String response) {
        //listener = (ContactAdderListener) getTargetFragment();
        try {
            listener.ContactRequested(Integer.parseInt(response.trim()));
            dismiss();

        } catch (NullPointerException e)
        {
            e.printStackTrace();
        }

    }

    //Contactul cerut este invalid
    @Override
    public void ContentNotFound() {
        search_info.setVisibility(View.VISIBLE);
        search_info.setTextColor(Color.RED);
        search_info.setText("This email doesn't exist!");
    }

    public void setListener(ContactAdderListener listener)
    {
        this.listener = listener;
    }

    private class NewContact_Dialog implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            String email = contact_email.getText().toString();
            SubmitPressed(email);
        }
    }
}


