package com.example.catalin.chat;

import android.view.View;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;

public class AccountSubmitChecker implements View.OnClickListener{

    private static final int PASS_TOO_SHORT = 1;
    private static final int PASS_GOOD = 2;
    private SubmitListener listener;

    private String email;
    private String password;

    private EditText emailView;
    private EditText passwordView;

    interface SubmitListener
    {
        void ValidCombination(String email, String password);
        void ShortPassword();
        void InvalidEmail();
    }

    AccountSubmitChecker(EditText emailView, EditText passwordView, SubmitListener listener)
    {
        this.emailView = emailView;
        this.passwordView = passwordView;
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {

        email = emailView.getText().toString();
        password = passwordView.getText().toString();
        if(IsValidEmail(email))
        {
            int pass_code = IsValidPassword(password);
            if(pass_code == PASS_GOOD)
            {
                listener.ValidCombination(email, password);
                /*try {
                    LoginUser(password, email);
                    submitButton.setVisibility(View.GONE);
                    progressCircle.setVisibility(View.VISIBLE);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }*/
            }
            else if(pass_code == PASS_TOO_SHORT)
            {
                listener.ShortPassword();
                //ShowLoginInfo("Password too short!");
                //Toast.makeText(context, "Password too short!", Toast.LENGTH_SHORT).show();
            }
        }
        else listener.InvalidEmail();
            //ShowLoginInfo("Invalid email!");
        //Toast.makeText(context, "Invalid email!", Toast.LENGTH_SHORT).show();
    }

    private boolean IsValidEmail(String s)
    {
        if(s.length() < 5) return false;
        return s.contains("@");
    }

    private int IsValidPassword(String s)
    {
        if(s.length() < 6) return PASS_TOO_SHORT;
        return PASS_GOOD;
    }
}
