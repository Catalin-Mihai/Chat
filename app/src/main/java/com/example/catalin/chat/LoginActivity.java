package com.example.catalin.chat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText passwordView;
    private EditText emailView;
    private TextView resetpassView;
    private TextView createaccView;
    private Button submitButton;
    private Context context;
    private ProgressBar progressCircle;
    private DialogInfo dialogInfo;
    private ConstraintLayout mainLayout;
    /*private FrameLayout loginFrameLayout;
    private TextView loginInfoText;
    private Handler animHandler;
    private Runnable animRunnable;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(getSupportActionBar() != null)
        getSupportActionBar().hide();

        //Test
        /*String s = null;
        try {
            s = APILinkBuilder.Build("getcontacts.php", "blabla", "plala", "cecece", "deee");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e("TEST_API", s);*/

        passwordView = findViewById(R.id.passwordview);
        emailView = findViewById(R.id.emailview);
        submitButton = findViewById(R.id.submitbutton);
        resetpassView = findViewById(R.id.forgot_password);
        createaccView = findViewById(R.id.create_new_account);
        createaccView.setOnClickListener(new CreateAccListener());
        context = this;
        mainLayout = findViewById(R.id.main_layout);
        //submitButton.setOnClickListener(new SubmitListener());
        submitButton.setOnClickListener(new AccountSubmitChecker(emailView, passwordView, new SubmitListener()));
        progressCircle = findViewById(R.id.progressCircle);
        dialogInfo = new DialogInfo(context, mainLayout);

        View view = LayoutInflater.from(context).inflate(R.layout.dialoginfo, ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content), false);
        /*loginFrameLayout = findViewById(R.id.loginInfoLayout);
        loginInfoText = findViewById(R.id.loginInfoText);
        animHandler = new Handler();*/
    }

    class CreateAccListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, CreateAccActivity.class);
            startActivity(intent);
        }
    }

    void LoginUser(String password, String email) throws UnsupportedEncodingException {
        Log.d("LOGIN", password + " " + email);
        UserGetter userGetter = new UserGetter();
        /*String url = "https://catalinmihai.000webhostapp.com/rest/getuser.php?email="
                + URLEncoder.encode(email, "UTF-8")
                + "&password=" + URLEncoder.encode(password, "UTF-8");*/
        String url = APILinkBuilder.Build("getuser.php", "email", email, "password", password);
        userGetter.setListener(new Login());
        userGetter.execute(url);
    }

    class Login implements UserGetter.LogListener
    {
        @Override
        public void UserLogged(User user) {
            Toast.makeText(context, "Logged in!", Toast.LENGTH_SHORT).show();
            progressCircle.setVisibility(View.GONE);
            Log.e("TESTEST", "START ACTIVITY");
            Intent intent = new Intent(LoginActivity.this, ContactsActivity.class);
            intent.putExtra("userinfo", user);
            startActivity(intent);
            finish();
        }

        @Override
        public void UserNotFound() {
            //Toast.makeText(context, "Wrong password/email!", Toast.LENGTH_SHORT).show();
            dialogInfo.ShowDialogInfo("Wrong email or password!", DialogInfo.InfoType.INFO_ERROR);
            submitButton.setVisibility(View.VISIBLE);
            progressCircle.setVisibility(View.GONE);
        }
    }

    private class SubmitListener implements AccountSubmitChecker.SubmitListener
    {

        @Override
        public void ValidCombination(String email, String password) {
            try {
                LoginUser(password, email);
                submitButton.setVisibility(View.GONE);
                progressCircle.setVisibility(View.VISIBLE);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void ShortPassword() {
            dialogInfo.ShowDialogInfo("Password too short!", DialogInfo.InfoType.INFO_ERROR);
        }

        @Override
        public void InvalidEmail() {
            dialogInfo.ShowDialogInfo("Invalid email!", DialogInfo.InfoType.INFO_ERROR);
        }
    }

    /*class SubmitListener implements View.OnClickListener{

        private static final int PASS_TOO_SHORT = 1;
        private static final int PASS_GOOD = 2;

        @Override
        public void onClick(View v) {

            String email = emailView.getText().toString();
            String password = passwordView.getText().toString();
            if(IsValidEmail(email))
            {
                int pass_code = IsValidPassword(password);
                if(pass_code == PASS_GOOD)
                {
                    try {
                        LoginUser(password, email);
                        submitButton.setVisibility(View.GONE);
                        progressCircle.setVisibility(View.VISIBLE);

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                else if(pass_code == PASS_TOO_SHORT)
                {
                    ShowLoginInfo("Password too short!");
                    //Toast.makeText(context, "Password too short!", Toast.LENGTH_SHORT).show();
                }
            }
            else ShowLoginInfo("Invalid email!");
                //Toast.makeText(context, "Invalid email!", Toast.LENGTH_SHORT).show();
        }

        boolean IsValidEmail(String s)
        {
            if(s.length() < 5) return false;
            return s.contains("@");
        }

        int IsValidPassword(String s)
        {
            if(s.length() < 6) return PASS_TOO_SHORT;
            return PASS_GOOD;
        }
    }*/
}

