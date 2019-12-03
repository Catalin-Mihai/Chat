package com.example.catalin.chat;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;

public class CreateAccActivity extends AppCompatActivity {

    private EditText passView;
    private EditText emailView;
    private Button submitButt;
    private ConstraintLayout mainLayout;
    private DialogInfo dialogInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);
        if(getSupportActionBar() != null)
            getSupportActionBar().hide();
        mainLayout = findViewById(R.id.mainLayout);
        passView = findViewById(R.id.new_password);
        emailView = findViewById(R.id.new_email);
        submitButt = findViewById(R.id.submit_button);
        submitButt.setOnClickListener(new AccountSubmitChecker(emailView, passView, new SubmitListener()));
        dialogInfo = new DialogInfo(this, mainLayout);
    }

    private class SubmitListener implements AccountSubmitChecker.SubmitListener {

        @Override
        public void ValidCombination(String email, String password) {
            //TODO CREARE CONT
            GlobalGetter globalGetter = new GlobalGetter();
            globalGetter.setListener(new AccountExistChecker(email, password));
            String url = APILinkBuilder.Build("getuser.php","email", email, "password", password);
            globalGetter.execute(url);
        }

        @Override
        public void ShortPassword() {
            dialogInfo.ShowDialogInfo("Password too short!", DialogInfo.InfoType.INFO_ERROR);
        }

        @Override
        public void InvalidEmail() {
            Log.e("TEST", "EMAILLL");
            dialogInfo.ShowDialogInfo("Invalid email!", DialogInfo.InfoType.INFO_ERROR);
        }
    }

    private class AccountExistChecker implements GlobalGetter.HttpContentListener
    {
        String email;
        String password;
        AccountExistChecker(String email, String password)
        {
            this.email = email;
            this.password = password;
        }

        @Override
        public void ContentReceived(String response) { //Account already exist
            Log.e("Response", response);
            dialogInfo.ShowDialogInfo("There is already an account with this email", DialogInfo.InfoType.INFO_ERROR);
        }

        @Override
        public void ContentNotFound() {
            //dialogInfo.ShowDialogInfo("This email is account free");
            GlobalGetter globalGetter = new GlobalGetter();
            globalGetter.setListener(new AccountInserter());
            String url = APILinkBuilder.Build("insertuser.php", "email", email, "password", password);
            globalGetter.execute(url);
        }
    }

    private class AccountInserter implements GlobalGetter.HttpContentListener
    {

        @Override
        public void ContentReceived(String response) {
            if(response.equals(GlobalGetter.CONTENT_NOT_INSERTED))
            {
                Log.e("RESPONSE", response);
                dialogInfo.ShowDialogInfo("A problem occurred while inserting your account", DialogInfo.InfoType.INFO_ERROR);
            }
            else {
                Runnable runnable = () -> {
                    dialogInfo.HideDialogInfo();//default runnable
                    //Log.e("TEST", "RUNTEST");
                    submitButt.setClickable(true);
                    finish(); //plus extra instruction
                };
                dialogInfo.ShowDialogInfoEx("Your account has been successfully created", DialogInfo.InfoType.INFO_SUCCESS, 2000, runnable);
                Log.e("RESPONSE", response);
                submitButt.setClickable(false);
            }
        }

        @Override
        public void ContentNotFound() { //Carpeala. Vreau sa folosesc NOT_INSERTED in loc de NOT_FOUND pentru ca nu prea are sens cu a doua varianta.
            dialogInfo.ShowDialogInfo("Error! Couldn't insert your account!", DialogInfo.InfoType.INFO_ERROR);
        }
    }

}
